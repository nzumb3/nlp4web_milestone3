
/* First created by JCasGen Mon Feb 04 15:49:40 CET 2019 */
package types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Mon Feb 04 15:49:40 CET 2019
 * @generated */
public class Genre_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Genre.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("types.Genre");
 
  /** @generated */
  final Feature casFeat_genre;
  /** @generated */
  final int     casFeatCode_genre;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getGenre(int addr) {
        if (featOkTst && casFeat_genre == null)
      jcas.throwFeatMissing("genre", "types.Genre");
    return ll_cas.ll_getStringValue(addr, casFeatCode_genre);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGenre(int addr, String v) {
        if (featOkTst && casFeat_genre == null)
      jcas.throwFeatMissing("genre", "types.Genre");
    ll_cas.ll_setStringValue(addr, casFeatCode_genre, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Genre_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_genre = jcas.getRequiredFeatureDE(casType, "genre", "uima.cas.String", featOkTst);
    casFeatCode_genre  = (null == casFeat_genre) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_genre).getCode();

  }
}



    