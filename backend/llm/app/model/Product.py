from __future__ import annotations

from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from app.database import Base


class Product(Base):
    __tablename__ = "product"

    # 실제 DB 컬럼명은 product_id로 생성됩니다.
    id = Column(Integer, primary_key=True, autoincrement=True, index=True, name="product_id")
    image_url = Column(String(255), nullable=False, comment="작물 이미지 URL")
    category_id = Column(Integer, ForeignKey("category.category_id"), nullable=False, comment="카테고리 ID")

    # Category와의 관계 (Spring의 경우 1:1)
    category = relationship("Category", back_populates="product", uselist=False)

    notifications = relationship("Notification", back_populates="product")

    def __repr__(self):
        return f"<Product(id={self.id}, image_url='{self.image_url}', category_id={self.category_id})>"
