import hashlib


def passwordHash(password, userId, salt='fd975e94f51920b4'):
    salt += str(userId)
    saltedPassword = password + salt
    # calculate hash(saltedPassword)
    return hashlib.sha256(saltedPassword.encode()).hexdigest()

