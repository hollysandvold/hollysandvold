# Holly Sandvold, 260788799
# Script to clean a jason file with expected call:
# python3 clean.py -i <input_file> -o <output_file>
# expected keys: title or title_text; createdAt; text; author; total_count; tags.

from json.decoder import JSONDecodeError
from dateutil import parser
import pytz, datetime
import sys
import json
import os

#remove posts without a title field and normalize all title fields
def check_title(data):
    #Remove all the posts that don’t have either a title or title_text field.
    #if title field exists, do nothing
    if "title" in data:
        pass
    #if title_text field exists, change to title
    elif "title_text" in data:
        data["title"] = data.pop("title_text")
    #Remove all the posts that don’t have either a title or title_text field.
    else:
        data = None
    return data

#Remove all the posts where the author field is empty, null, or N/A.
def check_author(data):
    #if author exists, make sure it's not empty or null or N/A
    if "author" in data:
        author = data["author"]
        if author == "N/A" or author is None or author == "":
            data = None
    #keep if authors does not exist
    return data

#The value in the total_count can only be type int, float, str. You must attempt to cast float and str to an int value
def cast_total_count(data):
    if "total_count" in data:
        #try to cast to int
        try:
            data["total_count"] = int(data["total_count"])
        
        #If unable to cast total_count to int, remove the post.
        except ValueError:
            data = None
    #if total_count is not present, keep anyways
    else:
        pass

    return data

#the tags field should be a list of individual words (where each word does NOT contain a space). 
#tags expected to be a list
def check_tags(data):
    if "tags" in data:
         # Any element that contains spaces should be split into separate words. 
        data["tags"] = [words for segments in data["tags"] for words in segments.split()]
    # If the tags field is not present, keep anyways
    else:
        pass
    return data

#Standardize all createdAt date times to the UTC timezone.
#Remove all the posts with invalid date time that can’t be parsed using the ISO datetime standard.
def stand_createdAt(data):
    try:
        date = parser.parse(data["createdAt"])
        utc_date_time = date.astimezone(pytz.utc)
        data["createdAt"] = utc_date_time.isoformat()
    #if cannot be converted, delete entry   
    except:
        data = None
    return data

#try to load the line as a json, delete line if fails
def load_attempt(line):
    try:
        return json.loads(line)
    except JSONDecodeError: 
        return None

def main():
    #grab args
    input = sys.argv[2]
    output = sys.argv[4]
    
    #reset file if necessary
    if os.path.exists(output):
        os.remove(output)

    #read json file line by line
    file1 = open(input, 'r')
    Lines = file1.readlines()
    
    start = 0 
    # go line by line
    for line in Lines:

        #try to load json
        data = load_attempt(line)
        #skip all the posts that are invalid JSON dictionaries.
        if data is None:
            continue

        data = check_title(data)
        if data is None:
            continue

        data = check_author(data)
        if data is None:
            continue

        data = cast_total_count(data)
        if data is None:
            continue

        data = check_tags(data)
        if data is None:
            continue

        data = stand_createdAt(data)
        if data is None:
            continue

        #if entry is valid, write to output
        if start == 0:
            with open(output, 'a') as f:
                json.dump(data, f)
            start = 1
        else:
            with open(output, 'a') as f:
                f.write("\n")
                json.dump(data, f)
    f.close()
    exit()

if __name__ == "__main__":
    main()