import csv
import json


def main():
    arr = []
    with open("data/street_names.csv","r") as file:
        csv_file = csv.reader(file)
        arr = [row[0] for row in csv_file]

    print(arr[0])
    dic = {}
    dic["names"] = arr
    with open("data/street_names.json","w") as file:
        json.dump(arr, file, indent=2)


if __name__ == '__main__':
    main()