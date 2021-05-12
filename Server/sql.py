import utils
from sqlalchemy import create_engine, exc, Column, Integer, String, ForeignKey
from sqlalchemy.orm import declarative_base, sessionmaker, scoped_session

engine = create_engine('sqlite:///cupo.db', echo=True)
Session = scoped_session(sessionmaker(bind=engine))
Base = declarative_base()


def SessionFK():
    s = Session()
    s.execute('PRAGMA foreign_keys=ON')
    return s


# declare mappings
class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    email = Column(String, nullable=False, unique=True)
    username = Column(String, nullable=False)
    password = Column(String, nullable=False)
    timestamp = Column(Integer, nullable=False)
    gender = Column(String, default='')
    phone = Column(String, default='')
    bio = Column(String, default='')

    def __repr__(self):
        return "<User(id={},email='{}',username='{}',password='{}',timestamp={},gender='{}',phone='{}',bio='{}')>".format(self.id, self.email, self.username, self.password, self.timestamp, self.gender, self.phone, self.bio)


class Post(Base):
    __tablename__ = 'posts'

    id = Column(Integer, primary_key=True)
    author_id = Column(Integer, ForeignKey('users.id'), nullable=False)
    title = Column(String, nullable=False)
    content = Column(String, nullable=False)
    timestamp = Column(Integer, nullable=False)

    def __repr__(self):
        return "<Post(id={},author_id={},title='{}',content='{}',timestamp={})>".format(self.id, self.author_id, self.title, self.content, self.timestamp)


class PostReply(Base):
    __tablename__ = 'post_replies'

    id = Column(Integer, primary_key=True)
    post_id = Column(Integer, ForeignKey('posts.id'), nullable=False)
    author_id = Column(Integer, ForeignKey('users.id'), nullable=False)
    content = Column(String, nullable=False)
    timestamp = Column(Integer, nullable=False)

    def __repr__(self):
        return "<PostReply(id={},post_id={},author_id={},content='{}',timestamp={})>".format(self.id, self.post_id, self.author_id, self.content, self.timestamp)


# create all tables
Base.metadata.create_all(engine)


# APIs
# User
def createUser(email, username, password, ts):
    u = User(email=email, username=username, password=password, timestamp=ts)
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

def findUserByEmail(email):
    s = Session()
    try:
        return s.query(User).filter(User.email == email).one()
    except exc.SQLAlchemyError:
        return None
    finally:
        Session.remove()

def updateUserPassword(email, newPassword):
    s = Session()
    try:
        u = s.query(User).filter(User.email == email).one()
        u.password = utils.passwordHash(newPassword, u.timestamp)
        s.commit()
    except exc.SQLAlchemyError:
        s.rollback()
        return False
    else:
        return True
    finally:
        Session.remove()

def setUserData(email, k, v):
    s = Session()
    try:
        u = s.query(User).filter(User.email == email).one()
        setattr(u, k, v)
        s.commit()
    except exc.SQLAlchemyError:
        s.rollback()
        return False
    else:
        return True
    finally:
        Session.remove()


# Post
def createPost(authorEmail, title, content, timestamp):
    author = findUserByEmail(authorEmail)
    if author is None:
        return False

    post = Post(author_id=author.id, title=title, content=content, timestamp=timestamp)

    s = SessionFK()
    try:
        s.add(post)
        s.commit()
    except exc.SQLAlchemyError:
        s.rollback()
        return False
    else:
        return True
    finally:
        Session.remove()

def getAllPosts():
    posts = Session().query(Post).join(User, Post.author_id == User.id).with_entities(Post.id, User.email, Post.title, Post.content, Post.timestamp).all()
    Session.remove()
    return posts


# PostReply
def createPostReply(postId, authorEmail, content, timestamp):
    author = findUserByEmail(authorEmail)
    if author is None:
        return False

    p = PostReply(post_id=postId, author_id=author.id, content=content, timestamp=timestamp)

    s = SessionFK()
    try:
        s.add(p)
        s.commit()
    except exc.SQLAlchemyError:
        s.rollback()
        return False
    else:
        return True
    finally:
        Session.remove()

