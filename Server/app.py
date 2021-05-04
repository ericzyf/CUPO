import sql
from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app)


@app.route('/')
def root():
    return jsonify({
        'POST': '/create_user'
    })


@app.route('/create_user', methods=['POST'])
def create_user():
    req = request.json
    sql.createUser(req['email'], req['email'], req['password'])
    return '', 201
