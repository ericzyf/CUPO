import random
import smtplib
import string
from dotenv import dotenv_values
from email.message import EmailMessage


# Return a random string consists of uppercase letters and digits of length 6
def genCode():
    return ''.join(random.choices(string.ascii_uppercase + string.digits, k=6))


def sendCode(recv, code):
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

