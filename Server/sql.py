from sqlalchemy import create_engine, exc, Column, Integer, String
from sqlalchemy.orm import declarative_base, sessionmaker, scoped_session

engine = create_engine('sqlite:///cupo.db', echo=True)
Session = scoped_session(sessionmaker(bind=engine))
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


# APIs
def createUser(email, username, password):
    u = User(email=email, username=username, password=password)

    s = Session()
    try:
        s.add(u)
        s.commit()
    except exc.SQLAlchemyError:
        s.rollback()
        return False
    else:
        return True
    finally:
        Session.remove()

