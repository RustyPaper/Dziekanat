import json
import argparse
import random
import psycopg2
from pprint import pprint

cursor = ""
conn = ""
def connect_to_db():
    global conn
    global cursor

    with open("configDB.json","r") as file:
        config = json.loads(file.read())

    conn = psycopg2.connect(host=config['host'], port=config['port'], database=config['database'], user=config['user'], password=config['password'])
    cursor = conn.cursor()
    #cursor.execute("CREATE TABLE Studenci (id SERIAL PRIMARY KEY, imie varchar(255), nazwisko varchar(255), adres_zamieszkania varchar(255),pesel varchar(255), email varchar(255), numer_telefonu varchar(255), numer_indeksu varchar(255) );")


def close_db():
    global conn
    global cursor

    cursor.close()
    conn.commit()

with open("data/names.json","r") as file:
    NAMES = json.loads(file.read())

with open("data/lastnames.json", "r") as file:
    LAST_NAMES = json.loads(file.read())

with open("data/street_names.json", "r") as file:
    STREET_NAMES = json.loads(file.read())

with open("data/cities.json", "r") as file:
    CITIES = json.loads(file.read())

with open("data/email_domains.json", "r") as file:
    EMAIL_DOMAINS = json.loads(file.read())


def random_name():
    index = random.randint(0,len(NAMES)-1)
    name = NAMES[index]

    index = random.randint(0, len(LAST_NAMES)-1)

    lastname = LAST_NAMES[index]

    if name[-1].lower() == "a" and lastname[-1].lower() == "i":
        lastname = lastname[:-1]+"A"
        pprint(lastname)

    return (name, lastname)


def random_pesel():
    numbers = []
    numbers.append(random.randint(90,97))
    numbers.append(random.randint(1,12))
    if numbers[1] == 2:
        numbers.append(random.randint(1,28))
    else:
        numbers.append(random.randint(1, 30))

    numbers = list(map(lambda x: '{}'.format(x) if x > 9 else '0{}'.format(x), numbers))

    for i in range(5):
        numbers.append('{}'.format(random.randint(0, 9)))

    return "".join(numbers)


def random_address():

    address = []
    index = random.randint(0,len(STREET_NAMES)-1)
    address.append(STREET_NAMES[index])

    address.append('{}{}/{}{}'.format(random.randint(0,9),random.randint(0,9),random.randint(0,9),random.randint(0,9)))

    index = random.randint(0, len(CITIES)-1)
    address.append(CITIES[index])

    return ", ".join(address)

def random_email(name, lastname):
    index = random.randint(0,len(EMAIL_DOMAINS)-1)

    return "{}.{}@{}".format(name,lastname,EMAIL_DOMAINS[index])

def random_phone():
    return "{}".format(random.randint(6,8))+"".join(["{}".format(random.randint(0,8)) for n in range(8)])

def main():
    global cursor

    connect_to_db()
    parser = argparse.ArgumentParser()

    parser.add_argument("quantity", type=int)

    args = parser.parse_args()
    result = []

    for number in range(args.quantity):
        name = random_name()
        pesel = random_pesel()
        address = random_address()
        email = random_email(name[0], name[1])
        phone = random_phone()
        query = "INSERT INTO wykladowcy (imie, nazwisko, adres_zamieszkania, pesel, email) VALUES('{}', '{}', '{}', '{}', '{}');".format(name[0],name[1],address,pesel, email)
        cursor.execute(query)

    for user in result:
        pprint(user)

    close_db()
if __name__ == '__main__':
    main()
