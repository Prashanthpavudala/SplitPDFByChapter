# SplitPDFByChapter



curl --location 'localhost:8767/pdf/split' \
--form 'file=@"/Users/prashanth/Desktop/Books/Data Structures and Algorithms - Narasimha Karumanchi.pdf"' \
--form 'chapters="[
  { \"title\": \"Chapter 1 - Introduction\", \"startPage\": 16, \"endPage\": 61 },
  { \"title\": \"Chapter 2 - Recursion and Backtracking\", \"startPage\": 62, \"endPage\": 73 },
  { \"title\": \"Chapter 3 - Linked Lists\", \"startPage\": 74, \"endPage\": 162 },
  { \"title\": \"Chapter 4 - Stacks\", \"startPage\": 163, \"endPage\": 204 },
  { \"title\": \"Chapter 5 - Queues\", \"startPage\": 205, \"endPage\": 223 },
  { \"title\": \"Chapter 6 - Trees\", \"startPage\": 224, \"endPage\": 368 },
  { \"title\": \"Chapter 7 - Heaps and Priority Queues\", \"startPage\": 369, \"endPage\": 408 },
  { \"title\": \"Chapter 8 - Disjoint Sets\", \"startPage\": 409, \"endPage\": 425 },
  { \"title\": \"Chapter 9 - Graph Algorithms\", \"startPage\": 426, \"endPage\": 503 },
  { \"title\": \"Chapter 10 - Sorting\", \"startPage\": 504, \"endPage\": 546 },
  { \"title\": \"Chapter 11 - Searching\", \"startPage\": 547, \"endPage\": 589 },
  { \"title\": \"Chapter 12 - Selection Algorithms\", \"startPage\": 590, \"endPage\": 605 },
  { \"title\": \"Chapter 13 - Symbol Tables\", \"startPage\": 606, \"endPage\": 609 },
  { \"title\": \"Chapter 14 - Hashing\", \"startPage\": 610, \"endPage\": 639 },
  { \"title\": \"Chapter 15 - String Algorithms\", \"startPage\": 640, \"endPage\": 684 },
  { \"title\": \"Chapter 16 - Algorithm Design Techniques\", \"startPage\": 685, \"endPage\": 689 },
  { \"title\": \"Chapter 17 - Greedy Algorithms\", \"startPage\": 690, \"endPage\": 705 },
  { \"title\": \"Chapter 18 - Divide and Conquer\", \"startPage\": 706, \"endPage\": 733 },
  { \"title\": \"Chapter 19 - Dynamic Programming\", \"startPage\": 734, \"endPage\": 794 },
  { \"title\": \"Chapter 20 - Complexity Classes\", \"startPage\": 795, \"endPage\": 807 },
  { \"title\": \"Chapter 21 - Miscellaneous\", \"startPage\": 808, \"endPage\": 828 }
]"'