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
        'GET': ['/auth'],
        'POST': ['/create_user']
    })


@app.route('/create_user', methods=['POST'])
def create_user():
    ts = int(time.time() * 1000)  # timestamp in millisec
    req = request.json
    email = req['email']
    username = email  # initial username is email
    password = utils.passwordHash(req['password'], ts)  # hashed password

    if sql.createUser(email, username, password, ts):
        return '', 201
    else:
        return '', 409

