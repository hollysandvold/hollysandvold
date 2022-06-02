# compute word count 
# count words said by each pony in MLP 

# python compile_word_counts.py -o <word_counts_json> -d <clean_dialog.csv file>

import argparse
import os.path
from collections import Counter
from typing import final
from numpy import result_type
import pandas as pd
import sys
import json
from pathlib import Path
parentdir = Path(__file__).parents[1]
stopwords = os.path.join(parentdir, "data/stopwords.txt")
STOPWORDS = stopwords


def get_only_main_ponies(df):
    df = df.drop(columns=['title', 'writer'])
    for x in df.index:
        ponyName = df.loc[x, 'pony']
        ponyName = ponyName.casefold()
        if(ponyName=="Twilight Sparkle".casefold() or ponyName=="Applejack".casefold() or ponyName=="Rarity".casefold() or ponyName == "Pinkie Pie".casefold() or ponyName == "Rainbow Dash".casefold() or ponyName == "Fluttershy".casefold()):
            df.loc[x, 'pony'] = ponyName
            pass
        else:
            df = df.drop([x])
    return df

def get_all_words(df):
    allwords = []
    for x in df.index:
        dialog = df.loc[x, 'dialog']
        charsToRemove = ["(", ")", "[", "]" , ",", "-", ".", "?", "!", ":", ";", "#", "&", "\""]
        for char in charsToRemove:
            dialog = dialog.replace(char, " ")
        
        with open(STOPWORDS, "r") as f:
            stopwords = f.readlines()
            stopwords = [line.rstrip() for line in stopwords]
        
        dialog = dialog.split()
        ponywords = []
        for word in dialog:
            if word.lower() not in stopwords:
                if word.lower().isalpha():
                    if not any(c.isdigit() for c in word):
                        ponywords.append(word.lower())
                        allwords.append(word.lower())
        df.loc[x, 'dialog'] = ponywords
    return allwords

def get_frequent_words(wordCount):
    finalWords = []
    for word in wordCount:
        occurences = wordCount[word]
        if occurences >= 5:
            finalWords.append(word)
    return finalWords

def pony_word_counts(df, finalWords):
    ponies = {"twilight sparkle": {}, "applejack": {}, "rarity": {}, "pinkie pie": {}, "rainbow dash": {}, "fluttershy": {}}

    for x in df.index:
        dialog = df.loc[x, 'dialog']
        pony = df.loc[x,'pony']
        for word in dialog:
            if word in finalWords:
                if word not in ponies[pony]:
                    ponies[pony][word] = 1
                else:
                    ponies[pony][word] = ponies[pony][word] + 1
    return ponies

def main(): 
    # get arguments 
    parser = argparse.ArgumentParser()
    parser.add_argument("-o")
    parser.add_argument("-d")
    args = parser.parse_args()
    
    output = args.o
    infile = args.d

    df = pd.read_csv(infile)

    # only one pony speaking at a time exactly matching the ponies names counts
    # keep only the main char ponies and the dialogue
    df = get_only_main_ponies(df)
    
    allwords = get_all_words(df)

    wordCount = Counter(allwords)
    finalWords = get_frequent_words(wordCount)

    #now get words counts for each pony
    ponies = pony_word_counts(df, finalWords)
    
    # output as a json file
    dir = os.path.dirname(args.o)

    if not os.path.exists(dir):
        if dir != '':
            os.makedirs(dir)
    with open(output, 'w') as f:
        json.dump(ponies, f, indent=4)

if __name__ == '__main__':
    main()
