import sql
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
    req = request.json
    ts = int(time.time() * 1000)
    if sql.createUser(req['email'], req['email'], req['password'], ts):
        return '', 201
    else:
        return '', 409

