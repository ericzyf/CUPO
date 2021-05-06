import random
import string


# Return a random string consists of uppercase letters and digits of length 6
def genVerificationCode():
    return ''.join(random.choices(string.ascii_uppercase + string.digits, k=6))

