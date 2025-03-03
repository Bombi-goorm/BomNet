from __future__ import annotations

from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from app.database import Base


class Category(Base):
    __tablename__ = "category"

    category_id = Column(Integer, primary_key=True, autoincrement=True, index=True)
    name = Column(String(255), nullable=False, comment="카테고리 이름")
    parent_id = Column(Integer, ForeignKey("category.category_id"), nullable=True, comment="부모 카테고리 ID")

    # 자기참조 관계 (부모/자식)
    parent = relationship("Category", remote_side=[category_id], back_populates="children")
    children = relationship("Category", back_populates="parent")

    # Product와의 관계 (Spring의 경우 Product는 Category와 1:1 관계)
    product = relationship("Product", back_populates="category", uselist=False)

    def __repr__(self):
        return f"<Category(category_id={self.category_id}, name='{self.name}', parent_id={self.parent_id})>"
