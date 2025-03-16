from fastapi import Depends, HTTPException
from sqlalchemy.orm import Session

from app.database import get_db
from app.model import Member

def get_current_member(member_id: str, db: Session = Depends(get_db)):
    if not member_id:
        raise HTTPException(status_code=401, detail="멤버를 찾을 수 없습니다.")

    member = db.query(Member).filter(Member.member_id == member_id).first()
    if not member:
        raise HTTPException(status_code=401, detail="멤버를 찾을 수 없습니다.")
