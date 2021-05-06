import hashlib
import re


def passwordHash(password, ts, salt='fd975e94f51920b4'):
    salt += str(ts)
    saltedPassword = password + salt
    # calculate hash(saltedPassword)
    return hashlib.sha256(saltedPassword.encode()).hexdigest()


cuhk_email_regex = re.compile(r"^\w+@(link.)?cuhk.edu.hk$", flags=re.ASCII)

def isCUHKEmail(email):
    return cuhk_email_regex.match(email)

