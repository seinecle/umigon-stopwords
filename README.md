# umigon-stopwords
Stopwords in many languages and sophisticated stopwords removal methods for ngrams.

## Installation

This is a dependency free, 112Mb jar:

```xml
<dependency>
	<groupId>net.clementlevallois.functions</groupId>
	<artifcactId>umigon-stopwords</artifactId>
	<version>0.13</version>
</dependency>
```

Or [check on Maven](https://central.sonatype.com/artifact/net.clementlevallois.functions/umigon-stopwords) to see the latest version.


* 2023, April 13: version 0.13

Added more words to the list of English stopwords of the scientific discourse

* 2023, March 28: version 0.12

Fixes a critical issue with files containing stopwords being ignored because of a malformed pom. Moving the resource files to the resources folder is a better practice.

* 2023, March 28: version 0.11

Adds 'https' as an annoying stopword to remove (English and French only at the moment)

* 2023, March 24: version 0.10

Initial release


## Usage
```java

// initialization: loading stopwords for a given language.

String selectedLanguage = "en";
Set<String> stopwords = Stopwords.getStopWords(selectedLanguage).get("long");

StopWordsRemover stopWordsRemover = new StopWordsRemover(minCharNumber, selectedLanguage);

Set<String> userSuppliedStopwords = Set.of("lorem", "ipsum");
boolean replaceStopwords = false;

stopWordsRemover.useUSerSuppliedStopwords(userSuppliedStopwords, replaceStopwords);

if (isScientificCorpus) {
	if (selectedLanguage.equals("en")) {
		Set<String> scientificStopwordsInEnglish = Stopwords.getScientificStopwordsInEnglish();
		stopWordsRemover.addFieldSpecificStopWords(scientificStopwordsInEnglish);
	}
	if (selectedLanguage.equals("fr")) {
		Set<String> scientificStopwordsInFrench = Stopwords.getScientificStopwordsInFrench();
		stopWordsRemover.addFieldSpecificStopWords(scientificStopwordsInFrench);
	}
}
stopWordsRemover.addWordsToRemove(new HashSet());
stopWordsRemover.addStopWordsToKeep(new HashSet());

// 2. Run

String testWord_1 = "of";
boolean removeIt_1 = stopWordsRemover.shouldItBeRemoved(testWord_1);
// removeIt_1 will evaluate to "true";

String testWord_2 = "United States of America";
boolean removeIt_2 = stopWordsRemover.shouldItBeRemoved(testWord_2);
// removeIt_2 will evaluate to "false";

String testWord_3 = "States of";
boolean removeIt_3 = stopWordsRemover.shouldItBeRemoved(testWord_3);
// removeIt_3 will evaluate to "true";

```

### Lists of stopwords
- in multiple languages  
- with lists of stopwords for academic types of literature and Twitter


### Focus on the stopwords for the academic literature
These stopwords have been curated "by hand" across a long period of time. Terms are included if they are not specific to a scientific field. Please check if this fits your needs. There are some border cases. For instance, "network" is included, because I judged that the term carries less and less informational value (is becoming less and less specific to a given field or subfield). Users interested in studying corpora network theory / social networks / neural networks might still need to remove it from the list.

### Stopwords for the academic literature in French
This list is currently under construction, but works already pretty well.


### Origin?
This function is developed by Clement Levallois, in support of academic work published [in various places](https://scholar.google.fr/citations?user=r0R0vekAAAAJ&hl=en). It is now used in support of [a web app providing free text analysis for non coders](https://nocodefunctions.com).

### Credit and attribution
If you use this repository in an academic context, please cite the publication where these stopwords where first deployed:

> Levallois, C., Clithero, J. A., Wouters, P., Smidts, A., & Huettel, S. A. (2012). Translating upwards: linking the neural and social sciences via neuroeconomics. Nature Reviews Neuroscience, 13(11), 789-797.

Otherwise, please add a link to this repo in your app.

### Contributions
Your contributions are very welcome.

### License
Creative Commons Attribution 4.0 International Public License
