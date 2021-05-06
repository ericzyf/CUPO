import random
import redis
import smtplib
import string
from dotenv import dotenv_values
from email.message import EmailMessage

R = redis.Redis(host='localhost', port=6379, db=0)
CODE_TTL = 300  # each verification code expires after 5 mins


# Return a random string consists of uppercase letters and digits of length 6
def genCode():
    return ''.join(random.choices(string.ascii_uppercase + string.digits, k=6))


def sendMail(recv, code):
    # load account info from .env
    env = dotenv_values('.env')
    SENDER = env['VERIFICATION_EMAIL']
    PWD = env['VERIFICATION_EMAIL_PWD']

    msg = EmailMessage()
    msg.set_content('Verification code: ' + code)
    msg['Subject'] = 'CUPO Sign Up Verification'
    msg['From'] = SENDER
    msg['To'] = recv

    s = smtplib.SMTP('smtp-mail.outlook.com', 587)
    s.starttls()
    s.login(SENDER, PWD)
    s.send_message(msg)
    s.quit()


def sendCode(recv):
    # check redis if there is existing unexpired code
    res = R.get(recv)
    if res is not None:
        # get previous code
        prevCode = res.decode()
        # renew previous code
        R.expire(recv, CODE_TTL)
        # send again
        sendMail(recv, prevCode)
    else:
        # create new code
        newCode = genCode()
        # store in redis
        R.set(recv, newCode, ex=CODE_TTL)
        # send
        sendMail(recv, newCode)

