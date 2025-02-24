from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship

from database import Base


class Product(Base):
    __tablename__ = "product"

    id = Column(Integer, primary_key=True, autoincrement=True, index=True, name="product_id")
    image_url = Column(String(255), nullable=False, comment="작물 이미지 URL")
    category_id = Column(Integer, ForeignKey("category.category_id"), nullable=False, comment="카테고리 ID")

    # 카테고리와 1:1 관계 설정
    category = relationship("Category", backref="product")

    def __repr__(self):
        return f"<Product(id={self.id}, image_url='{self.image_url}', category_id={self.category_id})>"