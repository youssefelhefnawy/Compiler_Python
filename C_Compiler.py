import re as Regex

class Token:
    def __init__(self, TYPE, VALUE):
        self.TYPE = TYPE
        self.VALUE = VALUE

def ReadFile(FileName):
    FileInstance=open(FileName,"r")
    All_Lines= FileInstance.readlines()
    FileInstance.close()
    return All_Lines
    
def ProduceTokens(Statements):

    return

Regex.MULTILINE
ListOfLines=ReadFile("Input.txt")
ListOfTokens = Regex.findall(r'\w+|\d+|//+.*$|/\*.*\*/|".*"|\'.*\'|[^\w\s]+', AllLines)
print(ListOfTokens)
List_2nd_of_Tokens=['INTEGRAL_LITERAL','FLOAT_LITERAL'
                ,'STRING_LITERAL','CHAR_LITERAL',
                'LEFT_CURLY_B','RIGHT-CURLY_B',
                'LEFT_SQUARE_B','RIGHT_SQUARE_B',
                'LEFT_ROUND_B','RIGHT_ROUND_B'
                'COMMA','SEMICOLON','DOT''NOT'
                'ASSIGN_OPERATOR','PREPROCESSOR'
                'BACKWARD_SLASH','MINUS','PLUS'
                'ASTERICK','DIVIDE','MOD',
                'LESSTHAN','GREATERTHAN','LESS_EQ',
                'GREAT_EQ','EQUAL','NOT_EQUAL','AND'
                'OR','BITWISE_AND','BITWISE_OR',
                'BITWISE_XOR','LEFT_SHIFT','RIGHT_SHIFT'
                'BITWISE_NOT','MULTI_COMMENT','SINGLE_COMMENT'
                ]
Regex_2nd=['\d+','\d+(\.\d+)?', 
         '\"[^\"]*\"',
         '\'[^\']?\'',
           '{', '}',
           '\[',
           '\]',
           '\(','\)',','
        ]
Dict_of_Tokens = {r'^auto\b': 'AUTO', r'\bnew\b': 'NEW', r'\btrue\b': 'TRUE',
                  r'\bfalse\b': 'FALSE', r'^break\b': 'TRUE',
                  '\d+': 'INTEGRAL_LITERAL', '\d*\.\d+': 'FLOAT_LITERAL',
                  '\"[^\"]*\"': 'STRING_LITERAL', '\'[^\']?\'': 'CHAR_LITERAL',
                  '{': 'LEFT_CURLY_B', '}': 'RIGHT-CURLY_B', '\[': 'LEFT_SQUARE_B',
                  '\]': 'RIGHT_SQUARE_B', '\(': 'LEFT_ROUND_B', '\)': 'RIGHT_ROUND_B',
                  ',': 'COMMA'}
Output_Tokens = []

for OneToken in ListOfTokens:
    for pattern in Dict_of_Tokens:
        match = Regex.match(pattern, OneToken)
        if match:
            Output_Tokens.append(Token(Dict_of_Tokens[pattern], match.group(0)))
            break

for OneToken in Output_Tokens:
    print("<{}>: {}".format(OneToken.TYPE, OneToken.VALUE))
#print(Regex.search('\"[^\"]*\"', " \"2312\" ").group(0))
#Result=Regex.findall('\d+[\.\d+]?',"2.3121")
#print(type(Result.group())) -- > Regex.Search
#print(Result.group(0))
#print(Result)
