
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy import create_engine, Column, Integer, String, TIMESTAMP, ForeignKey, select, and_
from sqlalchemy.orm import sessionmaker, declarative_base, Session, relationship
from datetime import datetime

from app.dto.common_response_dto import CommonResponseDto
from app.dto.request_dto import ChatbotRequestDto

alert_router = APIRouter()

# ✅ 데이터베이스 연결
DATABASE_URL = "mysql+pymysql://root:1234@localhost:3306/bomnet_db"
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()

# ✅ 회원(Member) 테이블 모델 (사용자 식별)
class Member(Base):
    __tablename__ = "members"

    id = Column(String(40), primary_key=True)
    name = Column(String(100), nullable=False)

# ✅ Notification (알림) 모델
class Notification(Base):
    __tablename__ = "notifications"

    id = Column(Integer, primary_key=True, autoincrement=True)
    member_id = Column(String(40), ForeignKey("members.id"), nullable=False)
    notification_type = Column(String(20), nullable=False, default="TARGET_PRICE")  # 지명가 알림
    title = Column(String(255), nullable=True)
    message = Column(String(255), nullable=False)
    is_read = Column(String(1), default="N")  # 읽음 여부 (기본값: N)
    created_at = Column(TIMESTAMP, default=datetime.utcnow)

    member = relationship("Member")

# ✅ 테이블 자동 생성
Base.metadata.create_all(bind=engine)

# ✅ 데이터베이스 세션 종속성
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

# ✅ 가격 알람 등록 API
@alert_router.post("/set", response_model=CommonResponseDto[None])
async def set_alert(data: ChatbotRequestDto, db: Session = Depends(get_db)):
    """
    사용자가 원하는 가격 알람을 설정하고 Notification 테이블에 저장합니다.
    """

    # 1️⃣ 필수 입력값 검증
    if not data.bigId or not data.midId or not data.smallId or not data.price or not data.memberId:
        raise HTTPException(status_code=400, detail="❌ 필수 입력값이 없습니다.")

    # 2️⃣ 사용자가 존재하는지 확인 (회원 조회)
    member = db.execute(select(Member).where(Member.id == data.memberId)).scalar_one_or_none()
    if not member:
        raise HTTPException(status_code=404, detail="❌ 존재하지 않는 회원입니다.")

    # 3️⃣ 중복 알람 확인
    existing_alert = db.execute(
        select(Notification).where(
            and_(
                Notification.member_id == data.memberId,
                Notification.notification_type == "TARGET_PRICE",
                Notification.message.like(f"%{data.bigId} - {data.midId} - {data.smallId} - {data.price}원%")
            )
        )
    ).scalar_one_or_none()

    if existing_alert:
        return CommonResponseDto(success=False, message=f"⚠️ 이미 등록된 알람입니다.", data=None)

    # 4️⃣ 새로운 알람 저장
    new_alert = Notification(
        member_id=data.memberId,
        title=f"💰 가격 알람: {data.bigId} - {data.midId} - {data.smallId}",
        message=f"{data.bigId} - {data.midId} - {data.smallId}의 가격이 {data.price}원으로 설정되었습니다.",
        notification_type="TARGET_PRICE"
    )

    db.add(new_alert)
    db.commit()

    return CommonResponseDto(success=True, message=f"✅ 알람이 등록되었습니다.", data=None)
