import random
import string


def genVerificationCode():
    return ''.join(random.choices(string.ascii_uppercase + string.digits, k=6))

