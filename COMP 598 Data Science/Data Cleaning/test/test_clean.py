import unittest
from pathlib import Path
import os, sys
import json
from unittest.case import TestCase
parentdir = Path(__file__).parents[1]
sys.path.append(os.path.join(parentdir, "src"))
import clean

class CleanTest(unittest.TestCase):
    def setUp(self):
        print("\nRUNNING TESTS FOR HW5 -  clean.py\n")
        # load the fixture files as variables  
        self.test_1_file_path = os.path.join(parentdir, "test/fixtures/test_1.json")
        self.test_2_file_path = os.path.join(parentdir, "test/fixtures/test_2.json")
        self.test_3_file_path = os.path.join(parentdir, "test/fixtures/test_3.json")
        self.test_4_file_path = os.path.join(parentdir, "test/fixtures/test_4.json")
        self.test_5_file_path = os.path.join(parentdir, "test/fixtures/test_5.json")
        self.test_6_file_path = os.path.join(parentdir, "test/fixtures/test_6.json")

    def test_file(self):
        #for each test, load and then call respective clean function to check if the data comes back as null
        #also test to make sure the test files exist
        print("TEST TITLE")
        print("----------------------------------------------")
        try:
            self.assertEqual(os.path.isfile(self.test_1_file_path), True)
            try:
                print("Test 1 file found")
                with open(self.test_1_file_path) as f:
                    data = json.load(f)
                    self.assertEqual(clean.check_title(data), None)
                    print("Test 1 passed, errors succesfully identified\n")
                f.close()
            except AssertionError:
                f.close()
                print("Test 1 failed\n")
        except AssertionError:
            print("Test 1 not found\n")

        print("TEST CREATEDAT")
        print("----------------------------------------------")
        try:
            self.assertEqual(os.path.isfile(self.test_2_file_path), True)
            try:
                print("Test 2 file found")
                with open(self.test_2_file_path) as f:
                    data = json.load(f)
                    self.assertEqual(clean.stand_createdAt(data), None)
                    print("Test 2 passed, errors succesfully identified\n")
                f.close()
            except AssertionError:
                f.close()
                print("Test 2 failed\n")
        except AssertionError:
            print("Test 2 not found\n")

        print("TEST INVALID JSON ENTRIES")
        print("----------------------------------------------")
        try: 
            self.assertEqual(os.path.isfile(self.test_3_file_path), True)
            try:
                print("Test 3 file found")
                file1 = open(self.test_3_file_path, 'r')
                line = file1.readline()
                self.assertEqual(clean.load_attempt(line), None)
                print("Test 3 passed, errors succesfully identified\n")
                file1.close()
            except AssertionError:
                file1.close()
                print("Test 3 failed\n")
        except AssertionError:
            print("Test 3 not found\n")
            
        print("TEST AUTHOR")
        print("----------------------------------------------")
        try:
            self.assertEqual(os.path.isfile(self.test_4_file_path), True)
            try:
                print("Test 4 file found")
                with open(self.test_4_file_path) as f:
                    data = json.load(f)
                    self.assertEqual(clean.check_author(data), None)
                    print("Test 4 passed, errors succesfully identified\n")
                f.close()
            except AssertionError:
                f.close()
                print("Test 4 failed\n")
        except AssertionError:
            print("Test 4 not found\n")

        print("TEST TOTAL_COUNT")
        print("----------------------------------------------")
        try:
            self.assertEqual(os.path.isfile(self.test_5_file_path), True)
            try:
                print("Test 5 file found")
                with open(self.test_5_file_path) as f:
                    data = json.load(f)
                    self.assertEqual(clean.cast_total_count(data), None)
                    print("Test 5 passed, errors succesfully identified\n")
                f.close()
            except AssertionError:
                f.close()
                print("Test 5 failed\n")
        except AssertionError:
            print("Test 5 not found\n")

        print("TEST TAGS")
        print("----------------------------------------------")
        try: 
            self.assertEqual(os.path.isfile(self.test_6_file_path), True)
            try: 
                print("Test 6 file found")
                with open(self.test_6_file_path) as f:
                    data1 = json.load(f)
                    len_desired = len(data1["tags"])+ 2
                    data2 = clean.check_tags(data1)
                    self.assertEqual( len(data2["tags"]), len_desired)
                    print("Test 6 passed, errors succesfully identified\n")
                f.close()
            except AssertionError:
                f.close()
                print("Test 6 failed\n")
        except AssertionError:
            print("Test 1 not found\n")

        print("All done :)")
        
    
if __name__ == '__main__':
    unittest.main()