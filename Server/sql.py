from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.orm import declarative_base, sessionmaker

engine = create_engine('sqlite:///:memory:', echo=True)
Session = sessionmaker(bind=engine)
Base = declarative_base()


# declare mappings
class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    email = Column(String, nullable=False, unique=True)
    username = Column(String, nullable=False)
    password = Column(String, nullable=False)

    def __repr__(self):
        return "<User(id={},email='{}',username='{}',password='{}')>".format(self.id, self.email, self.username, self.password)


# create all tables
Base.metadata.create_all(engine)

