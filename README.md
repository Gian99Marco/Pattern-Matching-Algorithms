# Comparison of algorithms for the Pattern Matching problem

## Table of Contents
1. The Pattern Matching Problem
2. The Brute Force Algorithm
3. The Boyer Moore Algorithm
4. The Knuth Morris Pratt (KMP) Algorithm
5. Application Example
6. Experimental Data
   - 6.1 Short Text
   - 6.2 Medium Text
   - 6.3 Long Text
   - 6.4 Conclusions
7. How to Run

## 1. The Pattern Matching Problem

Pattern Matching, which involves finding the occurrence of a certain textual pattern within a larger text, plays a crucial role in numerous applications. In computer science, the most common applications are found in text processing, Internet search engines, digital libraries, and bioinformatics, where Pattern Matching is particularly important in DNA sequence processing.

Text documents are ubiquitous in modern computing, as they are used to communicate and publish information. From an algorithmic design perspective, these documents can be considered as simple character strings. Performing search and processing operations on such data therefore requires efficient methods, or algorithms, to handle these strings.

Strings can be processed in various ways; a typical operation is cutting large strings into smaller ones. To describe the pieces that result from this operation, we use the term substring. A substring of a string P of length m characters is a string of the form P[i]P[i+1]P[i+2]...P[j] or more simply P[i...j], for some 0≤i≤j≤m-1, that is, the string formed by the characters of P from index i to index j inclusive.

If i > j we will consider P[i...j] a null string, i.e., a string of length 0. We will denote with P[0...i], for 0≤i≤j≤m-1, the prefix of string P and with P[i...m-1], for 0≤i≤m-1, the suffix P. Note that the null string is a prefix and suffix of any other string.

In the Pattern Matching problem, given a string T of length n representing a text and a string P of length m representing the pattern, we are asked to verify if P is a substring of T. The notion of match indicates the presence of a substring of T that starts at some index i and corresponds to P, character by character. In formulas: T[i] = P[0], T[i+1] = P[1], T[i+m-1] = P[m-1], or P = T[i...i+m-1]. The output of any Pattern Matching algorithm can be either the index of T where substring P begins or an indication that pattern P does not appear in T.

To make character string processing as general as possible, analysis is typically not limited to characters of T and P explicitly coming from a well-known character set, such as ASCII or Unicode. Instead, the general symbol Σ is typically used to denote the set of characters in the alphabet from which P and T characters can come. This alphabet can obviously be a subset of ASCII or Unicode character sets, but it could also be more general and can even be infinite. However, since most document processing algorithms are used in applications where the character set is finite, it is usually assumed that the size of alphabet Σ, denoted by |Σ|, is a fixed constant.

## 2. The Brute Force Algorithm

The Brute Force algorithm is certainly the most intuitive and simplest among those that solve the Pattern Matching problem. It examines every character in the text against the initial character of the pattern. Once a match is found, it proceeds to compare the subsequent text characters with the rest of the pattern. If no occurrence is found, it continues checking the text character by character in an attempt to find another match. This way, almost every character in the text must be examined until either a pattern match is found or the text ends.

The algorithm consists of two nested loops. The outer loop checks all possible starting indices of the pattern in the text, while the inner loop checks each character of the pattern by comparing it with the potential corresponding character in the text.

```
Algorithm 1: BruteForce(T, P)
Input: String T (text) of n characters and string P (pattern) of m characters
Output: Starting index of the first substring of T that matches P, 
        or an indication that P is not a substring of T

1 for i ← 0 to n - m do
2     j ← 0
3     while (j < m and T[i + j] = P[j]) do
4         j ← j + 1
5     if j = m then
6         return i
7 return P is not a substring of T
```

In the worst case, for each candidate index in T, the algorithm performs up to m character comparisons to discover that P does not match T at the current index. The outer loop is executed at most n - m + 1 times, while the inner loop at most m times. Therefore, the worst-case complexity of the Brute Force algorithm is O((n - m + 1)m), or O(nm). When n and m are approximately equal, the algorithm has quadratic complexity.

The main inefficiency of the Brute Force algorithm is that when testing a possible pattern placement, a certain number of comparisons are made, but as soon as there is a mismatch, all the information gained from previous comparisons is lost.

## 3. The Boyer Moore Algorithm

Unlike the Brute Force algorithm that examines every character in the text to find a pattern, the Boyer-Moore algorithm can sometimes avoid comparing the pattern with a considerable fraction of the characters in the text. While the Brute Force algorithm can work with a potentially unlimited alphabet, the Boyer Moore algorithm assumes instead that the alphabet is of fixed and finite size.

The Boyer-Moore algorithm is based on two heuristics: the looking-glass heuristic and the character-jump heuristic. The first states that when verifying a possible placement of pattern P in text T, comparisons should start from the end of P and then move backward toward the beginning of P. The character-jump heuristic states that when verifying a possible placement of pattern P in text T, a mismatch of the text character T[i] = c with the corresponding pattern character P[j] is handled as follows: if c is not contained anywhere in P then P must be moved completely past T[i] (because T[i] doesn't match any character in P); otherwise P must be shifted until an occurrence of character c in P aligns with T[i].

These two heuristics allow avoiding comparison between P and entire groups of characters in T because if the end of the pattern is compared with the text, then jumps can be made within the text rather than comparing each single character, thus avoiding many unnecessary comparisons.

When aligning the pattern to the text, the last character of the pattern is compared with the corresponding text character. If the characters don't match, there's no need to perform comparisons with the previous characters. Jumping along the text to make comparisons rather than checking every single character reduces the number of comparisons to be made, which is key to increasing the algorithm's efficiency. The comparison continues until either the beginning of P is reached (meaning an occurrence has been found) or there is a mismatch that generates a right shift of the alignment dictated by the character-jump heuristic. Comparisons are performed again with the new alignment and the process repeats until either an occurrence of P is found or the alignment is moved beyond the end of T, meaning no more matches will be found.

To implement the character-jump heuristic, the function last(c) is defined which takes as input a character c and produces as output the index of the last occurrence (rightmost) of c in P. If c is not present in P, conventionally last(c) = -1 is set. The last(c) function specifies how much pattern P must be shifted if a character equal to c is found in the text that doesn't match the pattern.

```
Algorithm 2: BoyerMoore(T, P)
Input: String T (text) of n characters and string P (pattern) of m characters
Output: Starting index of the first substring of T that matches P, 
        or an indication that P is not a substring of T

1 i ← m - 1
2 j ← m - 1
3 repeat
4     if P[j] = T[i] then
5         if j = 0 then
6             return i
7         else
8             i ← i - 1
9             j ← j - 1
10     else
11         i ← i + m - min(j, 1 + last(T[i]))
12         j ← m - 1
13 until i > n - 1
14 return P is not a substring of T
```

The correctness of the Boyer-Moore algorithm derives from the fact that every time the method makes a shift, no possible match is skipped since last(c) indicates the position of the last occurrence of c in P.

The calculation of the last(c) function requires O(m + |Σ|) time, while the actual pattern search requires O(nm + |Σ|) in the worst case. Therefore, the worst-case complexity of the Boyer-Moore algorithm is O(nm + |Σ|).

## 4. The Knuth Morris Pratt (KMP) Algorithm

When studying the worst-case complexity of the Brute Force algorithm and the Boyer-Moore algorithm on specific problem instances, greater inefficiency can be noticed. Many comparisons can be performed while testing a potential pattern placement in the text, however if a pattern character that doesn't match in the text is discovered, all information obtained from these comparisons is thrown away and we start over with the next pattern placement. The Knuth-Morris-Pratt (KMP) algorithm avoids wasting information and thus achieves better worst-case complexity.

The idea behind the KMP algorithm is that when the comparison between the text character and pattern character fails after a certain number of successes, instead of moving back the index on the pattern and text (i.e., stopping the comparison between pattern and text and restarting the search ignoring the outcome of already performed comparisons), the knowledge about the pattern can be exploited avoiding comparisons whose outcome is already known.

The peculiarity of the KMP algorithm lies in the preprocessing of the pattern to be searched, which contains sufficient indication to determine the position from which to continue the search in case of mismatch. The pattern processing occurs through a failure function f that indicates the correct shift of P such that previously executed comparisons can be reused to the greatest extent possible.

```
Algorithm 3: KMPFailureFunction(P)
Input: String P (pattern) of m characters
Output: The failure function f for P that maps j to the length of the
        longest prefix of P that is a suffix of P[1...j]

1 i ← 1
2 j ← 0
3 f(0) ← 0
4 while i < m do
5     if P[j] = P[i] then
6         f(i) ← j + 1
7         i ← i + 1
8         j ← j + 1
9     else if j > 0 then
10         j ← f(j - 1)
11     else
12         f(i) ← 0
13         i ← i + 1
```

```
Algorithm 4: KnuthMorrisPratt(T,P)
Input: String T (text) of n characters and string P (pattern) of m characters
Output: Starting index of the first substring of T that matches P,
        or an indication that P is not a substring of T

1 f ← KMPFailureFunction(P)
2 i ← 0
3 j ← 0
4 while i < n do
5     if P[j] = T[i] then
6         if j = m - 1 then
7             return i - m + 1
8         i ← i + 1
9         j ← j + 1
10     else if j > 0 then
11         j ← f(j - 1)
12     else
13         i ← i + 1
14 return P is not a substring of T
```

The complexity of the failure function is O(m). Its analysis is analogous to that of the KMP algorithm. Therefore, the overall complexity of the KMP algorithm in the worst case is O(n + m).

## 5. Application Example

In this section, the program output is reported based on the text contained in "text.txt", which is "Lorem ipsum", and a randomly chosen pattern contained in "pattern.txt". Below are two examples: one test with a pattern present in the text and one test with the pattern not present. The execution times, expressed in milliseconds, may vary from one execution to another.

### Pattern present
```
Brute Force
Found at position: 92
Number of comparisons: 15
Computation time: 0.0158 ms

Boyer Moore
Found at position: 92
Number of comparisons: 11
Computation time: 0.0308 ms

Knuth Morris Pratt
Found at position: 92
Number of comparisons: 14
Computation time: 0.026 ms
```

### Pattern not present
```
Brute Force
Pattern is not matched in the text
Number of comparisons: 24
Computation time: 0.0374 ms

Boyer Moore
Pattern is not matched in the text
Number of comparisons: 5
Computation time: 0.0547 ms

Knuth Morris Pratt
Pattern is not matched in the text
Number of comparisons: 24
Computation time: 0.0372 ms
```

## 6. Experimental Data

To compare the performance of the three algorithms, various experimental tests were conducted, each with a different configuration of input data, namely text string and pattern.

In particular, a short length text (the first two tercets of the Divine Comedy), a medium length text (Lorem ipsum of 3500 characters), and a long text (Lorem ipsum of 10 thousand characters) were selected. For each of these, the algorithms' results were tested, namely the time taken and the number of comparisons made, varying the length of the pattern to search for. The latter varies from 2% to 20% (with 2% steps) of the length of the text under examination.

Since the algorithms' performance strongly depends on the characteristics of the texts and patterns, as well as variations in the performance of the computer used, different pattern strings were generated in random positions for each length, to then generate an average of the numbers of comparisons made and the time taken by the three algorithms.

It is emphasized that the data was collected using the same computer and under similar load conditions.

The results obtained for each type of text are discussed below.

### 6.1 Short Text
For short length texts, the Boyer Moore algorithm performs on average fewer comparisons compared to KMP and Brute Force. With this type of text, the Boyer Moore and KMP algorithms are not very efficient as the execution time is dominated by the pattern preprocessing time, especially when the latter is of considerable length.

### 6.2 Medium Text
Even in the case of searching on a medium-length text, the performance of KMP and Boyer Moore degrades as the pattern size increases, while still remaining better than that of Brute Force. This phenomenon is observed especially for longer percentage patterns, which cause the preprocessing phase to dominate over the search phase.

### 6.3 Long Text
The same considerations from the previous cases apply here as well. It can be noted how the performance of the KMP and Boyer Moore algorithms still remains better than that of Brute Force.

### 6.4 Conclusions
The temporal behavior of Brute Force appears particularly dependent on the length of the text being searched, being more efficient in short length texts and inefficient in long length texts. This algorithm is particularly effective when the pattern length is very limited, as the number of comparisons in this case is particularly reduced.

As expected, the Boyer Moore algorithm and the KMP algorithm are on average more efficient than Brute Force. Indeed, in most cases and especially for long patterns, Boyer Moore requires a lower total number of comparisons, thanks to the character jumps it can perform. At the same time, the KMP algorithm, by preprocessing the pattern through the failure function, is particularly efficient with a smaller alphabet and with strings that have prefixes that are also suffixes.

## 7 How to Run

To properly run the code, you need Java version 11.0 or higher.

## Steps to Run the Code

1. (Optional) Modify the files `text.txt` and `pattern.txt` in the `src` folder if you want to check for a specific pattern in a text of your choice

2. Open the command prompt

3. Navigate to the `src` folder inside `Pattern Matching`

4. Type the following command and press enter:
   ```
   javac Experiment.java
   ```

5. Type the following command and press enter:
   ```
   java Experiment
   ```
