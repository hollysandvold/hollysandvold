# scrape whosdatedwho website
#
# python3 collect_relationships.py -c <config-file.json> -o <output_file.json>
#
# config file contains a single dictionary with a path to a cache directory and a list of target celebrities
# if the cache file already exists, use that data
# otherwise, make the cache file using the scape
# output a json containing the names of the people the targets have dated

from bs4 import BeautifulSoup
import os, sys
import os.path
import requests
import json
import hashlib
BASE_URL = "https://www.whosdatedwho.com/dating/"

def load_config(config):
    if os.path.isfile(config):
        with open(config, 'r') as file:
            config_json = json.load(file)
        file.close()
    else:
        print("Error: config file not found")
        exit()
    
    return config_json

def cache(person, cache_dir):
    #create a hash code to name the file
        hashID = hash(person)

        #prep file path
        create_dir(cache_dir)
        cache_file_path = os.path.join(cache_dir, hashID)

        #check if cache file exists 
        #exists = check_cache(cache_file_path)
        #if file exists, deposit html file into cache
        if not os.path.isfile(cache_file_path):
            create_cache(cache_file_path, person)
        return cache_file_path
    
def create_dir(cache_dir):
    if not os.path.exists(cache_dir):
        os.makedirs(cache_dir)

def hash(person):
    url = BASE_URL + person
    hashID = hashlib.sha1(url.encode("UTF-8")).hexdigest()
    return hashID

def create_cache(cache_file, person):
    url = os.path.join(BASE_URL, person)
    #print(url)
    page = requests.get(url)
    soup = BeautifulSoup(page.content, "html.parser")
    #print(soup)
    with open(cache_file, "w") as file:
        file.write(str(soup))
    file.close()
    return page

def get_relationships(soup):
    #find and save the relationships json into an array
    try:
        relationships_script = soup.find('script', type="application/ld+json")
        relationships_json = json.loads(relationships_script.text)
        relationships = []
        for element in relationships_json['itemListElement']:
            name = element['item']['name'].lower().replace(" ", "-")
            relationships.append(name)
    #if no relationships
    except:
        relationships = []
    return relationships

def main():
    #get args
    config = sys.argv[2]
    output = sys.argv[4]
    
    #get parameters from config file
    config = load_config(config)

    cache_dir = config["cache_dir"]
    target_people = config["target_people"]

    #iterate through each target person
    whodatedwho = {}
    for person in target_people:
        cache_file_path = cache(person, cache_dir)

        #get the soup data to proceed
        with open(cache_file_path, "r") as f:
            soup = BeautifulSoup(f, "html.parser")
        
        #get aray of relationships
        relationships = get_relationships(soup)

        #add array to dict
        whodatedwho[person] = relationships
    
    #load dict to output file
    with open(output, "w") as f:
        json.dump(whodatedwho, f, indent = 4)

    exit()

if __name__ == '__main__':
    main()