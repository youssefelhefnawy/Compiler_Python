import re as Regex


class Token:
    def __init__(self, TYPE, VALUE):
        self.TYPE = TYPE
        self.VALUE = VALUE


def ReadFile(FileName):
    FileInstance = open(FileName, "r")
    All_Lines = FileInstance.read()
    FileInstance.close()
    return All_Lines


def swap(a, b):
    return b, a


def SortMatches(Matches):
    for i in range(0, len(Matches)):
        for j in range(0, len(Matches)):
            if Matches[i].VALUE.start() < Matches[j].VALUE.start():
                Matches[i], Matches[j] = swap(Matches[i], Matches[j])


def SetDirty(start, end, List):
    for i in range(start, end):
        List[i] = 1
    return List


Regex.MULTILINE
WholeText = ReadFile("Input.txt")
RegexToToken = {
    r'/\*[\w\W]*\*/': 'MULTIPLE COMMENT',
    r'//.*': 'SINGLE COMMENT',
    r'\"[^"]*\"': 'STRING_LITERAL',
    r'\bstruct\b': 'STRUCT',
    r'\bstatic\b': 'STATIC',
    r'\bsizeof\b': 'SIZEOF',
    r'\benum\b': 'ENUM',
    r'\bunion\b': 'UNION',
    r'\bvoid\b': 'VOID',
    r'\bvolatile\b': 'VOLATILE',
    r'\btypedef\b': 'TYPEDEF',
    r'^auto\b': 'AUTO',
    r'\bnew\b': 'NEW',
    r'\btrue\b': 'TRUE',
    r'\bfalse\b': 'FALSE',
    r'\bbreak\b': 'TRUE',
    r'\bbool\b': 'BOOL',
    r'\bcase\b': 'CASE',
    r'\bchar\b': 'CHAR',
    r'\bconst\b': 'CONST',
    r'\bcontinue\b': 'CONTINUE',
    r'\bdefault\b': 'DEFAULT',
    r'\bdo\b': 'DO',
    r'\breturn\b': 'RETURN',
    r'\bdouble\b': 'DOUBLE',
    r'\bfloat\b': 'FLOAT',
    r'\bint\b': 'INT',
    r'\blong\b': 'LONG',
    r'\bshort\b': 'SHORT',
    r'\bsigned\b': 'SIGNED',
    r'\bunsigned\b': 'UNSIGNED',
    r'\belse\b': 'ELSE',
    r'\bextern\b': 'EXTERN',
    r'\bwhile\b': 'WHILE',
    r'\bfor\b': 'FOR',
    r'\bgoto\b': 'GOTO',
    r'\bif\b': 'IF',
    r'\bswitch\b': 'SWITCH',
    r'\bregister\b': 'REGISTER',
    r'\d*(\.\d+)\b': 'FLOAT_LITERAL',
    r'\b\d+\b': 'INTEGRAL_LITERAL',
    r"\'[^']?\'": 'CHAR_LITERAL',
    r'{': 'LEFT_CURLY_B',
    r'}': 'RIGHT-CURLY_B',
    r'\[': 'LEFT_SQUARE_B',
    r'\]': 'RIGHT_SQUARE_B',
    r'\(': 'LEFT_ROUND_B',
    r'\)': 'RIGHT_ROUND_B',
    ',': 'COMMA',
    ';': 'SEMICOLON',
    r'\.': 'DOT',
    '#': 'PREPROCESSOR',
    r'\\': 'BACKSLASH',
    '-': 'MINUS',
    r'\+': 'PLUS',
    r'\*': 'MULTIPLY',
    r'/': 'DIVIDE',
    r'%': 'MOD',
    '<<': 'LEFTSHIFT',
    '>>': 'RIGHTSHIFT',
    '<=': 'LESS THAN OR EQUAL',
    '>=': 'GREATER THAN OR EQUAL',
    '<': 'LESSTHAN',
    r'\]==\]': 'EQUAL',
    r'!=': 'NOT EQUAL',
    '!': 'NOT',
    r'=': 'ASSIGNMENT',
    '&&': 'AND',
    '>': 'GREATER THAN',
    r'\|\|': 'OR',
    r'&': 'BITWISE AND',
    r'\|': 'BITWISE OR',
    r'\^': 'BITWISE XOR',
    '~': 'BITWISE NOT',
    r'\b0\b': 'EOF',
    r'\b[a-zA-Z_$][\w$]*\b': 'ID',
}

MarkedChars = [0] * len(WholeText)
All_Matches = []
for pattern in RegexToToken:
    Result = Regex.finditer(pattern, WholeText)
    for match in Result:
        if MarkedChars[match.start()] == 0:
            All_Matches.append(Token(RegexToToken[pattern], match))
            MarkedChars = SetDirty(match.start(), match.end(), MarkedChars)

ErrorMatches = Regex.finditer(r'[^\s\n]', WholeText)
StopExecution = False
for ErrorMatch in ErrorMatches:
    if MarkedChars[ErrorMatch.start()] == 0:
        StopExecution = True
        print("Syntax error! No match for token \"{}\"!".format(ErrorMatch.group(0)))
if StopExecution:
    exit()
SortMatches(All_Matches)
for Match in All_Matches:
    print("<{}>: {}".format(Match.TYPE, Match.VALUE.group(0)))

# use r before string
# use finditer
