import hashlib


def passwordHash(password, ts, salt='fd975e94f51920b4'):
    salt += str(ts)
    saltedPassword = password + salt
    # calculate hash(saltedPassword)
    return hashlib.sha256(saltedPassword.encode()).hexdigest()

