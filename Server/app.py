import sql
import utils
from flask import Flask, request, jsonify
from flask_cors import CORS
import time

app = Flask(__name__)
CORS(app)


@app.route('/')
def root():
    return jsonify({
        '/users': ['POST'],
        '/users/password': ['PUT'],
        '/users/gender': ['PUT'],
        '/users/phone': ['PUT'],
        '/users/bio': ['PUT'],
        '/auth': ['POST']
    })


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

    if sql.createUser(email, username, password, ts):
        return jsonifyUser( sql.User(email=email, username=username, password=password, timestamp=ts, gender='', phone='', bio='') ), 201
    else:
        return '', 409


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

