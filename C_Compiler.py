import re as Regex
def ReadFile(FileName):
    FileInstance=open(FileName,"r")
    All_Lines= FileInstance.readlines()
    FileInstance.close()
    return All_Lines
    
def ProduceTokens(Statements):

    return
    
ListOfLines=ReadFile("Input.txt")
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
           
           '{','}',
           
           '\[',
           '\]',
           '\(','\)',','
        ]

print(Regex.search('\"[^\"]*\"', " \"2312\" ").group(0))
#Result=Regex.findall('\d+[\.\d+]?',"2.3121")
#print(type(Result.group())) -- > Regex.Search
#print(Result.group(0))
#print(Result)
