from flask import Flask

app = Flask(__name__)

@app.route('/api/flask/hello')
def hello():
    return {
        "message": "Hello from Flask"
    }

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')
