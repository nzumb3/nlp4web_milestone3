

/* First created by JCasGen Mon Feb 04 15:49:40 CET 2019 */
package types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Feb 04 15:49:40 CET 2019
 * XML source: /home/bene/Documents/Git_Repositories/nlp4web_milestone3/code/src/main/resources/desc/type/Genre.xml
 * @generated */
public class Genre extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Genre.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Genre() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Genre(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Genre(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Genre(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: genre

  /** getter for genre - gets 
   * @generated
   * @return value of the feature 
   */
  public String getGenre() {
    if (Genre_Type.featOkTst && ((Genre_Type)jcasType).casFeat_genre == null)
      jcasType.jcas.throwFeatMissing("genre", "types.Genre");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Genre_Type)jcasType).casFeatCode_genre);}
    
  /** setter for genre - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGenre(String v) {
    if (Genre_Type.featOkTst && ((Genre_Type)jcasType).casFeat_genre == null)
      jcasType.jcas.throwFeatMissing("genre", "types.Genre");
    jcasType.ll_cas.ll_setStringValue(addr, ((Genre_Type)jcasType).casFeatCode_genre, v);}    
  }

    