import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


class JSONObject {
	HashMap<String, Object> pair;
	 JSONObject(){
		pair=new HashMap();
	}


Object get(String key){
	return pair.get(key);
}

 Object getPathValue(String path)
 {
	 Object json=this;
	 String[] key=path.split("/");
	 //System.out.println("len="+key.length);
	 if(key[0]==""){
		 return json;
	 }
	 for(int i=0;i<key.length;i++){
		 int tp=-1;
		 //System.out.println("key"+key[i]+"saf" );
		 if(key[i].contains("[")){
			 //System.out.println(key[i].substring(key[i].indexOf("[")+1, key[i].indexOf("]")));
			 tp=Integer.parseInt(key[i].substring(key[i].indexOf("[")+1, key[i].indexOf("]")));
		 }
		
			 if(tp!=-1){
				//System.out.println(tp);
				if(json.getClass().getName().equals("java.util.ArrayList")){
					json=((ArrayList)json).get(tp);
				}
				else{
				 json=((ArrayList)((JSONObject)json).pair.get((key[i].substring(0,key[i].indexOf("["))))).get(tp);
				}
				 //System.out.println("json"+json);
			 }
			 else{
				 //System.out.println("json"+json);
		 json=((JSONObject)json).pair.get(key[i]);
			 }
		 }
		
	 return json;
 }

}
public class JsonParser {
	
	static int i=0;
	static String s="";
	static ArrayList<Integer> digit=new ArrayList();
	public static  JSONObject object() throws Exception{
		if(i>s.length()-1){
			return null;
		}
		 JSONObject ob=new JSONObject();
		//System.out.println("i="+i+" ch="+s.charAt(i));
		
		if(s.charAt(i)=='{'){
			//System.out.println("i1="+i+" ch="+s.charAt(i+1));
			if(s.charAt(i+1)=='}'){
				
				i=i+2;
				return ob;
			}
			//System.out.println("ob1char="+s.charAt(i)+" i="+i);
			do{
					i++;
				if(!pair(ob)){
					//System.out.println("ob2char="+s.charAt(i)+" i="+i);
						return null;
				}
				//System.out.println("ob3char="+s.charAt(i)+" i="+i);
					}while(s.charAt(i)==',');
			//System.out.println(s.charAt(i));
				if(s.charAt(i)=='}'){
					//System.out.println("ob4char="+s.charAt(i)+" i="+i);
					i++;
					return ob;
				}
				//System.out.println("ob5char="+s.charAt(i)+" i="+i);
			
		}
		//System.out.println("ob6char="+s.charAt(i)+" i="+i);
		return null;
	}
	public static boolean pair( JSONObject ob) throws Exception{
		
		
		String str=string();
		if(str!=null){
			
			//System.out.println("p1char="+s.charAt(i)+" i="+i);
			if(s.charAt(i++)==':'){
				//System.out.println("p2char="+s.charAt(i)+" i="+i);
				
				
				Object value=value();
				
				if(value!=null){
					ob.pair.put(str, value);
					//System.out.println("p3char="+s.charAt(i)+" i="+i);
					return true;
				}
			}
		}
		//System.out.println("p4char="+s.charAt(i)+" i="+i);
		return false;	
		
	}
	public static String string() throws Exception{
		String temp="";
		if(s.charAt(i++)=='"'){
			
			//System.out.println("st1char="+s.charAt(i)+" i="+i);
			while(s.charAt(i)!='"'){
				temp+=s.charAt(i);
				//System.out.println("st2char="+s.charAt(i)+" i="+i);
			if(s.charAt(i++)=='\\'){
				
				//System.out.println("st3char="+s.charAt(i)+" i="+i);
				if(s.charAt(i)=='"'||s.charAt(i)=='\\'||s.charAt(i)=='/'||s.charAt(i)=='b'||s.charAt(i)=='f'||s.charAt(i)=='n'||s.charAt(i)=='r'||s.charAt(i)=='t'||s.charAt(i)=='u'){
					temp+=s.charAt(i);
					i++;
					//System.out.println("st4char="+s.charAt(i)+" i="+i);
					
				}
			}
	
			}
					
		}
		//System.out.println("st6char="+s.charAt(i)+" i="+i);
		i++;
		return temp;
		
	}
	public static Object value() throws Exception{
		//System.out.println("i="+i+" ch="+s.charAt(i));
		Object ob="";
		switch (s.charAt(i)){
		case '"':
			ob=string();
			
		break;
		case '{':
			//System.out.println("i="+i+" ch="+s.charAt(i));
			
			ob=object();
				//System.out.println("obi="+i+" ch="+s.charAt(i));
				
		break;
		case '-':
			String tpo=number();
			//System.out.println(tpo);
			ob=tpo;
		break;
		case '[':
			ob=array();
		break;
		case 't':
			ob=true1();
		break;
		
		case 'f':
			ob=false1();
		break;
		case 'n':
			ob=null1();
		break;
		default: if(digit.contains((int)(s.charAt(i))-48)) 
				ob=number();
		break;
		
			
			
		}
		
		return ob;
	}
	public static ArrayList array() throws Exception{
		if(i>s.length()-1){
			return null;
		}
	if(s.charAt(i)=='['){
		ArrayList array=new ArrayList();
		//System.out.println("a1char="+s.charAt(i)+" i="+i);
		if(s.charAt(i+1)==']'){
			i=i+2;
			return array;
		}
		do{
			i++;
			//System.out.println("a2char="+s.charAt(i)+" i="+i);
			Object ob;
			ob=value();
		if(ob==null){
			//System.out.println("a3char="+s.charAt(i)+" i="+i);
				return null;
		}
		array.add(ob);
			}while(s.charAt(i)==',');
		//System.out.println("aachar="+s.charAt(i)+" i="+i);
		if(s.charAt(i++)==']'){
			return array;
		}
		//System.out.println("a4char="+s.charAt(i)+" i="+i);
	}
	
	return null;
	}
	public static String num(boolean exp) throws Exception{
		boolean res=true,rt=false;;
		String temp="";
	if(s.charAt(i)=='-'){
		temp+=s.charAt(i);
		i++;
	}
	
	while(digit.contains((int)(s.charAt(i))-48)){
		temp+=s.charAt(i);
		i++;
		//System.out.println("d1char="+s.charAt(i)+" i="+i);
		if(s.charAt(i)=='.'&&exp){
			return null;
		}
		if(s.charAt(i)=='.'){
			temp+=s.charAt(i);
			//System.out.println("d2char="+s.charAt(i)+" i="+i);
			if(res){
				//System.out.println("d3char="+s.charAt(i)+" i="+i);
			res=false;
			i++;
			}
			else{
				//System.out.println("d4char="+s.charAt(i)+" i="+i);
				return null;
			}
		}
		//System.out.println("d5char="+s.charAt(i)+" i="+i);
		
		rt=true;
	}
	//System.out.println("d6char="+s.charAt(i)+" i="+i);
	if(rt&&s.charAt(i-1)!='.')
		{//System.out.println("d7char="+s.charAt(i)+" i="+i);
		return temp;
		}
	else{
		//System.out.println("p4char="+s.charAt(i)+" i="+i);
		return null;
	}
	}
	public static String number() throws Exception{
		String temp="";
		String tp=num(false);
		if(tp!=null){
			temp+=tp;
			//System.out.println("nu1char="+s.charAt(i)+" i="+i);
		if(s.charAt(i)=='e'||s.charAt(i)=='E'){
			temp+=s.charAt(i);
			i++;
			//System.out.println("nu2char="+s.charAt(i)+" i="+i);
			tp=num(true);
			if(tp!=null){
				//System.out.println("nu3char="+s.charAt(i)+" i="+i);
				return temp+tp;
			}
			else{
				//System.out.println("nu4char="+s.charAt(i)+" i="+i);
				return null;
			}
		}
		return temp;
		}
		return null;
		
		
	}
	public static String true1(){
		//System.out.println("t1char="+s.charAt(i)+" i="+i);
		if(s.substring(i,i+4).equals("true")){
			//System.out.println("t2char="+s.charAt(i)+" i="+i);
			i=i+4;
			return "true";
		}
		
		return null;
	}
	public static String false1() throws Exception{
		//System.out.println("f1char="+s.charAt(i)+" i="+i);
		//System.out.println(s.substring(i,i+5));
		if(s.substring(i,i+5).equals("false")){
			//System.out.println("f2char="+s.charAt(i)+" i="+i);
			i=i+5;
			return "false";
		}
		return null;
	}
	public static String null1() throws Exception{
		//System.out.println("nul1char="+s.charAt(i)+" i="+i);
		if(s.substring(i,i+4).equals("null")){
			//System.out.println("nul2char="+s.charAt(i)+" i="+i);
			i=i+4;
			return "null";
		}
		return null;
	}
	
	public static void main(String[] args) {
		try{
			
		/*	BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			s=br.readLine();*/
		
			FileInputStream fin=new FileInputStream("D://myFile.txt");
			int i=0;
			
			while((i=fin.read())!=-1){
				////System.out.println((char)i+" "+i);
				if(i!=(int)'"'){
				if(i!=(int)('\\')&&i!=(int)' '&&i!=(int)'\b'&&i!=(int)'\r'&&i!=(int)'\f'&&i!=(int)'\t'&&i!=(int)'\n'){
				s=s+(char)i;
				////System.out.println("s="+s);
				}
				}
				else{
					s=s+(char)i;
					while((i=fin.read())!=(int)'"'){
						if(i==(int)'\\'){
							s=s+(char)i;
							i=fin.read();
						 if(i==(int)('\\')||i==(int)('/')||i==(int)('"')||i==(int)('n')||i==(int)('t')||i==(int)'b'||i==(int)'r'||i==(int)'f'){
						 
						s=s+(char)i;
					}
					else{
						throw new Exception();
					}
				}
						else{
							s=s+(char)i;
						}
					}
					s=s+(char)i;
				}
				////System.out.println((char)i);
			}
			//System.out.println(s.length());
			//System.out.println(s);
		
		//System.out.println("asf");
		for(int j=0;j<=9;j++){
			//System.out.println(j);
			digit.add(j);
		}
		//System.out.println("oi");
		 JSONObject ob=object();
		//System.out.println("ob="+ob);
		ArrayList<Object> ar=array();
		if(ob!=null||ar!=null){
		
			System.out.println("No Error");
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String st=br.readLine();
			if(st.charAt(0)=='['){
				String str[]=st.split("/");
				int l=0;
				Object ary=ar.get(Integer.parseInt(str[l++].substring(1, str[0].length()-1)));
				while(str.length>l){
					if(str[l].charAt(1)=='['){
						if(ary.getClass().getName().equals("java.util.ArrayList")){
							ary=((ArrayList)ary).get(Integer.parseInt(str[l].substring(1, str[0].length()-1)));
							
						}
						else{
							
							break;
						}
					}
					else{
						break;
					}
					l++;
					
				}
				String temp="";
				if(ary.getClass().getName().equals("JSONObject")){
				for(int k=l;k<str.length;k++){
					temp+=str[k];
				}
				//System.out.println(temp+"dAd");
				System.out.println(((JSONObject)ary).getPathValue(temp));
				}
				else{
					System.out.println(ary);
				}
				
			}
			
			else{
				//System.out.println("ob="+ob+" ar="+ar);
				System.out.println(ob.getPathValue(st));
			}
			
		}
		
		else{
			throw new Exception();
		}
		
	}
		
		catch(Exception e){
			System.out.println(e);
		}

	}

}
