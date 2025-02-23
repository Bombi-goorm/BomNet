from sqlalchemy import Column, Integer, String
from database import Base


class ProductCode(Base):
    __tablename__ = "product_code"

    id = Column(Integer, primary_key=True, index=True)  # 상품 ID
    big_id = Column(String(10), nullable=False)  # 대분류 ID
    big_name = Column(String(50), nullable=False)  # 대분류명
    mid_id = Column(String(10), nullable=False)  # 중분류 ID
    mid_name = Column(String(50), nullable=False)  # 품목명
    small_id = Column(String(10), nullable=False)  # 소분류 ID
    small_name = Column(String(50), nullable=False)  # 품종명
