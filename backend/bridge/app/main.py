from fastapi import FastAPI
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.exc import SQLAlchemyError

# Database connection setup
DATABASE_URL = "mysql+pymysql://root:1234@mariadb:3306/bomnet_db"

# Create SQLAlchemy engine and session
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# Declare a base class for the models
Base = declarative_base()

# FastAPI app setup
app = FastAPI()

@app.get("/")
def hello():
    return {"message": "Hello World!"}

@app.get("/ping")
def ping_db():
    try:
        db = SessionLocal()
        db.execute(text('SELECT 1'))  # Simple query to check DB connection
        return {"message": "Database connection successful"}
    except SQLAlchemyError as e:
        return {"error": str(e)}
    finally:
        db.close()

# Run the application with uvicorn
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
