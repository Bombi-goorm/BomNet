from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship

from app.database import Base


class Region(Base):
    __tablename__ = 'region'
    id = Column(Integer, primary_key=True, name='region_id')
    weather_si_do_code = Column(String(20), nullable=False)
    weather_si_gun_gu_code = Column(String(20), nullable=False)
    si_gun_gu_code = Column(String(30), nullable=False)
    si_gun_gu_name = Column(String(30), nullable=False)
    station_number = Column(String(10), nullable=False)
    station_name = Column(String(20), nullable=False)
    forecast_zone_code = Column(String(20), nullable=False)
    special_zone_code = Column(String(20), nullable=False)
    special_zone_name = Column(String(40), nullable=False)
    x = Column(String(15), nullable=False)
    y = Column(String(15), nullable=False)
    xx = Column(String(5), nullable=False)
    yy = Column(String(5), nullable=False)
    region_weather = relationship("RegionWeather", uselist=False, back_populates="region")


class RegionWeather(Base):
    __tablename__ = 'region_weather'
    id = Column(Integer, primary_key=True)
    average_temperature = Column(Integer)
    min_temperature = Column(Integer)
    max_temperature = Column(Integer)
    annual_precipitation = Column(Integer)
    annual_sunlight_hours = Column(Integer)
    station_id = Column(String)
    station_name = Column(String)
    region_id = Column(Integer, ForeignKey('region.id'))
    region = relationship("Region", back_populates="region_weather")
