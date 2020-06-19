# Project Miner
A quick set of java tools to mine GitHub repositories for design-related tickets


## Getting Started

Instructions to get the Project Miner running on any machine with any platform of OS.

### Prerequisites 

What must be installed to run the tool:
- IntelliJ IDE with up-to-date JDK package installed

What must be obtained before runing the tool:
- Github Authentication: Personal Access Token
<br>

Note: One has been generated using the account tdresearchgroup, ask administrator for access authorization.
* *How to obtain the Personal access token*:
     * *--->Click profile picture (Top-right corner)*
     * *---> Setting*
     * *---> Developer settings (Bottom-left corner)*
     * *---> Personal access token*
     * *---> ProjectMiner Token<br>*
            *---> Regenerate token (if necessary)*<br>
             *OR*<br>
             *---> Generate new token (needs to fill out the form)*

*For more details of GitHub Authentication, check out below two links* <br>
* *Authorizing OAuth Apps*<br>
https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/<br>
* *Other Authentication Methods*<br>
https://developer.github.com/v3/auth/#basic-authentication<br>


### How to Run

**Cloning the Project**:

Clone the git **master** branch using the command line tool(s)

```
git clone https://github.com/tdresearchgroup/projectminer.git
```

Or use the GitHub Desktop to clone a repository to a designated path by entering the below URL

```
https://github.com/tdresearchgroup/projectminer.git
```
Note: There is another earlier version called "projectminer - ver.2020.05.17" saved on Google Drive: 
      https://drive.google.com/drive/u/1/folders/1BVKyCyADc9X9NoTDEtp2tPg8GI2uPNWX<br>
      which can be used as an alternative if all details of every issue needs to printed to console and these details can be saved into a txt file for quicker access, ask administrator for access authorization. 

**Setting Up the Project Miner**:

**First**, check and make sure the program can run without errors:

1. JDK Package needs to be selected if necessary when setting up as a new project.
2. File location of "design_keywords.txt" needs to be fixed in ProjectHandler.java if necessary.
3. Getters and Setters of ClosedTicketInfo may need to be fixed.

**Second**, copy and paste the ProjectMiner Token into RESTClient.java, line 29:

```
String curlHeader = "curl -H \"Authorization: token ......\" ";
```
"......" should be replaced by the token previously generated.

In terminal, use below command line order to check remaining rate limit for the first time.
```
curl -H "Authorization: token ......" -X GET https://api.github.com/rate_limit
```
"......" is the token that is not expired and ready to be used.

*For more details of GitHub rate limits, check out the following link* <br>
https://developer.github.com/v3/#rate-limiting

**Third**, setup correct owner, name and raw ticket number in config.properties in the directory called "resources" for the desired project to be fetched.
For example,
```
github_repo_owner = elastic
github_repo_name = elasticsearch
github_ticket_num_raw = 57332
```
Note: the raw ticktet number can be the latest issue number of the repo, this can be confirmed by checking the actual URL of the project, for example for this project,  https://github.com/elastic/elasticsearch/issues


**After the successful run**:<br>
zeroOup.csv and nonZeroOutput.csv will be generated in the "src" directory.<br>
Note: These two csv files MUST be moved to a different file directory after every run, otherwise they will be OVERWRITTEN in the next run.  

In addition, in order to speed up the fetching process, check the rate limit remaining and proceed as described below before every run.

In ProjectHandler.java, line 145:
```
for (int i = AAA; i <= BBB; i++)
```
"AAA" can be replaced by the last issue number read, and "BBB" can be replaced by the sum of "AAA"+ Remaining Rate Limit within the same hour to better utilize the of Remaining Rate Limit.
Last issue number read can be confirmed by checking the lastest zeroOutput.csv.

## Final Data Organizing
All data saved in the all the zeroOup.csv and nonZeroOutput.csv they can be either combined together by each project or leave asstand-alone files to be read for version conversion.<br>
Note: For version conversion, contact Derek Reimanis <derek.reimanis@gmail.com> for further details.
