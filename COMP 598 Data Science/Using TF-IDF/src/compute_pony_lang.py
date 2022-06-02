#  compute most frequent and distinct pony language

# python compute_pony_lang.py -c <pony_counts.json> -n <num_words>

import argparse
import json
import math 

def get_ponies_per_word (data):
    ponies_per_word = {}
    for pony in data:
        for word in data[pony]:
            if word not in ponies_per_word:
                ponies_per_word[word] = 1
            else:
                ponies_per_word[word] = ponies_per_word[word] + 1
    return ponies_per_word

def get_idk(ponies_per_word):
    idf = {}

    for word in ponies_per_word:
        idfScore = math.log(6/(ponies_per_word[word]),10)
        idf[word] = idfScore

    return idf
    
def get_tf_idf_all_ponies(data, idf):
    tf_idf_all_ponies = {}

    for pony in data: 
        tf_idf = {}
        for word in data[pony]:
            tfidfScore = data[pony][word] * idf[word]
            tf_idf[word] = tfidfScore
        tf_idf = dict(sorted(tf_idf.items(), key=lambda item: item[1], reverse=True))
        tf_idf_all_ponies[pony]=tf_idf

    return tf_idf_all_ponies

def get_top_tf_idf_scores(tf_idf_scores, num):
    top_tf_idf_scores = {}
    for pony in tf_idf_scores:
        i = 0
        top = []
        for word in tf_idf_scores[pony]:
            if i == num:
                break
            else: 
                
                top.append(word)
                i = i +1
        top_tf_idf_scores[pony] = top

    return top_tf_idf_scores

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("-c")
    parser.add_argument("-n")
    args = parser.parse_args()

    input = args.c 
    num = int(args.n) 

    with open(input) as f:
        data = json.load(f)

    ponies_per_word = get_ponies_per_word (data)

    idf = get_idk(ponies_per_word)

    tf_idf_scores = get_tf_idf_all_ponies(data, idf)

    top_tf_idf_scores = get_top_tf_idf_scores(tf_idf_scores, num)
    
    print(json.dumps(top_tf_idf_scores, indent = 4))
    print(tf_idf_scores["twilight sparkle"])
  
if __name__ == '__main__':
    main()
