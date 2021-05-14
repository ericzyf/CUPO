import sql
import utils
import verification
from flask import Flask, request, jsonify
from flask_cors import CORS
import time

app = Flask(__name__)
CORS(app)


@app.route('/')
def root():
    return jsonify({
        '/v': ['POST'],
        '/users': ['POST'],
        '/users/username': ['PUT'],
        '/users/password': ['PUT'],
        '/users/gender': ['PUT'],
        '/users/phone': ['PUT'],
        '/users/bio': ['PUT'],
        '/user_info': ['PUT'],
        '/auth': ['POST'],
        '/posts': ['POST', 'GET'],
        '/post_replies': ['POST'],
        '/post_replies/<post_id>': ['GET']
    })


@app.route('/v', methods=['POST'])
def verification_code():
    req = request.json
    # send verification code to user
    email = req['email']
    if utils.isCUHKEmail(email):
        verification.sendCode(email)
        return '', 202
    else:
        return '', 400


def jsonifyUser(user):
    return jsonify({
        'email': user.email,
        'username': user.username,
        'timestamp': user.timestamp,
        'gender': user.gender,
        'phone': user.phone,
        'bio': user.bio
    })


@app.route('/users', methods=['POST'])
def users():
    ts = int(time.time() * 1000)  # timestamp in millisec
    req = request.json
    email = req['email']
    username = email  # initial username is email
    password = utils.passwordHash(req['password'], ts)  # hashed password
    code = req['code']

    if not verification.verifyCode(email, code):
        return '', 401
    elif not sql.createUser(email, username, password, ts):
        return '', 409
    else:
        return jsonifyUser( sql.User(email=email, username=username, password=password, timestamp=ts, gender='', phone='', bio='') ), 201


@app.route('/users/username', methods=['PUT'])
def users_username():
    req = request.json
    email = req['email']
    username = req['username']

    # reject empty string as username
    if username == '':
        return '', 400

    if sql.setUserData(email, 'username', username):
        return '', 201
    else:
        return '', 404


@app.route('/users/password', methods=['PUT'])
def users_password():
    req = request.json
    email = req['email']
    password = req['password']

    if sql.updateUserPassword(email, password):
        return '', 201
    else:
        return '', 404


@app.route('/users/gender', methods=['PUT'])
def users_gender():
    req = request.json
    email = req['email']
    gender = req['gender']

    if sql.setUserData(email, 'gender', gender):
        return '', 201
    else:
        return '', 404


@app.route('/users/phone', methods=['PUT'])
def users_phone():
    req = request.json
    email = req['email']
    phone = req['phone']

    if sql.setUserData(email, 'phone', phone):
        return '', 201
    else:
        return '', 404


@app.route('/users/bio', methods=['PUT'])
def users_bio():
    req = request.json
    email = req['email']
    bio = req['bio']

    if sql.setUserData(email, 'bio', bio):
        return '', 201
    else:
        return '', 404


@app.route('/user_info', methods=['PUT'])
def user_info():
    req = request.json
    email = req['email']

    user = sql.findUserByEmail(email)
    if user is not None:
        return jsonifyUser(user), 200
    else:
        return '', 404


@app.route('/auth', methods=['POST'])
def auth():
    req = request.json
    email = req['email']
    password = req['password']

    user = sql.findUserByEmail(email)
    if (user is not None) and (user.password == utils.passwordHash(password, user.timestamp)):
        return jsonifyUser(user), 200
    else:
        return '', 401


@app.route('/posts', methods=['POST', 'GET'])
def posts():
    if request.method == 'POST':
        req = request.json

        email = req['email']
        title = req['title']
        content = req['content']
        timestamp = int(time.time() * 1000)

        if sql.createPost(email, title, content, timestamp):
            return '', 201
        else:
            return '', 400

    elif request.method == 'GET':
        p = [
            {
                'id': x[0],
                'email': x[1],
                'username': x[2],
                'title': x[3],
                'content': x[4],
                'timestamp': x[5]
            }
            for x in sql.getAllPosts()
        ]
        return jsonify(p), 200


@app.route('/post_replies', methods=['POST'])
def post_replies():
    req = request.json

    postId = req['post_id']
    authorEmail = req['email']
    content = req['content']
    timestamp = int(time.time() * 1000)

    if sql.createPostReply(postId, authorEmail, content, timestamp):
        return '', 201
    else:
        return '', 400


@app.route('/post_replies/<post_id>')
def get_post_replies(post_id):
    replies = [
        {
            'email': x[0],
            'username': x[1],
            'content': x[2],
            'timestamp': x[3]
        }
        for x in sql.getPostReplies(post_id)
    ]
    return jsonify(replies), 200

