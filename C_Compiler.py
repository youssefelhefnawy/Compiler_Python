import re as Regex

class Token:
    def __init__(self, TYPE, VALUE):
        self.TYPE = TYPE
        self.VALUE = VALUE

def ReadFile(FileName):
    FileInstance=open(FileName,"r")
    All_Lines= FileInstance.read()
    FileInstance.close()
    return All_Lines
    
def swap(a,b):
    temp=b
    b=a
    a=temp
    return [a,b]
def SeeMatches(TokenName,listofmatches):    
    Matches=[]
    for match in listofmatches:
        Matches.append([TokenName,match])
    return Matches


def SortMatches(Matches):
    for i in range(0, len(Matches)):
        for j in range(0, len(Matches)):
            if Matches[i][1].start() > Matches[j][1].start():
                [Matches[i], Matches[j]] = swap(Matches[i], Matches[j])


def SetDirty(start, end, List):
    for i in range(start, end):
        List[i] = 1
    return List

Regex.MULTILINE
ListOfLines = ReadFile("Input.txt")
Regex_2nd = {
    r'/\*(\W|\w)*\*/': 'MULTIPLE COMMENT',
    r'//.*\n': 'SINGLE COMMENT',
    r'\"[^"]*\"': 'STRING_LITERAL',
    r'^auto\b': 'AUTO',
    r'\bnew\b': 'NEW',
    r'\btrue\b': 'TRUE',
    r'\bfalse\b': 'FALSE',
    r'^break\b': 'TRUE',
    r'\b\d+(\.\d+)\b': 'FLOAT_LITERAL',
    r'\b\d+\b': 'INTEGRAL_LITERAL',
    r"\'[^']?\'": 'CHAR_LITERAL',
    r'{': 'LEFT_CURLY_B',
    r'}': 'RIGHT-CURLY_B',
    r'\[': 'LEFT_SQUARE_B',
    r'\]': 'RIGHT_SQUARE_B',
    r'\(': 'LEFT_ROUND_B',
    '\)': 'RIGHT_ROUND_B',
    ',': 'COMMA',
    ';': 'SEMICOLON'
    , r'\.': 'DOT'
    , '#': 'PREPROCESSOR'
    , r'\\': 'BACKSLASH'
    , '-': 'MINUS'
    , r'\+': 'PLUS'
    , 'r\*': 'MULTIPLY'
    , r'/': 'DIVIDE'
    , r'%': 'MOD'
    , '<<': 'LEFTSHIFT'
    , '>>': 'RIGHTSHIFT'
    , '<=': 'LESS THAN OR EQUAL'
    , '>=': 'GREATER THAN OR EQUAL'
    , '<': 'LESSTHAN'
    , r'\]==\]': 'EQUAL'
    , r'!=': 'NOT EQUAL'
    , '!': 'NOT',
    r'=': 'ASSIGNMENT',
    '&&': 'AND',
    '>': 'GREATER THAN',
    r'\|\|': 'OR'
    , r'&': 'BITWISE AND'
    , '\|': 'BITWISE OR',
    r'\^': 'BITIWSE XOR'
    , '~': 'BITWISE NOT'
}##Lessa fi mshakl
           
    

WholeText=""
for Line in ListOfLines:
    WholeText=WholeText+Line
MarkedChars=[]
for i in range(0,len(WholeText)):
    MarkedChars.append(0)    

All_Matches=[]
for pattern in Regex_2nd:
    Result=Regex.finditer(pattern,WholeText)
    Regex_Matches=[]
    for match in Result:

        if MarkedChars[match.start()]==0:
            Regex_Matches.append([Regex_2nd[pattern],match])
            MarkedChars=SetDirty(match.start(),match.end(),MarkedChars)
        else:
            continue
            ##Do Nothing
            
    All_Matches=All_Matches+Regex_Matches
                  
SortMatches(All_Matches)
All_Matches.reverse()

for Match in All_Matches:
    print(Match[0],"---->",Match[1].group()+"---------")
dictio={'model':'ford'}

###use r before string
###use finditer
