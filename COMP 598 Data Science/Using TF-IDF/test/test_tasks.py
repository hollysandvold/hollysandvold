from typing import final
import unittest
from pathlib import Path
import os, sys
import pandas as pd
import json
from collections import Counter

from pandas.io import parsers
parentdir = Path(__file__).parents[1]
sys.path.append(os.path.join(parentdir, "src"))
import compile_word_counts as cwc
import compute_pony_lang as cpl


class TasksTest(unittest.TestCase):
    def setUp(self):
        dir = os.path.dirname(__file__)
        self.mock_dialog = os.path.join(dir, 'fixtures', 'mock_dialog.csv')
        self.true_word_counts = os.path.join(dir, 'fixtures', 'word_counts.true.json')
        self.true_tf_idfs = os.path.join(dir, 'fixtures', 'tf_idfs.true.json')


    def test_task1(self):
        # use  self.mock_dialog and self.true_word_counts; REMOVE self.assertTrue(True) and write your own assertion, i.e. self.assertEquals(...)
        print("Test compile_word_counts.py")
        print("----------------------------------------------")
        try:
            self.assertEqual(os.path.isfile(self.mock_dialog), True)
            self.assertEqual(os.path.isfile(self.true_word_counts), True)
        except AssertionError:
            print("mock_dialog.csv or word_counts.true.json not found\n")
            pass
        df = pd.read_csv(self.mock_dialog)
        df = cwc.get_only_main_ponies(df)
        allwords = cwc.get_all_words(df)
        wordCount = Counter(allwords)
        finalWords = cwc.get_frequent_words(wordCount)
        data = cwc.pony_word_counts(df, finalWords)
        with open(self.true_word_counts) as f:
            trueWordCounts = json.load(f)
        f.close()
        self.assertEqual(data, trueWordCounts)
        print("Test passed, json file produced matches expected output.\n")

    def test_task2(self):
        # use self.true_word_counts self.true_tf_idfs; REMOVE self.assertTrue(True) and write your own assertion, i.e. self.assertEquals(...)
        print("Test compute_pony_lang.py")
        print("----------------------------------------------")
        try:
            self.assertEqual(os.path.isfile(self.true_tf_idfs), True)
            self.assertEqual(os.path.isfile(self.true_word_counts), True)
        except AssertionError:
            print("word_counts.true.json or tf_idfs.true.json not found\n")
            pass 
        with open(self.true_word_counts) as f:
            trueWordCounts = json.load(f)
        f.close()
        with open(self.true_tf_idfs) as f:
            trueIdfScores = json.load(f)
        f.close()
        ponies_per_word = cpl.get_ponies_per_word(trueWordCounts)
        idf = cpl.get_idk(ponies_per_word)
        tf_idf_scores = cpl.get_tf_idf_all_ponies(trueWordCounts, idf)
        self.assertEqual(tf_idf_scores, trueIdfScores)
        print("Test passed, tf-idf scores produced matches expected output.\n")
        
    
if __name__ == '__main__':
    unittest.main()