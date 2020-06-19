# Project Miner
A quick set of java tools to mine GitHub repositories for design-related tickets


## Getting Started

Instructions to get the Project Miner running on any machine with any platform of OS.

### Prerequisites 

What must be installed to run the tool:

```
IntelliJ IDE with up-to-date JDK package installed
```
What must be obtained before runing the tool:
```
Github Authentication: Personal Access Token
```
Note: One has been generated using the account tdresearchgroup, ask administrator for access authorization.
      Click (Top-right corner)profile picture ---> Setting 
                                              ---> (Bottom-left corner) Developer settings 
                                              ---> Personal access token
                                              ---> ProjectMiner Token
                                                   ---> Regenerate token (if necessary)
                                              
                                              OR
                                              ---> Generate new token (needs to fill out the form)

For more details of GitHub Authentication, check out below two links <br>
Authorizing OAuth Apps
https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/
Other Authentication Methods
https://developer.github.com/v3/auth/#basic-authentication


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
Note: There is a earlier version called "projectminer - ver.2020.05.17" saved on Google Drive: 
      https://drive.google.com/drive/u/1/folders/1BVKyCyADc9X9NoTDEtp2tPg8GI2uPNWX
      which can be used as an alternative if all details of every issue needs to printed to console, ask administrator for access authorization. 

**Setting Up the Project Miner**:

**First**, check and make sure the program should run free of errors:

```
1. JDK Package needs to be selected if necessary when setting up as a new project.
2. File location of "design_keywords.txt" needs to be fixed if necessary.
3. 
```
Keep pressing "Enter" until you see "Is this OK? (yes)"

Enter "yes" to continue

**Second**, copy and paste the ProjectMiner Token into  

```
npm i sqlite3
npm i express
npm i ejs
npm i nodemailer
```
Note: if the warning "Permission Denied" is shown, has to re-run command line tool(s)
      as an administrator or Sudo in Mac Os as indicated previously and repeat the above steps

**To Start the Application**:

Run **app.js** using command line

```
node app.js
```

Application will be live on port 2020

Enter below URL into your browser, **Google Chrome** recommended

```
localhost:2020/
```

If you would like a reference when booking a flight be sure to use a real email in the form.<br>
The site may need to be reactivated to have the latest booking information since
it is local.

### Wiki



