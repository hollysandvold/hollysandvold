#accepts an input file containing one JSON dict per line correstponding to a reddit post
# returns the average length of titles
#no text in a title counts as 0

#python3 compute_title_lengths.py <input_file>.

import json
from json.decoder import JSONDecodeError
import sys
import os

def load_attempt(line):
    try:
        return json.loads(line)
    except JSONDecodeError: 
        return None

def get_average(Lines):
    sum = 0 
    count = 0 
    # go line by line
    for line in Lines:
        #try to load json
        data = load_attempt(line)
        count = count + 1
        try: 
            sum = sum + len(data["data"]["title"])
        #if no title, title length = 0
        except:
            pass
    average = sum/count
    return average

def main():
    input = sys.argv[1]

    #read json file line by line
    file1 = open(input, 'r')
    Lines = file1.readlines()
    
    sum = 0 
    count = 0 
    # go line by line
    for line in Lines:
        #try to load json
        data = load_attempt(line)
        count = count + 1
        try: 
            sum = sum + len(data["data"]["title"])
        #if no title, title length = 0
        except:
            pass
    average = get_average(Lines)
    print(average)
    exit()
        


if __name__ == "__main__":
    main()
