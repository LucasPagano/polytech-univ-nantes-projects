import cx_Oracle
con = cx_Oracle.connect('E144754R/E144754R@matador/oracl')
print(con.version)
con.close()