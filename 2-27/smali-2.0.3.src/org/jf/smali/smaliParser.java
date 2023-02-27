/*       */ package org.jf.smali;
/*       */ 
/*       */ import java.util.ArrayList;
/*       */ import java.util.List;
/*       */ import java.util.Stack;
/*       */ import org.antlr.runtime.BaseRecognizer;
/*       */ import org.antlr.runtime.BitSet;
/*       */ import org.antlr.runtime.CommonToken;
/*       */ import org.antlr.runtime.DFA;
/*       */ import org.antlr.runtime.EarlyExitException;
/*       */ import org.antlr.runtime.FailedPredicateException;
/*       */ import org.antlr.runtime.IntStream;
/*       */ import org.antlr.runtime.MismatchedSetException;
/*       */ import org.antlr.runtime.NoViableAltException;
/*       */ import org.antlr.runtime.Parser;
/*       */ import org.antlr.runtime.ParserRuleReturnScope;
/*       */ import org.antlr.runtime.RecognitionException;
/*       */ import org.antlr.runtime.RecognizerSharedState;
/*       */ import org.antlr.runtime.Token;
/*       */ import org.antlr.runtime.TokenStream;
/*       */ import org.antlr.runtime.tree.CommonTree;
/*       */ import org.antlr.runtime.tree.CommonTreeAdaptor;
/*       */ import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
/*       */ import org.antlr.runtime.tree.RewriteRuleTokenStream;
/*       */ import org.antlr.runtime.tree.TreeAdaptor;
/*       */ import org.jf.dexlib2.Opcodes;
/*       */ 
/*       */ public class smaliParser extends Parser
/*       */ {
/*    20 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACCESS_SPEC", "ANNOTATION_DIRECTIVE", "ANNOTATION_VISIBILITY", "ARRAY_DATA_DIRECTIVE", "ARRAY_DESCRIPTOR", "ARROW", "BASE_ARRAY_DESCRIPTOR", "BASE_CHAR_LITERAL", "BASE_CLASS_DESCRIPTOR", "BASE_FLOAT", "BASE_FLOAT_OR_ID", "BASE_INTEGER", "BASE_PRIMITIVE_TYPE", "BASE_SIMPLE_NAME", "BASE_STRING_LITERAL", "BASE_TYPE", "BINARY_EXPONENT", "BOOL_LITERAL", "BYTE_LITERAL", "CATCHALL_DIRECTIVE", "CATCH_DIRECTIVE", "CHAR_LITERAL", "CLASS_DESCRIPTOR", "CLASS_DIRECTIVE", "CLOSE_BRACE", "CLOSE_PAREN", "COLON", "COMMA", "DECIMAL_EXPONENT", "DOTDOT", "DOUBLE_LITERAL", "DOUBLE_LITERAL_OR_ID", "END_ANNOTATION_DIRECTIVE", "END_ARRAY_DATA_DIRECTIVE", "END_FIELD_DIRECTIVE", "END_LOCAL_DIRECTIVE", "END_METHOD_DIRECTIVE", "END_PACKED_SWITCH_DIRECTIVE", "END_PARAMETER_DIRECTIVE", "END_SPARSE_SWITCH_DIRECTIVE", "END_SUBANNOTATION_DIRECTIVE", "ENUM_DIRECTIVE", "EPILOGUE_DIRECTIVE", "EQUAL", "ESCAPE_SEQUENCE", "FIELD_DIRECTIVE", "FIELD_OFFSET", "FLOAT_LITERAL", "FLOAT_LITERAL_OR_ID", "HEX_DIGIT", "HEX_DIGITS", "HEX_PREFIX", "IMPLEMENTS_DIRECTIVE", "INLINE_INDEX", "INSTRUCTION_FORMAT10t", "INSTRUCTION_FORMAT10x", "INSTRUCTION_FORMAT10x_ODEX", "INSTRUCTION_FORMAT11n", "INSTRUCTION_FORMAT11x", "INSTRUCTION_FORMAT12x", "INSTRUCTION_FORMAT12x_OR_ID", "INSTRUCTION_FORMAT20bc", "INSTRUCTION_FORMAT20t", "INSTRUCTION_FORMAT21c_FIELD", "INSTRUCTION_FORMAT21c_FIELD_ODEX", "INSTRUCTION_FORMAT21c_STRING", "INSTRUCTION_FORMAT21c_TYPE", "INSTRUCTION_FORMAT21ih", "INSTRUCTION_FORMAT21lh", "INSTRUCTION_FORMAT21s", "INSTRUCTION_FORMAT21t", "INSTRUCTION_FORMAT22b", "INSTRUCTION_FORMAT22c_FIELD", "INSTRUCTION_FORMAT22c_FIELD_ODEX", "INSTRUCTION_FORMAT22c_TYPE", "INSTRUCTION_FORMAT22cs_FIELD", "INSTRUCTION_FORMAT22s", "INSTRUCTION_FORMAT22s_OR_ID", "INSTRUCTION_FORMAT22t", "INSTRUCTION_FORMAT22x", "INSTRUCTION_FORMAT23x", "INSTRUCTION_FORMAT30t", "INSTRUCTION_FORMAT31c", "INSTRUCTION_FORMAT31i", "INSTRUCTION_FORMAT31i_OR_ID", "INSTRUCTION_FORMAT31t", "INSTRUCTION_FORMAT32x", "INSTRUCTION_FORMAT35c_METHOD", "INSTRUCTION_FORMAT35c_METHOD_ODEX", "INSTRUCTION_FORMAT35c_TYPE", "INSTRUCTION_FORMAT35mi_METHOD", "INSTRUCTION_FORMAT35ms_METHOD", "INSTRUCTION_FORMAT3rc_METHOD", "INSTRUCTION_FORMAT3rc_METHOD_ODEX", "INSTRUCTION_FORMAT3rc_TYPE", "INSTRUCTION_FORMAT3rmi_METHOD", "INSTRUCTION_FORMAT3rms_METHOD", "INSTRUCTION_FORMAT51l", "INTEGER_LITERAL", "INVALID_TOKEN", "I_ACCESS_LIST", "I_ANNOTATION", "I_ANNOTATIONS", "I_ANNOTATION_ELEMENT", "I_ARRAY_ELEMENTS", "I_ARRAY_ELEMENT_SIZE", "I_CATCH", "I_CATCHALL", "I_CATCHES", "I_CLASS_DEF", "I_ENCODED_ARRAY", "I_ENCODED_ENUM", "I_ENCODED_FIELD", "I_ENCODED_METHOD", "I_END_LOCAL", "I_EPILOGUE", "I_FIELD", "I_FIELDS", "I_FIELD_INITIAL_VALUE", "I_FIELD_TYPE", "I_IMPLEMENTS", "I_LABEL", "I_LINE", "I_LOCAL", "I_LOCALS", "I_METHOD", "I_METHODS", "I_METHOD_PROTOTYPE", "I_METHOD_RETURN_TYPE", "I_ORDERED_METHOD_ITEMS", "I_PACKED_SWITCH_ELEMENTS", "I_PACKED_SWITCH_START_KEY", "I_PARAMETER", "I_PARAMETERS", "I_PARAMETER_NOT_SPECIFIED", "I_PROLOGUE", "I_REGISTERS", "I_REGISTER_LIST", "I_REGISTER_RANGE", "I_RESTART_LOCAL", "I_SOURCE", "I_SPARSE_SWITCH_ELEMENTS", "I_STATEMENT_ARRAY_DATA", "I_STATEMENT_FORMAT10t", "I_STATEMENT_FORMAT10x", "I_STATEMENT_FORMAT11n", "I_STATEMENT_FORMAT11x", "I_STATEMENT_FORMAT12x", "I_STATEMENT_FORMAT20bc", "I_STATEMENT_FORMAT20t", "I_STATEMENT_FORMAT21c_FIELD", "I_STATEMENT_FORMAT21c_STRING", "I_STATEMENT_FORMAT21c_TYPE", "I_STATEMENT_FORMAT21ih", "I_STATEMENT_FORMAT21lh", "I_STATEMENT_FORMAT21s", "I_STATEMENT_FORMAT21t", "I_STATEMENT_FORMAT22b", "I_STATEMENT_FORMAT22c_FIELD", "I_STATEMENT_FORMAT22c_TYPE", "I_STATEMENT_FORMAT22s", "I_STATEMENT_FORMAT22t", "I_STATEMENT_FORMAT22x", "I_STATEMENT_FORMAT23x", "I_STATEMENT_FORMAT30t", "I_STATEMENT_FORMAT31c", "I_STATEMENT_FORMAT31i", "I_STATEMENT_FORMAT31t", "I_STATEMENT_FORMAT32x", "I_STATEMENT_FORMAT35c_METHOD", "I_STATEMENT_FORMAT35c_TYPE", "I_STATEMENT_FORMAT3rc_METHOD", "I_STATEMENT_FORMAT3rc_TYPE", "I_STATEMENT_FORMAT51l", "I_STATEMENT_PACKED_SWITCH", "I_STATEMENT_SPARSE_SWITCH", "I_SUBANNOTATION", "I_SUPER", "LABEL", "LINE_COMMENT", "LINE_DIRECTIVE", "LOCALS_DIRECTIVE", "LOCAL_DIRECTIVE", "LONG_LITERAL", "MEMBER_NAME", "METHOD_DIRECTIVE", "NEGATIVE_INTEGER_LITERAL", "NULL_LITERAL", "OPEN_BRACE", "OPEN_PAREN", "PACKED_SWITCH_DIRECTIVE", "PARAMETER_DIRECTIVE", "PARAM_LIST", "PARAM_LIST_OR_ID", "POSITIVE_INTEGER_LITERAL", "PRIMITIVE_TYPE", "PROLOGUE_DIRECTIVE", "REGISTER", "REGISTERS_DIRECTIVE", "RESTART_LOCAL_DIRECTIVE", "SHORT_LITERAL", "SIMPLE_NAME", "SOURCE_DIRECTIVE", "SPARSE_SWITCH_DIRECTIVE", "STRING_LITERAL", "SUBANNOTATION_DIRECTIVE", "SUPER_DIRECTIVE", "VERIFICATION_ERROR_TYPE", "VOID_TYPE", "VTABLE_INDEX", "WHITE_SPACE" };
/*       */ 
/*   309 */   protected TreeAdaptor adaptor = new CommonTreeAdaptor();
/*       */ 
/*   323 */   private boolean verboseErrors = false;
/*   324 */   private boolean allowOdex = false;
/*   325 */   private int apiLevel = 15;
/*   326 */   private Opcodes opcodes = new Opcodes(this.apiLevel);
/*       */ 
/*   494 */   protected Stack<smali_file_scope> smali_file_stack = new Stack();
/*       */ 
/*  1606 */   protected Stack<statements_and_directives_scope> statements_and_directives_stack = new Stack();
/*       */ 
/* 16970 */   protected DFA33 dfa33 = new DFA33(this);
/*       */ 
/* 16983 */   static final String[] DFA33_transitionS = { "\001?\001￿\001\001\017￿\002\001\005￿\001\001\b￿\002\001\001￿\001\001\003￿\001\001\013￿,\001R￿\003\001\007￿\002\001\004￿\001\001\001￿\002\001\002￿\002\001", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "\001￿", "" };
/*       */ 
/* 17053 */   static final short[] DFA33_eot = DFA.unpackEncodedString("A￿");
/* 17054 */   static final short[] DFA33_eof = DFA.unpackEncodedString("A￿");
/* 17055 */   static final char[] DFA33_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 17056 */   static final char[] DFA33_max = DFA.unpackEncodedStringToUnsignedChars("");
/* 17057 */   static final short[] DFA33_accept = DFA.unpackEncodedString("\001￿\001\002>￿\001\001");
/* 17058 */   static final short[] DFA33_special = DFA.unpackEncodedString("");
/*       */   static final short[][] DFA33_transition;
/*       */   public static final BitSet FOLLOW_class_spec_in_smali_file1145;
/*       */   public static final BitSet FOLLOW_super_spec_in_smali_file1156;
/*       */   public static final BitSet FOLLOW_implements_spec_in_smali_file1164;
/*       */   public static final BitSet FOLLOW_source_spec_in_smali_file1173;
/*       */   public static final BitSet FOLLOW_method_in_smali_file1181;
/*       */   public static final BitSet FOLLOW_field_in_smali_file1187;
/*       */   public static final BitSet FOLLOW_annotation_in_smali_file1193;
/*       */   public static final BitSet FOLLOW_EOF_in_smali_file1204;
/*       */   public static final BitSet FOLLOW_CLASS_DIRECTIVE_in_class_spec1291;
/*       */   public static final BitSet FOLLOW_access_list_in_class_spec1293;
/*       */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_class_spec1295;
/*       */   public static final BitSet FOLLOW_SUPER_DIRECTIVE_in_super_spec1313;
/*       */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_super_spec1315;
/*       */   public static final BitSet FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1334;
/*       */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1336;
/*       */   public static final BitSet FOLLOW_SOURCE_DIRECTIVE_in_source_spec1355;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_source_spec1357;
/*       */   public static final BitSet FOLLOW_ACCESS_SPEC_in_access_list1376;
/*       */   public static final BitSet FOLLOW_FIELD_DIRECTIVE_in_field1407;
/*       */   public static final BitSet FOLLOW_access_list_in_field1409;
/*       */   public static final BitSet FOLLOW_member_name_in_field1411;
/*       */   public static final BitSet FOLLOW_COLON_in_field1413;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field1415;
/*       */   public static final BitSet FOLLOW_EQUAL_in_field1418;
/*       */   public static final BitSet FOLLOW_literal_in_field1420;
/*       */   public static final BitSet FOLLOW_annotation_in_field1433;
/*       */   public static final BitSet FOLLOW_END_FIELD_DIRECTIVE_in_field1447;
/*       */   public static final BitSet FOLLOW_METHOD_DIRECTIVE_in_method1558;
/*       */   public static final BitSet FOLLOW_access_list_in_method1560;
/*       */   public static final BitSet FOLLOW_member_name_in_method1562;
/*       */   public static final BitSet FOLLOW_method_prototype_in_method1564;
/*       */   public static final BitSet FOLLOW_statements_and_directives_in_method1566;
/*       */   public static final BitSet FOLLOW_END_METHOD_DIRECTIVE_in_method1572;
/*       */   public static final BitSet FOLLOW_ordered_method_item_in_statements_and_directives1617;
/*       */   public static final BitSet FOLLOW_registers_directive_in_statements_and_directives1625;
/*       */   public static final BitSet FOLLOW_catch_directive_in_statements_and_directives1633;
/*       */   public static final BitSet FOLLOW_catchall_directive_in_statements_and_directives1641;
/*       */   public static final BitSet FOLLOW_parameter_directive_in_statements_and_directives1649;
/*       */   public static final BitSet FOLLOW_annotation_in_statements_and_directives1657;
/*       */   public static final BitSet FOLLOW_label_in_ordered_method_item1742;
/*       */   public static final BitSet FOLLOW_instruction_in_ordered_method_item1748;
/*       */   public static final BitSet FOLLOW_debug_directive_in_ordered_method_item1754;
/*       */   public static final BitSet FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1774;
/*       */   public static final BitSet FOLLOW_integral_literal_in_registers_directive1778;
/*       */   public static final BitSet FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1798;
/*       */   public static final BitSet FOLLOW_integral_literal_in_registers_directive1802;
/*       */   public static final BitSet FOLLOW_SIMPLE_NAME_in_simple_name1836;
/*       */   public static final BitSet FOLLOW_ACCESS_SPEC_in_simple_name1842;
/*       */   public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1853;
/*       */   public static final BitSet FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1864;
/*       */   public static final BitSet FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1875;
/*       */   public static final BitSet FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1886;
/*       */   public static final BitSet FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1897;
/*       */   public static final BitSet FOLLOW_BOOL_LITERAL_in_simple_name1908;
/*       */   public static final BitSet FOLLOW_NULL_LITERAL_in_simple_name1919;
/*       */   public static final BitSet FOLLOW_REGISTER_in_simple_name1930;
/*       */   public static final BitSet FOLLOW_PARAM_LIST_OR_ID_in_simple_name1941;
/*       */   public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_simple_name1952;
/*       */   public static final BitSet FOLLOW_VOID_TYPE_in_simple_name1963;
/*       */   public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_simple_name1974;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name1985;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name1996;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2007;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2018;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2029;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2040;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2051;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2062;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2073;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2084;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2095;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2106;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2117;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2128;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2139;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2150;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2161;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2172;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2183;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2194;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2205;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2216;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2227;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2238;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2249;
/*       */   public static final BitSet FOLLOW_simple_name_in_member_name2264;
/*       */   public static final BitSet FOLLOW_MEMBER_NAME_in_member_name2270;
/*       */   public static final BitSet FOLLOW_OPEN_PAREN_in_method_prototype2285;
/*       */   public static final BitSet FOLLOW_param_list_in_method_prototype2287;
/*       */   public static final BitSet FOLLOW_CLOSE_PAREN_in_method_prototype2289;
/*       */   public static final BitSet FOLLOW_type_descriptor_in_method_prototype2291;
/*       */   public static final BitSet FOLLOW_PARAM_LIST_in_param_list2321;
/*       */   public static final BitSet FOLLOW_PARAM_LIST_OR_ID_in_param_list2331;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_param_list2341;
/*       */   public static final BitSet FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2418;
/*       */   public static final BitSet FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2429;
/*       */   public static final BitSet FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2444;
/*       */   public static final BitSet FOLLOW_FLOAT_LITERAL_in_float_literal2455;
/*       */   public static final BitSet FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2465;
/*       */   public static final BitSet FOLLOW_DOUBLE_LITERAL_in_double_literal2476;
/*       */   public static final BitSet FOLLOW_LONG_LITERAL_in_literal2486;
/*       */   public static final BitSet FOLLOW_integer_literal_in_literal2492;
/*       */   public static final BitSet FOLLOW_SHORT_LITERAL_in_literal2498;
/*       */   public static final BitSet FOLLOW_BYTE_LITERAL_in_literal2504;
/*       */   public static final BitSet FOLLOW_float_literal_in_literal2510;
/*       */   public static final BitSet FOLLOW_double_literal_in_literal2516;
/*       */   public static final BitSet FOLLOW_CHAR_LITERAL_in_literal2522;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_literal2528;
/*       */   public static final BitSet FOLLOW_BOOL_LITERAL_in_literal2534;
/*       */   public static final BitSet FOLLOW_NULL_LITERAL_in_literal2540;
/*       */   public static final BitSet FOLLOW_array_literal_in_literal2546;
/*       */   public static final BitSet FOLLOW_subannotation_in_literal2552;
/*       */   public static final BitSet FOLLOW_type_field_method_literal_in_literal2558;
/*       */   public static final BitSet FOLLOW_enum_literal_in_literal2564;
/*       */   public static final BitSet FOLLOW_integer_literal_in_parsed_integer_literal2577;
/*       */   public static final BitSet FOLLOW_LONG_LITERAL_in_integral_literal2589;
/*       */   public static final BitSet FOLLOW_integer_literal_in_integral_literal2595;
/*       */   public static final BitSet FOLLOW_SHORT_LITERAL_in_integral_literal2601;
/*       */   public static final BitSet FOLLOW_CHAR_LITERAL_in_integral_literal2607;
/*       */   public static final BitSet FOLLOW_BYTE_LITERAL_in_integral_literal2613;
/*       */   public static final BitSet FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2623;
/*       */   public static final BitSet FOLLOW_integer_literal_in_fixed_32bit_literal2629;
/*       */   public static final BitSet FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2635;
/*       */   public static final BitSet FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2641;
/*       */   public static final BitSet FOLLOW_float_literal_in_fixed_32bit_literal2647;
/*       */   public static final BitSet FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2653;
/*       */   public static final BitSet FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2659;
/*       */   public static final BitSet FOLLOW_integer_literal_in_fixed_literal2669;
/*       */   public static final BitSet FOLLOW_LONG_LITERAL_in_fixed_literal2675;
/*       */   public static final BitSet FOLLOW_SHORT_LITERAL_in_fixed_literal2681;
/*       */   public static final BitSet FOLLOW_BYTE_LITERAL_in_fixed_literal2687;
/*       */   public static final BitSet FOLLOW_float_literal_in_fixed_literal2693;
/*       */   public static final BitSet FOLLOW_double_literal_in_fixed_literal2699;
/*       */   public static final BitSet FOLLOW_CHAR_LITERAL_in_fixed_literal2705;
/*       */   public static final BitSet FOLLOW_BOOL_LITERAL_in_fixed_literal2711;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_array_literal2721;
/*       */   public static final BitSet FOLLOW_literal_in_array_literal2724;
/*       */   public static final BitSet FOLLOW_COMMA_in_array_literal2727;
/*       */   public static final BitSet FOLLOW_literal_in_array_literal2729;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_array_literal2737;
/*       */   public static final BitSet FOLLOW_simple_name_in_annotation_element2761;
/*       */   public static final BitSet FOLLOW_EQUAL_in_annotation_element2763;
/*       */   public static final BitSet FOLLOW_literal_in_annotation_element2765;
/*       */   public static final BitSet FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2790;
/*       */   public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_annotation2792;
/*       */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_annotation2794;
/*       */   public static final BitSet FOLLOW_annotation_element_in_annotation2800;
/*       */   public static final BitSet FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2803;
/*       */   public static final BitSet FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation2836;
/*       */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_subannotation2838;
/*       */   public static final BitSet FOLLOW_annotation_element_in_subannotation2840;
/*       */   public static final BitSet FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation2843;
/*       */   public static final BitSet FOLLOW_ENUM_DIRECTIVE_in_enum_literal2869;
/*       */   public static final BitSet FOLLOW_reference_type_descriptor_in_enum_literal2871;
/*       */   public static final BitSet FOLLOW_ARROW_in_enum_literal2873;
/*       */   public static final BitSet FOLLOW_simple_name_in_enum_literal2875;
/*       */   public static final BitSet FOLLOW_COLON_in_enum_literal2877;
/*       */   public static final BitSet FOLLOW_reference_type_descriptor_in_enum_literal2879;
/*       */   public static final BitSet FOLLOW_reference_type_descriptor_in_type_field_method_literal2903;
/*       */   public static final BitSet FOLLOW_ARROW_in_type_field_method_literal2911;
/*       */   public static final BitSet FOLLOW_member_name_in_type_field_method_literal2921;
/*       */   public static final BitSet FOLLOW_COLON_in_type_field_method_literal2923;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal2925;
/*       */   public static final BitSet FOLLOW_member_name_in_type_field_method_literal2947;
/*       */   public static final BitSet FOLLOW_method_prototype_in_type_field_method_literal2949;
/*       */   public static final BitSet FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal2991;
/*       */   public static final BitSet FOLLOW_VOID_TYPE_in_type_field_method_literal2997;
/*       */   public static final BitSet FOLLOW_reference_type_descriptor_in_fully_qualified_method3007;
/*       */   public static final BitSet FOLLOW_ARROW_in_fully_qualified_method3009;
/*       */   public static final BitSet FOLLOW_member_name_in_fully_qualified_method3011;
/*       */   public static final BitSet FOLLOW_method_prototype_in_fully_qualified_method3013;
/*       */   public static final BitSet FOLLOW_reference_type_descriptor_in_fully_qualified_field3033;
/*       */   public static final BitSet FOLLOW_ARROW_in_fully_qualified_field3035;
/*       */   public static final BitSet FOLLOW_member_name_in_fully_qualified_field3037;
/*       */   public static final BitSet FOLLOW_COLON_in_fully_qualified_field3039;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_fully_qualified_field3041;
/*       */   public static final BitSet FOLLOW_COLON_in_label3061;
/*       */   public static final BitSet FOLLOW_simple_name_in_label3063;
/*       */   public static final BitSet FOLLOW_COLON_in_label_ref3082;
/*       */   public static final BitSet FOLLOW_simple_name_in_label_ref3084;
/*       */   public static final BitSet FOLLOW_REGISTER_in_register_list3098;
/*       */   public static final BitSet FOLLOW_COMMA_in_register_list3101;
/*       */   public static final BitSet FOLLOW_REGISTER_in_register_list3103;
/*       */   public static final BitSet FOLLOW_REGISTER_in_register_range3138;
/*       */   public static final BitSet FOLLOW_DOTDOT_in_register_range3141;
/*       */   public static final BitSet FOLLOW_REGISTER_in_register_range3145;
/*       */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3174;
/*       */   public static final BitSet FOLLOW_fully_qualified_field_in_verification_error_reference3178;
/*       */   public static final BitSet FOLLOW_fully_qualified_method_in_verification_error_reference3182;
/*       */   public static final BitSet FOLLOW_CATCH_DIRECTIVE_in_catch_directive3192;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_catch_directive3194;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_catch_directive3196;
/*       */   public static final BitSet FOLLOW_label_ref_in_catch_directive3200;
/*       */   public static final BitSet FOLLOW_DOTDOT_in_catch_directive3202;
/*       */   public static final BitSet FOLLOW_label_ref_in_catch_directive3206;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_catch_directive3208;
/*       */   public static final BitSet FOLLOW_label_ref_in_catch_directive3212;
/*       */   public static final BitSet FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3244;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_catchall_directive3246;
/*       */   public static final BitSet FOLLOW_label_ref_in_catchall_directive3250;
/*       */   public static final BitSet FOLLOW_DOTDOT_in_catchall_directive3252;
/*       */   public static final BitSet FOLLOW_label_ref_in_catchall_directive3256;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_catchall_directive3258;
/*       */   public static final BitSet FOLLOW_label_ref_in_catchall_directive3262;
/*       */   public static final BitSet FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3301;
/*       */   public static final BitSet FOLLOW_REGISTER_in_parameter_directive3303;
/*       */   public static final BitSet FOLLOW_COMMA_in_parameter_directive3306;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_parameter_directive3308;
/*       */   public static final BitSet FOLLOW_annotation_in_parameter_directive3319;
/*       */   public static final BitSet FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3332;
/*       */   public static final BitSet FOLLOW_line_directive_in_debug_directive3405;
/*       */   public static final BitSet FOLLOW_local_directive_in_debug_directive3411;
/*       */   public static final BitSet FOLLOW_end_local_directive_in_debug_directive3417;
/*       */   public static final BitSet FOLLOW_restart_local_directive_in_debug_directive3423;
/*       */   public static final BitSet FOLLOW_prologue_directive_in_debug_directive3429;
/*       */   public static final BitSet FOLLOW_epilogue_directive_in_debug_directive3435;
/*       */   public static final BitSet FOLLOW_source_directive_in_debug_directive3441;
/*       */   public static final BitSet FOLLOW_LINE_DIRECTIVE_in_line_directive3451;
/*       */   public static final BitSet FOLLOW_integral_literal_in_line_directive3453;
/*       */   public static final BitSet FOLLOW_LOCAL_DIRECTIVE_in_local_directive3476;
/*       */   public static final BitSet FOLLOW_REGISTER_in_local_directive3478;
/*       */   public static final BitSet FOLLOW_COMMA_in_local_directive3481;
/*       */   public static final BitSet FOLLOW_NULL_LITERAL_in_local_directive3484;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_local_directive3490;
/*       */   public static final BitSet FOLLOW_COLON_in_local_directive3493;
/*       */   public static final BitSet FOLLOW_VOID_TYPE_in_local_directive3496;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_local_directive3500;
/*       */   public static final BitSet FOLLOW_COMMA_in_local_directive3534;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_local_directive3538;
/*       */   public static final BitSet FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3580;
/*       */   public static final BitSet FOLLOW_REGISTER_in_end_local_directive3582;
/*       */   public static final BitSet FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3605;
/*       */   public static final BitSet FOLLOW_REGISTER_in_restart_local_directive3607;
/*       */   public static final BitSet FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3630;
/*       */   public static final BitSet FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3651;
/*       */   public static final BitSet FOLLOW_SOURCE_DIRECTIVE_in_source_directive3672;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_source_directive3674;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x3699;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x3705;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s3720;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s3726;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i3741;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i3747;
/*       */   public static final BitSet FOLLOW_insn_format10t_in_instruction3764;
/*       */   public static final BitSet FOLLOW_insn_format10x_in_instruction3770;
/*       */   public static final BitSet FOLLOW_insn_format10x_odex_in_instruction3776;
/*       */   public static final BitSet FOLLOW_insn_format11n_in_instruction3782;
/*       */   public static final BitSet FOLLOW_insn_format11x_in_instruction3788;
/*       */   public static final BitSet FOLLOW_insn_format12x_in_instruction3794;
/*       */   public static final BitSet FOLLOW_insn_format20bc_in_instruction3800;
/*       */   public static final BitSet FOLLOW_insn_format20t_in_instruction3806;
/*       */   public static final BitSet FOLLOW_insn_format21c_field_in_instruction3812;
/*       */   public static final BitSet FOLLOW_insn_format21c_field_odex_in_instruction3818;
/*       */   public static final BitSet FOLLOW_insn_format21c_string_in_instruction3824;
/*       */   public static final BitSet FOLLOW_insn_format21c_type_in_instruction3830;
/*       */   public static final BitSet FOLLOW_insn_format21ih_in_instruction3836;
/*       */   public static final BitSet FOLLOW_insn_format21lh_in_instruction3842;
/*       */   public static final BitSet FOLLOW_insn_format21s_in_instruction3848;
/*       */   public static final BitSet FOLLOW_insn_format21t_in_instruction3854;
/*       */   public static final BitSet FOLLOW_insn_format22b_in_instruction3860;
/*       */   public static final BitSet FOLLOW_insn_format22c_field_in_instruction3866;
/*       */   public static final BitSet FOLLOW_insn_format22c_field_odex_in_instruction3872;
/*       */   public static final BitSet FOLLOW_insn_format22c_type_in_instruction3878;
/*       */   public static final BitSet FOLLOW_insn_format22cs_field_in_instruction3884;
/*       */   public static final BitSet FOLLOW_insn_format22s_in_instruction3890;
/*       */   public static final BitSet FOLLOW_insn_format22t_in_instruction3896;
/*       */   public static final BitSet FOLLOW_insn_format22x_in_instruction3902;
/*       */   public static final BitSet FOLLOW_insn_format23x_in_instruction3908;
/*       */   public static final BitSet FOLLOW_insn_format30t_in_instruction3914;
/*       */   public static final BitSet FOLLOW_insn_format31c_in_instruction3920;
/*       */   public static final BitSet FOLLOW_insn_format31i_in_instruction3926;
/*       */   public static final BitSet FOLLOW_insn_format31t_in_instruction3932;
/*       */   public static final BitSet FOLLOW_insn_format32x_in_instruction3938;
/*       */   public static final BitSet FOLLOW_insn_format35c_method_in_instruction3944;
/*       */   public static final BitSet FOLLOW_insn_format35c_type_in_instruction3950;
/*       */   public static final BitSet FOLLOW_insn_format35c_method_odex_in_instruction3956;
/*       */   public static final BitSet FOLLOW_insn_format35mi_method_in_instruction3962;
/*       */   public static final BitSet FOLLOW_insn_format35ms_method_in_instruction3968;
/*       */   public static final BitSet FOLLOW_insn_format3rc_method_in_instruction3974;
/*       */   public static final BitSet FOLLOW_insn_format3rc_method_odex_in_instruction3980;
/*       */   public static final BitSet FOLLOW_insn_format3rc_type_in_instruction3986;
/*       */   public static final BitSet FOLLOW_insn_format3rmi_method_in_instruction3992;
/*       */   public static final BitSet FOLLOW_insn_format3rms_method_in_instruction3998;
/*       */   public static final BitSet FOLLOW_insn_format51l_in_instruction4004;
/*       */   public static final BitSet FOLLOW_insn_array_data_directive_in_instruction4010;
/*       */   public static final BitSet FOLLOW_insn_packed_switch_directive_in_instruction4016;
/*       */   public static final BitSet FOLLOW_insn_sparse_switch_directive_in_instruction4022;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4042;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_format10t4044;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4074;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4102;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4123;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format11n4125;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format11n4127;
/*       */   public static final BitSet FOLLOW_integral_literal_in_insn_format11n4129;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4161;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format11x4163;
/*       */   public static final BitSet FOLLOW_instruction_format12x_in_insn_format12x4193;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format12x4195;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format12x4197;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format12x4199;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4231;
/*       */   public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4233;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format20bc4235;
/*       */   public static final BitSet FOLLOW_verification_error_reference_in_insn_format20bc4237;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4274;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_format20t4276;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4306;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field4308;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21c_field4310;
/*       */   public static final BitSet FOLLOW_fully_qualified_field_in_insn_format21c_field4312;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4344;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field_odex4346;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21c_field_odex4348;
/*       */   public static final BitSet FOLLOW_fully_qualified_field_in_insn_format21c_field_odex4350;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4388;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21c_string4390;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21c_string4392;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_insn_format21c_string4394;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4426;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21c_type4428;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21c_type4430;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4432;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4464;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21ih4466;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21ih4468;
/*       */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21ih4470;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4502;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21lh4504;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21lh4506;
/*       */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21lh4508;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s4540;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21s4542;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21s4544;
/*       */   public static final BitSet FOLLOW_integral_literal_in_insn_format21s4546;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t4578;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format21t4580;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format21t4582;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_format21t4584;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b4616;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22b4618;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22b4620;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22b4622;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22b4624;
/*       */   public static final BitSet FOLLOW_integral_literal_in_insn_format22b4626;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field4660;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field4662;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22c_field4664;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field4666;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22c_field4668;
/*       */   public static final BitSet FOLLOW_fully_qualified_field_in_insn_format22c_field4670;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex4704;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field_odex4706;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22c_field_odex4708;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field_odex4710;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22c_field_odex4712;
/*       */   public static final BitSet FOLLOW_fully_qualified_field_in_insn_format22c_field_odex4714;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type4754;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type4756;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22c_type4758;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type4760;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22c_type4762;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type4764;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field4798;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22cs_field4800;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22cs_field4802;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22cs_field4804;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22cs_field4806;
/*       */   public static final BitSet FOLLOW_FIELD_OFFSET_in_insn_format22cs_field4808;
/*       */   public static final BitSet FOLLOW_instruction_format22s_in_insn_format22s4829;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22s4831;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22s4833;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22s4835;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22s4837;
/*       */   public static final BitSet FOLLOW_integral_literal_in_insn_format22s4839;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t4873;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22t4875;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22t4877;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22t4879;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22t4881;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_format22t4883;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x4917;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22x4919;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format22x4921;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format22x4923;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x4955;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format23x4957;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format23x4959;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format23x4961;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format23x4963;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format23x4965;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t4999;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_format30t5001;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5031;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format31c5033;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format31c5035;
/*       */   public static final BitSet FOLLOW_STRING_LITERAL_in_insn_format31c5037;
/*       */   public static final BitSet FOLLOW_instruction_format31i_in_insn_format31i5068;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format31i5070;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format31i5072;
/*       */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format31i5074;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5106;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format31t5108;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format31t5110;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_format31t5112;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5144;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format32x5146;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format32x5148;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format32x5150;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method5182;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_method5184;
/*       */   public static final BitSet FOLLOW_register_list_in_insn_format35c_method5186;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_method5188;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format35c_method5190;
/*       */   public static final BitSet FOLLOW_fully_qualified_method_in_insn_format35c_method5192;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5224;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_type5226;
/*       */   public static final BitSet FOLLOW_register_list_in_insn_format35c_type5228;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_type5230;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format35c_type5232;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5234;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5266;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5268;
/*       */   public static final BitSet FOLLOW_register_list_in_insn_format35c_method_odex5270;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5272;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format35c_method_odex5274;
/*       */   public static final BitSet FOLLOW_fully_qualified_method_in_insn_format35c_method_odex5276;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5297;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35mi_method5299;
/*       */   public static final BitSet FOLLOW_register_list_in_insn_format35mi_method5301;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5303;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format35mi_method5305;
/*       */   public static final BitSet FOLLOW_INLINE_INDEX_in_insn_format35mi_method5307;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5328;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format35ms_method5330;
/*       */   public static final BitSet FOLLOW_register_list_in_insn_format35ms_method5332;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5334;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format35ms_method5336;
/*       */   public static final BitSet FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5338;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5359;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_method5361;
/*       */   public static final BitSet FOLLOW_register_range_in_insn_format3rc_method5363;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5365;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format3rc_method5367;
/*       */   public static final BitSet FOLLOW_fully_qualified_method_in_insn_format3rc_method5369;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5401;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5403;
/*       */   public static final BitSet FOLLOW_register_list_in_insn_format3rc_method_odex5405;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5407;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format3rc_method_odex5409;
/*       */   public static final BitSet FOLLOW_fully_qualified_method_in_insn_format3rc_method_odex5411;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5432;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rc_type5434;
/*       */   public static final BitSet FOLLOW_register_range_in_insn_format3rc_type5436;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5438;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format3rc_type5440;
/*       */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5442;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method5474;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rmi_method5476;
/*       */   public static final BitSet FOLLOW_register_range_in_insn_format3rmi_method5478;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method5480;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format3rmi_method5482;
/*       */   public static final BitSet FOLLOW_INLINE_INDEX_in_insn_format3rmi_method5484;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method5505;
/*       */   public static final BitSet FOLLOW_OPEN_BRACE_in_insn_format3rms_method5507;
/*       */   public static final BitSet FOLLOW_register_range_in_insn_format3rms_method5509;
/*       */   public static final BitSet FOLLOW_CLOSE_BRACE_in_insn_format3rms_method5511;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format3rms_method5513;
/*       */   public static final BitSet FOLLOW_VTABLE_INDEX_in_insn_format3rms_method5515;
/*       */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l5536;
/*       */   public static final BitSet FOLLOW_REGISTER_in_insn_format51l5538;
/*       */   public static final BitSet FOLLOW_COMMA_in_insn_format51l5540;
/*       */   public static final BitSet FOLLOW_fixed_literal_in_insn_format51l5542;
/*       */   public static final BitSet FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive5569;
/*       */   public static final BitSet FOLLOW_parsed_integer_literal_in_insn_array_data_directive5575;
/*       */   public static final BitSet FOLLOW_fixed_literal_in_insn_array_data_directive5587;
/*       */   public static final BitSet FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive5590;
/*       */   public static final BitSet FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive5636;
/*       */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive5642;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_packed_switch_directive5648;
/*       */   public static final BitSet FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive5655;
/*       */   public static final BitSet FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive5729;
/*       */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive5736;
/*       */   public static final BitSet FOLLOW_ARROW_in_insn_sparse_switch_directive5738;
/*       */   public static final BitSet FOLLOW_label_ref_in_insn_sparse_switch_directive5740;
/*       */   public static final BitSet FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive5748;
/*       */ 
/*       */   public smaliParser(TokenStream input)
/*       */   {
/*   303 */     this(input, new RecognizerSharedState());
/*       */   }
/*       */   public smaliParser(TokenStream input, RecognizerSharedState state) {
/*   306 */     super(input, state);
/*       */   }
/*       */ 
/*       */   public String[] getTokenNames()
/*       */   {
/*   317 */     return tokenNames; } 
/*   318 */   public String getGrammarFileName() { return "/home/jesusfreke/projects/smali/smali/src/main/antlr3/smaliParser.g";
/*       */   }
/*       */ 
/*       */   public void setVerboseErrors(boolean verboseErrors)
/*       */   {
/*   329 */     this.verboseErrors = verboseErrors;
/*       */   }
/*       */ 
/*       */   public void setAllowOdex(boolean allowOdex) {
/*   333 */     this.allowOdex = allowOdex;
/*       */   }
/*       */ 
/*       */   public void setApiLevel(int apiLevel) {
/*   337 */     this.opcodes = new Opcodes(apiLevel);
/*   338 */     this.apiLevel = apiLevel;
/*       */   }
/*       */ 
/*       */   public String getErrorMessage(RecognitionException e, String[] tokenNames)
/*       */   {
/*   344 */     if (this.verboseErrors) {
/*   345 */       List stack = getRuleInvocationStack(e, getClass().getName());
/*   346 */       String msg = null;
/*       */ 
/*   348 */       if ((e instanceof NoViableAltException)) {
/*   349 */         NoViableAltException nvae = (NoViableAltException)e;
/*   350 */         msg = " no viable alt; token=" + getTokenErrorDisplay(e.token) + " (decision=" + nvae.decisionNumber + " state " + nvae.stateNumber + ")" + " decision=<<" + nvae.grammarDecisionDescription + ">>";
/*       */       }
/*       */       else
/*       */       {
/*   355 */         msg = super.getErrorMessage(e, tokenNames);
/*       */       }
/*       */ 
/*   358 */       return stack + " " + msg;
/*       */     }
/*   360 */     return super.getErrorMessage(e, tokenNames);
/*       */   }
/*       */ 
/*       */   public String getTokenErrorDisplay(Token t)
/*       */   {
/*   365 */     if (!this.verboseErrors) {
/*   366 */       String s = t.getText();
/*   367 */       if (s == null) {
/*   368 */         if (t.getType() == -1) {
/*   369 */           s = "<EOF>";
/*       */         }
/*       */         else {
/*   372 */           s = "<" + tokenNames[t.getType()] + ">";
/*       */         }
/*       */       }
/*   375 */       s = s.replaceAll("\n", "\\\\n");
/*   376 */       s = s.replaceAll("\r", "\\\\r");
/*   377 */       s = s.replaceAll("\t", "\\\\t");
/*   378 */       return "'" + s + "'";
/*       */     }
/*       */ 
/*   381 */     CommonToken ct = (CommonToken)t;
/*       */ 
/*   383 */     String channelStr = "";
/*   384 */     if (t.getChannel() > 0) {
/*   385 */       channelStr = ",channel=" + t.getChannel();
/*       */     }
/*   387 */     String txt = t.getText();
/*   388 */     if (txt != null) {
/*   389 */       txt = txt.replaceAll("\n", "\\\\n");
/*   390 */       txt = txt.replaceAll("\r", "\\\\r");
/*   391 */       txt = txt.replaceAll("\t", "\\\\t");
/*       */     }
/*       */     else {
/*   394 */       txt = "<no text>";
/*       */     }
/*   396 */     return "[@" + t.getTokenIndex() + "," + ct.getStartIndex() + ":" + ct.getStopIndex() + "='" + txt + "',<" + tokenNames[t.getType()] + ">" + channelStr + "," + t.getLine() + ":" + t.getCharPositionInLine() + "]";
/*       */   }
/*       */ 
/*       */   public String getErrorHeader(RecognitionException e) {
/*   400 */     return getSourceName() + "[" + e.line + "," + e.charPositionInLine + "]";
/*       */   }
/*       */ 
/*       */   private CommonTree buildTree(int type, String text, List<CommonTree> children) {
/*   404 */     CommonTree root = new CommonTree(new CommonToken(type, text));
/*   405 */     for (CommonTree child : children) {
/*   406 */       root.addChild(child);
/*       */     }
/*   408 */     return root;
/*       */   }
/*       */ 
/*       */   private CommonToken getParamListSubToken(CommonToken baseToken, String str, int typeStartIndex) {
/*   412 */     CommonToken token = new CommonToken(baseToken);
/*   413 */     token.setStartIndex(baseToken.getStartIndex() + typeStartIndex);
/*       */ 
/*   415 */     switch (str.charAt(typeStartIndex))
/*       */     {
/*       */     case 'B':
/*       */     case 'C':
/*       */     case 'D':
/*       */     case 'F':
/*       */     case 'I':
/*       */     case 'J':
/*       */     case 'S':
/*       */     case 'Z':
/*   425 */       token.setType(199);
/*   426 */       token.setText(str.substring(typeStartIndex, typeStartIndex + 1));
/*   427 */       token.setStopIndex(baseToken.getStartIndex() + typeStartIndex);
/*   428 */       break;
/*       */     case 'L':
/*   432 */       int i = typeStartIndex;
/*       */       do i++; while (str.charAt(i) != ';');
/*       */ 
/*   435 */       token.setType(26);
/*   436 */       token.setText(str.substring(typeStartIndex, i + 1));
/*   437 */       token.setStopIndex(baseToken.getStartIndex() + i);
/*   438 */       break;
/*       */     case '[':
/*   442 */       int i = typeStartIndex;
/*       */       do i++; while (str.charAt(i) == '[');
/*       */ 
/*   445 */       while ((str.charAt(i++) == 'L') && 
/*   446 */         (str.charAt(i++) != ';'));
/*   449 */       token.setType(8);
/*   450 */       token.setText(str.substring(typeStartIndex, i));
/*   451 */       token.setStopIndex(baseToken.getStartIndex() + i - 1);
/*   452 */       break;
/*       */     case 'E':
/*       */     case 'G':
/*       */     case 'H':
/*       */     case 'K':
/*       */     case 'M':
/*       */     case 'N':
/*       */     case 'O':
/*       */     case 'P':
/*       */     case 'Q':
/*       */     case 'R':
/*       */     case 'T':
/*       */     case 'U':
/*       */     case 'V':
/*       */     case 'W':
/*       */     case 'X':
/*       */     case 'Y':
/*       */     default:
/*   455 */       throw new RuntimeException(String.format("Invalid character '%c' in param list \"%s\" at position %d", new Object[] { Character.valueOf(str.charAt(typeStartIndex)), str, Integer.valueOf(typeStartIndex) }));
/*       */     }
/*       */ 
/*   458 */     return token;
/*       */   }
/*       */ 
/*       */   private CommonTree parseParamList(CommonToken paramListToken) {
/*   462 */     String paramList = paramListToken.getText();
/*   463 */     CommonTree root = new CommonTree();
/*       */ 
/*   465 */     int startIndex = paramListToken.getStartIndex();
/*       */ 
/*   467 */     int i = 0;
/*   468 */     while (i < paramList.length()) {
/*   469 */       CommonToken token = getParamListSubToken(paramListToken, paramList, i);
/*   470 */       root.addChild(new CommonTree(token));
/*   471 */       i += token.getText().length();
/*       */     }
/*       */ 
/*   474 */     if (root.getChildCount() == 0) {
/*   475 */       return null;
/*       */     }
/*   477 */     return root;
/*       */   }
/*       */ 
/*       */   private void throwOdexedInstructionException(IntStream input, String odexedInstruction)
/*       */     throws OdexedInstructionException
/*       */   {
/*   484 */     throw new OdexedInstructionException(input, odexedInstruction);
/*       */   }
/*       */ 
/*       */   public final smali_file_return smali_file()
/*       */     throws RecognitionException
/*       */   {
/*   506 */     this.smali_file_stack.push(new smali_file_scope());
/*   507 */     smali_file_return retval = new smali_file_return();
/*   508 */     retval.start = this.input.LT(1);
/*       */ 
/*   510 */     CommonTree root_0 = null;
/*       */ 
/*   512 */     Token EOF8 = null;
/*   513 */     ParserRuleReturnScope class_spec1 = null;
/*   514 */     ParserRuleReturnScope super_spec2 = null;
/*   515 */     ParserRuleReturnScope implements_spec3 = null;
/*   516 */     ParserRuleReturnScope source_spec4 = null;
/*   517 */     ParserRuleReturnScope method5 = null;
/*   518 */     ParserRuleReturnScope field6 = null;
/*   519 */     ParserRuleReturnScope annotation7 = null;
/*       */ 
/*   521 */     CommonTree EOF8_tree = null;
/*   522 */     RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
/*   523 */     RewriteRuleSubtreeStream stream_field = new RewriteRuleSubtreeStream(this.adaptor, "rule field");
/*   524 */     RewriteRuleSubtreeStream stream_annotation = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
/*   525 */     RewriteRuleSubtreeStream stream_super_spec = new RewriteRuleSubtreeStream(this.adaptor, "rule super_spec");
/*   526 */     RewriteRuleSubtreeStream stream_implements_spec = new RewriteRuleSubtreeStream(this.adaptor, "rule implements_spec");
/*   527 */     RewriteRuleSubtreeStream stream_source_spec = new RewriteRuleSubtreeStream(this.adaptor, "rule source_spec");
/*   528 */     RewriteRuleSubtreeStream stream_method = new RewriteRuleSubtreeStream(this.adaptor, "rule method");
/*   529 */     RewriteRuleSubtreeStream stream_class_spec = new RewriteRuleSubtreeStream(this.adaptor, "rule class_spec");
/*       */ 
/*   531 */     ((smali_file_scope)this.smali_file_stack.peek()).hasClassSpec = (((smali_file_scope)this.smali_file_stack.peek()).hasSuperSpec = ((smali_file_scope)this.smali_file_stack.peek()).hasSourceSpec = 0);
/*   532 */     ((smali_file_scope)this.smali_file_stack.peek()).classAnnotations = new ArrayList();
/*       */     try
/*       */     {
/*   539 */       int cnt1 = 0;
/*       */       while (true)
/*       */       {
/*   542 */         int alt1 = 8;
/*   543 */         int LA1_0 = this.input.LA(1);
/*   544 */         if ((LA1_0 == 27) && (!((smali_file_scope)this.smali_file_stack.peek()).hasClassSpec)) {
/*   545 */           alt1 = 1;
/*       */         }
/*   547 */         else if ((LA1_0 == 210) && (!((smali_file_scope)this.smali_file_stack.peek()).hasSuperSpec)) {
/*   548 */           alt1 = 2;
/*       */         }
/*   550 */         else if (LA1_0 == 56) {
/*   551 */           alt1 = 3;
/*       */         }
/*   553 */         else if ((LA1_0 == 206) && (!((smali_file_scope)this.smali_file_stack.peek()).hasSourceSpec)) {
/*   554 */           alt1 = 4;
/*       */         }
/*   556 */         else if (LA1_0 == 189) {
/*   557 */           alt1 = 5;
/*       */         }
/*   559 */         else if (LA1_0 == 49) {
/*   560 */           alt1 = 6;
/*       */         }
/*   562 */         else if (LA1_0 == 5) {
/*   563 */           alt1 = 7;
/*       */         }
/*       */ 
/*   566 */         switch (alt1)
/*       */         {
/*       */         case 1:
/*   570 */           if (((smali_file_scope)this.smali_file_stack.peek()).hasClassSpec) {
/*   571 */             throw new FailedPredicateException(this.input, "smali_file", "!$smali_file::hasClassSpec");
/*       */           }
/*   573 */           pushFollow(FOLLOW_class_spec_in_smali_file1145);
/*   574 */           class_spec1 = class_spec();
/*   575 */           this.state._fsp -= 1;
/*       */ 
/*   577 */           stream_class_spec.add(class_spec1.getTree());
/*   578 */           ((smali_file_scope)this.smali_file_stack.peek()).hasClassSpec = true;
/*       */ 
/*   580 */           break;
/*       */         case 2:
/*   584 */           if (((smali_file_scope)this.smali_file_stack.peek()).hasSuperSpec) {
/*   585 */             throw new FailedPredicateException(this.input, "smali_file", "!$smali_file::hasSuperSpec");
/*       */           }
/*   587 */           pushFollow(FOLLOW_super_spec_in_smali_file1156);
/*   588 */           super_spec2 = super_spec();
/*   589 */           this.state._fsp -= 1;
/*       */ 
/*   591 */           stream_super_spec.add(super_spec2.getTree());
/*   592 */           ((smali_file_scope)this.smali_file_stack.peek()).hasSuperSpec = true;
/*       */ 
/*   594 */           break;
/*       */         case 3:
/*   598 */           pushFollow(FOLLOW_implements_spec_in_smali_file1164);
/*   599 */           implements_spec3 = implements_spec();
/*   600 */           this.state._fsp -= 1;
/*       */ 
/*   602 */           stream_implements_spec.add(implements_spec3.getTree());
/*       */ 
/*   604 */           break;
/*       */         case 4:
/*   608 */           if (((smali_file_scope)this.smali_file_stack.peek()).hasSourceSpec) {
/*   609 */             throw new FailedPredicateException(this.input, "smali_file", "!$smali_file::hasSourceSpec");
/*       */           }
/*   611 */           pushFollow(FOLLOW_source_spec_in_smali_file1173);
/*   612 */           source_spec4 = source_spec();
/*   613 */           this.state._fsp -= 1;
/*       */ 
/*   615 */           stream_source_spec.add(source_spec4.getTree());
/*   616 */           ((smali_file_scope)this.smali_file_stack.peek()).hasSourceSpec = true;
/*       */ 
/*   618 */           break;
/*       */         case 5:
/*   622 */           pushFollow(FOLLOW_method_in_smali_file1181);
/*   623 */           method5 = method();
/*   624 */           this.state._fsp -= 1;
/*       */ 
/*   626 */           stream_method.add(method5.getTree());
/*       */ 
/*   628 */           break;
/*       */         case 6:
/*   632 */           pushFollow(FOLLOW_field_in_smali_file1187);
/*   633 */           field6 = field();
/*   634 */           this.state._fsp -= 1;
/*       */ 
/*   636 */           stream_field.add(field6.getTree());
/*       */ 
/*   638 */           break;
/*       */         case 7:
/*   642 */           pushFollow(FOLLOW_annotation_in_smali_file1193);
/*   643 */           annotation7 = annotation();
/*   644 */           this.state._fsp -= 1;
/*       */ 
/*   646 */           stream_annotation.add(annotation7.getTree());
/*   647 */           ((smali_file_scope)this.smali_file_stack.peek()).classAnnotations.add(annotation7 != null ? (CommonTree)annotation7.getTree() : null);
/*       */ 
/*   649 */           break;
/*       */         default:
/*   652 */           if (cnt1 >= 1) break label942; EarlyExitException eee = new EarlyExitException(1, this.input);
/*   654 */           throw eee;
/*       */         }
/*   656 */         cnt1++;
/*       */       }
/*       */ 
/*   659 */       label942: EOF8 = (Token)match(this.input, -1, FOLLOW_EOF_in_smali_file1204);
/*   660 */       stream_EOF.add(EOF8);
/*       */ 
/*   663 */       if (!((smali_file_scope)this.smali_file_stack.peek()).hasClassSpec) {
/*   664 */         throw new SemanticException(this.input, "The file must contain a .class directive", new Object[0]);
/*       */       }
/*       */ 
/*   667 */       if (!((smali_file_scope)this.smali_file_stack.peek()).hasSuperSpec) {
/*   668 */         if (!(class_spec1 != null ? ((class_spec_return)class_spec1).className : null).equals("Ljava/lang/Object;")) {
/*   669 */           throw new SemanticException(this.input, "The file must contain a .super directive", new Object[0]);
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/*   680 */       retval.tree = root_0;
/*   681 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*   683 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*   688 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*   689 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(113, "I_CLASS_DEF"), root_1);
/*   690 */       this.adaptor.addChild(root_1, stream_class_spec.nextTree());
/*       */ 
/*   692 */       if (stream_super_spec.hasNext()) {
/*   693 */         this.adaptor.addChild(root_1, stream_super_spec.nextTree());
/*       */       }
/*   695 */       stream_super_spec.reset();
/*       */ 
/*   698 */       while (stream_implements_spec.hasNext()) {
/*   699 */         this.adaptor.addChild(root_1, stream_implements_spec.nextTree());
/*       */       }
/*   701 */       stream_implements_spec.reset();
/*       */ 
/*   704 */       if (stream_source_spec.hasNext()) {
/*   705 */         this.adaptor.addChild(root_1, stream_source_spec.nextTree());
/*       */       }
/*   707 */       stream_source_spec.reset();
/*       */ 
/*   711 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*   712 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(130, "I_METHODS"), root_2);
/*       */ 
/*   714 */       while (stream_method.hasNext()) {
/*   715 */         this.adaptor.addChild(root_2, stream_method.nextTree());
/*       */       }
/*   717 */       stream_method.reset();
/*       */ 
/*   719 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/*   724 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*   725 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(121, "I_FIELDS"), root_2);
/*       */ 
/*   727 */       while (stream_field.hasNext()) {
/*   728 */         this.adaptor.addChild(root_2, stream_field.nextTree());
/*       */       }
/*   730 */       stream_field.reset();
/*       */ 
/*   732 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/*   735 */       this.adaptor.addChild(root_1, buildTree(106, "I_ANNOTATIONS", ((smali_file_scope)this.smali_file_stack.peek()).classAnnotations));
/*   736 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*   742 */       retval.tree = root_0;
/*       */ 
/*   746 */       retval.stop = this.input.LT(-1);
/*       */ 
/*   748 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*   749 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*   753 */       reportError(re);
/*   754 */       recover(this.input, re);
/*   755 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*   759 */       this.smali_file_stack.pop();
/*       */     }
/*   761 */     return retval;
/*       */   }
/*       */ 
/*       */   public final class_spec_return class_spec()
/*       */     throws RecognitionException
/*       */   {
/*   777 */     class_spec_return retval = new class_spec_return();
/*   778 */     retval.start = this.input.LT(1);
/*       */ 
/*   780 */     CommonTree root_0 = null;
/*       */ 
/*   782 */     Token CLASS_DIRECTIVE9 = null;
/*   783 */     Token CLASS_DESCRIPTOR11 = null;
/*   784 */     ParserRuleReturnScope access_list10 = null;
/*       */ 
/*   786 */     CommonTree CLASS_DIRECTIVE9_tree = null;
/*   787 */     CommonTree CLASS_DESCRIPTOR11_tree = null;
/*   788 */     RewriteRuleTokenStream stream_CLASS_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DIRECTIVE");
/*   789 */     RewriteRuleTokenStream stream_CLASS_DESCRIPTOR = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
/*   790 */     RewriteRuleSubtreeStream stream_access_list = new RewriteRuleSubtreeStream(this.adaptor, "rule access_list");
/*       */     try
/*       */     {
/*   796 */       CLASS_DIRECTIVE9 = (Token)match(this.input, 27, FOLLOW_CLASS_DIRECTIVE_in_class_spec1291);
/*   797 */       stream_CLASS_DIRECTIVE.add(CLASS_DIRECTIVE9);
/*       */ 
/*   799 */       pushFollow(FOLLOW_access_list_in_class_spec1293);
/*   800 */       access_list10 = access_list();
/*   801 */       this.state._fsp -= 1;
/*       */ 
/*   803 */       stream_access_list.add(access_list10.getTree());
/*   804 */       CLASS_DESCRIPTOR11 = (Token)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_class_spec1295);
/*   805 */       stream_CLASS_DESCRIPTOR.add(CLASS_DESCRIPTOR11);
/*       */ 
/*   807 */       retval.className = (CLASS_DESCRIPTOR11 != null ? CLASS_DESCRIPTOR11.getText() : null);
/*       */ 
/*   815 */       retval.tree = root_0;
/*   816 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*   818 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*   821 */       this.adaptor.addChild(root_0, stream_CLASS_DESCRIPTOR.nextNode());
/*   822 */       this.adaptor.addChild(root_0, stream_access_list.nextTree());
/*       */ 
/*   826 */       retval.tree = root_0;
/*       */ 
/*   830 */       retval.stop = this.input.LT(-1);
/*       */ 
/*   832 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*   833 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*   837 */       reportError(re);
/*   838 */       recover(this.input, re);
/*   839 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*   844 */     return retval;
/*       */   }
/*       */ 
/*       */   public final super_spec_return super_spec()
/*       */     throws RecognitionException
/*       */   {
/*   859 */     super_spec_return retval = new super_spec_return();
/*   860 */     retval.start = this.input.LT(1);
/*       */ 
/*   862 */     CommonTree root_0 = null;
/*       */ 
/*   864 */     Token SUPER_DIRECTIVE12 = null;
/*   865 */     Token CLASS_DESCRIPTOR13 = null;
/*       */ 
/*   867 */     CommonTree SUPER_DIRECTIVE12_tree = null;
/*   868 */     CommonTree CLASS_DESCRIPTOR13_tree = null;
/*   869 */     RewriteRuleTokenStream stream_SUPER_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token SUPER_DIRECTIVE");
/*   870 */     RewriteRuleTokenStream stream_CLASS_DESCRIPTOR = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
/*       */     try
/*       */     {
/*   876 */       SUPER_DIRECTIVE12 = (Token)match(this.input, 210, FOLLOW_SUPER_DIRECTIVE_in_super_spec1313);
/*   877 */       stream_SUPER_DIRECTIVE.add(SUPER_DIRECTIVE12);
/*       */ 
/*   879 */       CLASS_DESCRIPTOR13 = (Token)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_super_spec1315);
/*   880 */       stream_CLASS_DESCRIPTOR.add(CLASS_DESCRIPTOR13);
/*       */ 
/*   889 */       retval.tree = root_0;
/*   890 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*   892 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*   897 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*   898 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(181, retval.start, "I_SUPER"), root_1);
/*   899 */       this.adaptor.addChild(root_1, stream_CLASS_DESCRIPTOR.nextNode());
/*   900 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*   906 */       retval.tree = root_0;
/*       */ 
/*   910 */       retval.stop = this.input.LT(-1);
/*       */ 
/*   912 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*   913 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*   917 */       reportError(re);
/*   918 */       recover(this.input, re);
/*   919 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*   924 */     return retval;
/*       */   }
/*       */ 
/*       */   public final implements_spec_return implements_spec()
/*       */     throws RecognitionException
/*       */   {
/*   939 */     implements_spec_return retval = new implements_spec_return();
/*   940 */     retval.start = this.input.LT(1);
/*       */ 
/*   942 */     CommonTree root_0 = null;
/*       */ 
/*   944 */     Token IMPLEMENTS_DIRECTIVE14 = null;
/*   945 */     Token CLASS_DESCRIPTOR15 = null;
/*       */ 
/*   947 */     CommonTree IMPLEMENTS_DIRECTIVE14_tree = null;
/*   948 */     CommonTree CLASS_DESCRIPTOR15_tree = null;
/*   949 */     RewriteRuleTokenStream stream_IMPLEMENTS_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token IMPLEMENTS_DIRECTIVE");
/*   950 */     RewriteRuleTokenStream stream_CLASS_DESCRIPTOR = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
/*       */     try
/*       */     {
/*   956 */       IMPLEMENTS_DIRECTIVE14 = (Token)match(this.input, 56, FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1334);
/*   957 */       stream_IMPLEMENTS_DIRECTIVE.add(IMPLEMENTS_DIRECTIVE14);
/*       */ 
/*   959 */       CLASS_DESCRIPTOR15 = (Token)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1336);
/*   960 */       stream_CLASS_DESCRIPTOR.add(CLASS_DESCRIPTOR15);
/*       */ 
/*   969 */       retval.tree = root_0;
/*   970 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*   972 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*   977 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*   978 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(124, retval.start, "I_IMPLEMENTS"), root_1);
/*   979 */       this.adaptor.addChild(root_1, stream_CLASS_DESCRIPTOR.nextNode());
/*   980 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*   986 */       retval.tree = root_0;
/*       */ 
/*   990 */       retval.stop = this.input.LT(-1);
/*       */ 
/*   992 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*   993 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*   997 */       reportError(re);
/*   998 */       recover(this.input, re);
/*   999 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  1004 */     return retval;
/*       */   }
/*       */ 
/*       */   public final source_spec_return source_spec()
/*       */     throws RecognitionException
/*       */   {
/*  1019 */     source_spec_return retval = new source_spec_return();
/*  1020 */     retval.start = this.input.LT(1);
/*       */ 
/*  1022 */     CommonTree root_0 = null;
/*       */ 
/*  1024 */     Token SOURCE_DIRECTIVE16 = null;
/*  1025 */     Token STRING_LITERAL17 = null;
/*       */ 
/*  1027 */     CommonTree SOURCE_DIRECTIVE16_tree = null;
/*  1028 */     CommonTree STRING_LITERAL17_tree = null;
/*  1029 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/*  1030 */     RewriteRuleTokenStream stream_SOURCE_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token SOURCE_DIRECTIVE");
/*       */     try
/*       */     {
/*  1036 */       SOURCE_DIRECTIVE16 = (Token)match(this.input, 206, FOLLOW_SOURCE_DIRECTIVE_in_source_spec1355);
/*  1037 */       stream_SOURCE_DIRECTIVE.add(SOURCE_DIRECTIVE16);
/*       */ 
/*  1039 */       STRING_LITERAL17 = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_source_spec1357);
/*  1040 */       stream_STRING_LITERAL.add(STRING_LITERAL17);
/*       */ 
/*  1049 */       retval.tree = root_0;
/*  1050 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  1052 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  1057 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1058 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(144, retval.start, "I_SOURCE"), root_1);
/*  1059 */       this.adaptor.addChild(root_1, stream_STRING_LITERAL.nextNode());
/*  1060 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1066 */       retval.tree = root_0;
/*       */ 
/*  1070 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  1072 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  1073 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  1077 */       reportError(re);
/*  1078 */       recover(this.input, re);
/*  1079 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  1084 */     return retval;
/*       */   }
/*       */ 
/*       */   public final access_list_return access_list()
/*       */     throws RecognitionException
/*       */   {
/*  1099 */     access_list_return retval = new access_list_return();
/*  1100 */     retval.start = this.input.LT(1);
/*       */ 
/*  1102 */     CommonTree root_0 = null;
/*       */ 
/*  1104 */     Token ACCESS_SPEC18 = null;
/*       */ 
/*  1106 */     CommonTree ACCESS_SPEC18_tree = null;
/*  1107 */     RewriteRuleTokenStream stream_ACCESS_SPEC = new RewriteRuleTokenStream(this.adaptor, "token ACCESS_SPEC");
/*       */     try
/*       */     {
/*       */       while (true)
/*       */       {
/*  1116 */         int alt2 = 2;
/*  1117 */         int LA2_0 = this.input.LA(1);
/*  1118 */         if (LA2_0 == 4) {
/*  1119 */           int LA2_2 = this.input.LA(2);
/*  1120 */           if ((LA2_2 == 4) || (LA2_2 == 6) || (LA2_2 == 21) || (LA2_2 == 26) || (LA2_2 == 35) || (LA2_2 == 52) || ((LA2_2 >= 58) && (LA2_2 <= 60)) || (LA2_2 == 62) || (LA2_2 == 64) || ((LA2_2 >= 67) && (LA2_2 <= 70)) || (LA2_2 == 74) || ((LA2_2 >= 76) && (LA2_2 <= 79)) || ((LA2_2 >= 81) && (LA2_2 <= 82)) || (LA2_2 == 84) || ((LA2_2 >= 88) && (LA2_2 <= 89)) || ((LA2_2 >= 91) && (LA2_2 <= 95)) || (LA2_2 == 101) || (LA2_2 == 188) || ((LA2_2 >= 190) && (LA2_2 <= 191)) || ((LA2_2 >= 197) && (LA2_2 <= 199)) || (LA2_2 == 201) || (LA2_2 == 205) || ((LA2_2 >= 211) && (LA2_2 <= 212))) {
/*  1121 */             alt2 = 1;
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*  1126 */         switch (alt2)
/*       */         {
/*       */         case 1:
/*  1130 */           ACCESS_SPEC18 = (Token)match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_list1376);
/*  1131 */           stream_ACCESS_SPEC.add(ACCESS_SPEC18);
/*       */ 
/*  1134 */           break;
/*       */         default:
/*  1137 */           break label363;
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/*  1148 */       label363: retval.tree = root_0;
/*  1149 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  1151 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  1156 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1157 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(104, retval.start, "I_ACCESS_LIST"), root_1);
/*       */ 
/*  1159 */       while (stream_ACCESS_SPEC.hasNext()) {
/*  1160 */         this.adaptor.addChild(root_1, stream_ACCESS_SPEC.nextNode());
/*       */       }
/*  1162 */       stream_ACCESS_SPEC.reset();
/*       */ 
/*  1164 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1170 */       retval.tree = root_0;
/*       */ 
/*  1174 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  1176 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  1177 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  1181 */       reportError(re);
/*  1182 */       recover(this.input, re);
/*  1183 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  1188 */     return retval;
/*       */   }
/*       */ 
/*       */   public final field_return field()
/*       */     throws RecognitionException
/*       */   {
/*  1203 */     field_return retval = new field_return();
/*  1204 */     retval.start = this.input.LT(1);
/*       */ 
/*  1206 */     CommonTree root_0 = null;
/*       */ 
/*  1208 */     Token FIELD_DIRECTIVE19 = null;
/*  1209 */     Token COLON22 = null;
/*  1210 */     Token EQUAL24 = null;
/*  1211 */     Token END_FIELD_DIRECTIVE27 = null;
/*  1212 */     ParserRuleReturnScope access_list20 = null;
/*  1213 */     ParserRuleReturnScope member_name21 = null;
/*  1214 */     ParserRuleReturnScope nonvoid_type_descriptor23 = null;
/*  1215 */     ParserRuleReturnScope literal25 = null;
/*  1216 */     ParserRuleReturnScope annotation26 = null;
/*       */ 
/*  1218 */     CommonTree FIELD_DIRECTIVE19_tree = null;
/*  1219 */     CommonTree COLON22_tree = null;
/*  1220 */     CommonTree EQUAL24_tree = null;
/*  1221 */     CommonTree END_FIELD_DIRECTIVE27_tree = null;
/*  1222 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/*  1223 */     RewriteRuleTokenStream stream_FIELD_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token FIELD_DIRECTIVE");
/*  1224 */     RewriteRuleTokenStream stream_EQUAL = new RewriteRuleTokenStream(this.adaptor, "token EQUAL");
/*  1225 */     RewriteRuleTokenStream stream_END_FIELD_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_FIELD_DIRECTIVE");
/*  1226 */     RewriteRuleSubtreeStream stream_annotation = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
/*  1227 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/*  1228 */     RewriteRuleSubtreeStream stream_access_list = new RewriteRuleSubtreeStream(this.adaptor, "rule access_list");
/*  1229 */     RewriteRuleSubtreeStream stream_member_name = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
/*  1230 */     RewriteRuleSubtreeStream stream_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule literal");
/*       */ 
/*  1232 */     List annotations = new ArrayList();
/*       */     try
/*       */     {
/*  1237 */       FIELD_DIRECTIVE19 = (Token)match(this.input, 49, FOLLOW_FIELD_DIRECTIVE_in_field1407);
/*  1238 */       stream_FIELD_DIRECTIVE.add(FIELD_DIRECTIVE19);
/*       */ 
/*  1240 */       pushFollow(FOLLOW_access_list_in_field1409);
/*  1241 */       access_list20 = access_list();
/*  1242 */       this.state._fsp -= 1;
/*       */ 
/*  1244 */       stream_access_list.add(access_list20.getTree());
/*  1245 */       pushFollow(FOLLOW_member_name_in_field1411);
/*  1246 */       member_name21 = member_name();
/*  1247 */       this.state._fsp -= 1;
/*       */ 
/*  1249 */       stream_member_name.add(member_name21.getTree());
/*  1250 */       COLON22 = (Token)match(this.input, 30, FOLLOW_COLON_in_field1413);
/*  1251 */       stream_COLON.add(COLON22);
/*       */ 
/*  1253 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_field1415);
/*  1254 */       nonvoid_type_descriptor23 = nonvoid_type_descriptor();
/*  1255 */       this.state._fsp -= 1;
/*       */ 
/*  1257 */       stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor23.getTree());
/*       */ 
/*  1259 */       int alt3 = 2;
/*  1260 */       int LA3_0 = this.input.LA(1);
/*  1261 */       if (LA3_0 == 47) {
/*  1262 */         alt3 = 1;
/*       */       }
/*  1264 */       switch (alt3)
/*       */       {
/*       */       case 1:
/*  1268 */         EQUAL24 = (Token)match(this.input, 47, FOLLOW_EQUAL_in_field1418);
/*  1269 */         stream_EQUAL.add(EQUAL24);
/*       */ 
/*  1271 */         pushFollow(FOLLOW_literal_in_field1420);
/*  1272 */         literal25 = literal();
/*  1273 */         this.state._fsp -= 1;
/*       */ 
/*  1275 */         stream_literal.add(literal25.getTree());
/*       */       }
/*       */ 
/*       */       while (true)
/*       */       {
/*  1287 */         int alt4 = 2;
/*  1288 */         int LA4_0 = this.input.LA(1);
/*  1289 */         if (LA4_0 == 5) {
/*  1290 */           int LA4_9 = this.input.LA(2);
/*  1291 */           if (this.input.LA(1) == 5) {
/*  1292 */             alt4 = 1;
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*  1297 */         switch (alt4)
/*       */         {
/*       */         case 1:
/*  1301 */           if (this.input.LA(1) != 5) {
/*       */             throw new FailedPredicateException(this.input, "field", "input.LA(1) == ANNOTATION_DIRECTIVE");
/*       */           }
/*  1304 */           pushFollow(FOLLOW_annotation_in_field1433);
/*  1305 */           annotation26 = annotation();
/*  1306 */           this.state._fsp -= 1;
/*       */ 
/*  1308 */           stream_annotation.add(annotation26.getTree());
/*  1309 */           annotations.add(annotation26 != null ? (CommonTree)annotation26.getTree() : null);
/*       */ 
/*  1311 */           break;
/*       */         default:
/*  1314 */           break label650;
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/*  1319 */       label650: int alt5 = 2;
/*  1320 */       int LA5_0 = this.input.LA(1);
/*  1321 */       if (LA5_0 == 38) {
/*  1322 */         alt5 = 1;
/*       */       }
/*  1324 */       else if ((LA5_0 == -1) || (LA5_0 == 5) || (LA5_0 == 27) || (LA5_0 == 49) || (LA5_0 == 56) || (LA5_0 == 189) || (LA5_0 == 206) || (LA5_0 == 210)) {
/*  1325 */         alt5 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  1329 */         NoViableAltException nvae = new NoViableAltException("", 5, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  1334 */       switch (alt5)
/*       */       {
/*       */       case 1:
/*  1338 */         END_FIELD_DIRECTIVE27 = (Token)match(this.input, 38, FOLLOW_END_FIELD_DIRECTIVE_in_field1447);
/*  1339 */         stream_END_FIELD_DIRECTIVE.add(END_FIELD_DIRECTIVE27);
/*       */ 
/*  1348 */         retval.tree = root_0;
/*  1349 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  1351 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  1356 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1357 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(120, retval.start, "I_FIELD"), root_1);
/*  1358 */         this.adaptor.addChild(root_1, stream_member_name.nextTree());
/*  1359 */         this.adaptor.addChild(root_1, stream_access_list.nextTree());
/*       */ 
/*  1362 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  1363 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(123, "I_FIELD_TYPE"), root_2);
/*  1364 */         this.adaptor.addChild(root_2, stream_nonvoid_type_descriptor.nextTree());
/*  1365 */         this.adaptor.addChild(root_1, root_2);
/*       */ 
/*  1369 */         if (stream_literal.hasNext())
/*       */         {
/*  1372 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  1373 */           root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(122, "I_FIELD_INITIAL_VALUE"), root_2);
/*  1374 */           this.adaptor.addChild(root_2, stream_literal.nextTree());
/*  1375 */           this.adaptor.addChild(root_1, root_2);
/*       */         }
/*       */ 
/*  1379 */         stream_literal.reset();
/*       */ 
/*  1383 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  1384 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(106, "I_ANNOTATIONS"), root_2);
/*       */ 
/*  1386 */         while (stream_annotation.hasNext()) {
/*  1387 */           this.adaptor.addChild(root_2, stream_annotation.nextTree());
/*       */         }
/*  1389 */         stream_annotation.reset();
/*       */ 
/*  1391 */         this.adaptor.addChild(root_1, root_2);
/*       */ 
/*  1394 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1400 */         retval.tree = root_0;
/*       */ 
/*  1403 */         break;
/*       */       case 2:
/*  1407 */         ((smali_file_scope)this.smali_file_stack.peek()).classAnnotations.addAll(annotations);
/*       */ 
/*  1415 */         retval.tree = root_0;
/*  1416 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  1418 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  1423 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1424 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(120, retval.start, "I_FIELD"), root_1);
/*  1425 */         this.adaptor.addChild(root_1, stream_member_name.nextTree());
/*  1426 */         this.adaptor.addChild(root_1, stream_access_list.nextTree());
/*       */ 
/*  1429 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  1430 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(123, "I_FIELD_TYPE"), root_2);
/*  1431 */         this.adaptor.addChild(root_2, stream_nonvoid_type_descriptor.nextTree());
/*  1432 */         this.adaptor.addChild(root_1, root_2);
/*       */ 
/*  1436 */         if (stream_literal.hasNext())
/*       */         {
/*  1439 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  1440 */           root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(122, "I_FIELD_INITIAL_VALUE"), root_2);
/*  1441 */           this.adaptor.addChild(root_2, stream_literal.nextTree());
/*  1442 */           this.adaptor.addChild(root_1, root_2);
/*       */         }
/*       */ 
/*  1446 */         stream_literal.reset();
/*       */ 
/*  1450 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  1451 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(106, "I_ANNOTATIONS"), root_2);
/*  1452 */         this.adaptor.addChild(root_1, root_2);
/*       */ 
/*  1455 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1461 */         retval.tree = root_0;
/*       */       }
/*       */ 
/*  1472 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  1474 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  1475 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  1479 */       reportError(re);
/*  1480 */       recover(this.input, re);
/*  1481 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  1486 */     return retval;
/*       */   }
/*       */ 
/*       */   public final method_return method()
/*       */     throws RecognitionException
/*       */   {
/*  1501 */     method_return retval = new method_return();
/*  1502 */     retval.start = this.input.LT(1);
/*       */ 
/*  1504 */     CommonTree root_0 = null;
/*       */ 
/*  1506 */     Token METHOD_DIRECTIVE28 = null;
/*  1507 */     Token END_METHOD_DIRECTIVE33 = null;
/*  1508 */     ParserRuleReturnScope access_list29 = null;
/*  1509 */     ParserRuleReturnScope member_name30 = null;
/*  1510 */     ParserRuleReturnScope method_prototype31 = null;
/*  1511 */     ParserRuleReturnScope statements_and_directives32 = null;
/*       */ 
/*  1513 */     CommonTree METHOD_DIRECTIVE28_tree = null;
/*  1514 */     CommonTree END_METHOD_DIRECTIVE33_tree = null;
/*  1515 */     RewriteRuleTokenStream stream_END_METHOD_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_METHOD_DIRECTIVE");
/*  1516 */     RewriteRuleTokenStream stream_METHOD_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token METHOD_DIRECTIVE");
/*  1517 */     RewriteRuleSubtreeStream stream_access_list = new RewriteRuleSubtreeStream(this.adaptor, "rule access_list");
/*  1518 */     RewriteRuleSubtreeStream stream_method_prototype = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
/*  1519 */     RewriteRuleSubtreeStream stream_statements_and_directives = new RewriteRuleSubtreeStream(this.adaptor, "rule statements_and_directives");
/*  1520 */     RewriteRuleSubtreeStream stream_member_name = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
/*       */     try
/*       */     {
/*  1526 */       METHOD_DIRECTIVE28 = (Token)match(this.input, 189, FOLLOW_METHOD_DIRECTIVE_in_method1558);
/*  1527 */       stream_METHOD_DIRECTIVE.add(METHOD_DIRECTIVE28);
/*       */ 
/*  1529 */       pushFollow(FOLLOW_access_list_in_method1560);
/*  1530 */       access_list29 = access_list();
/*  1531 */       this.state._fsp -= 1;
/*       */ 
/*  1533 */       stream_access_list.add(access_list29.getTree());
/*  1534 */       pushFollow(FOLLOW_member_name_in_method1562);
/*  1535 */       member_name30 = member_name();
/*  1536 */       this.state._fsp -= 1;
/*       */ 
/*  1538 */       stream_member_name.add(member_name30.getTree());
/*  1539 */       pushFollow(FOLLOW_method_prototype_in_method1564);
/*  1540 */       method_prototype31 = method_prototype();
/*  1541 */       this.state._fsp -= 1;
/*       */ 
/*  1543 */       stream_method_prototype.add(method_prototype31.getTree());
/*  1544 */       pushFollow(FOLLOW_statements_and_directives_in_method1566);
/*  1545 */       statements_and_directives32 = statements_and_directives();
/*  1546 */       this.state._fsp -= 1;
/*       */ 
/*  1548 */       stream_statements_and_directives.add(statements_and_directives32.getTree());
/*  1549 */       END_METHOD_DIRECTIVE33 = (Token)match(this.input, 40, FOLLOW_END_METHOD_DIRECTIVE_in_method1572);
/*  1550 */       stream_END_METHOD_DIRECTIVE.add(END_METHOD_DIRECTIVE33);
/*       */ 
/*  1559 */       retval.tree = root_0;
/*  1560 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  1562 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  1567 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1568 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(129, retval.start, "I_METHOD"), root_1);
/*  1569 */       this.adaptor.addChild(root_1, stream_member_name.nextTree());
/*  1570 */       this.adaptor.addChild(root_1, stream_method_prototype.nextTree());
/*  1571 */       this.adaptor.addChild(root_1, stream_access_list.nextTree());
/*  1572 */       this.adaptor.addChild(root_1, stream_statements_and_directives.nextTree());
/*  1573 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1579 */       retval.tree = root_0;
/*       */ 
/*  1583 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  1585 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  1586 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  1590 */       reportError(re);
/*  1591 */       recover(this.input, re);
/*  1592 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  1597 */     return retval;
/*       */   }
/*       */ 
/*       */   public final statements_and_directives_return statements_and_directives()
/*       */     throws RecognitionException
/*       */   {
/*  1618 */     this.statements_and_directives_stack.push(new statements_and_directives_scope());
/*  1619 */     statements_and_directives_return retval = new statements_and_directives_return();
/*  1620 */     retval.start = this.input.LT(1);
/*       */ 
/*  1622 */     CommonTree root_0 = null;
/*       */ 
/*  1624 */     ParserRuleReturnScope ordered_method_item34 = null;
/*  1625 */     ParserRuleReturnScope registers_directive35 = null;
/*  1626 */     ParserRuleReturnScope catch_directive36 = null;
/*  1627 */     ParserRuleReturnScope catchall_directive37 = null;
/*  1628 */     ParserRuleReturnScope parameter_directive38 = null;
/*  1629 */     ParserRuleReturnScope annotation39 = null;
/*       */ 
/*  1631 */     RewriteRuleSubtreeStream stream_catchall_directive = new RewriteRuleSubtreeStream(this.adaptor, "rule catchall_directive");
/*  1632 */     RewriteRuleSubtreeStream stream_annotation = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
/*  1633 */     RewriteRuleSubtreeStream stream_ordered_method_item = new RewriteRuleSubtreeStream(this.adaptor, "rule ordered_method_item");
/*  1634 */     RewriteRuleSubtreeStream stream_catch_directive = new RewriteRuleSubtreeStream(this.adaptor, "rule catch_directive");
/*  1635 */     RewriteRuleSubtreeStream stream_registers_directive = new RewriteRuleSubtreeStream(this.adaptor, "rule registers_directive");
/*  1636 */     RewriteRuleSubtreeStream stream_parameter_directive = new RewriteRuleSubtreeStream(this.adaptor, "rule parameter_directive");
/*       */     try
/*       */     {
/*  1643 */       ((statements_and_directives_scope)this.statements_and_directives_stack.peek()).hasRegistersDirective = false;
/*  1644 */       ((statements_and_directives_scope)this.statements_and_directives_stack.peek()).methodAnnotations = new ArrayList();
/*       */       while (true)
/*       */       {
/*  1649 */         int alt6 = 7;
/*  1650 */         switch (this.input.LA(1))
/*       */         {
/*       */         case 7:
/*       */         case 30:
/*       */         case 39:
/*       */         case 46:
/*       */         case 58:
/*       */         case 59:
/*       */         case 60:
/*       */         case 61:
/*       */         case 62:
/*       */         case 63:
/*       */         case 64:
/*       */         case 65:
/*       */         case 66:
/*       */         case 67:
/*       */         case 68:
/*       */         case 69:
/*       */         case 70:
/*       */         case 71:
/*       */         case 72:
/*       */         case 73:
/*       */         case 74:
/*       */         case 75:
/*       */         case 76:
/*       */         case 77:
/*       */         case 78:
/*       */         case 79:
/*       */         case 80:
/*       */         case 81:
/*       */         case 82:
/*       */         case 83:
/*       */         case 84:
/*       */         case 85:
/*       */         case 86:
/*       */         case 87:
/*       */         case 88:
/*       */         case 89:
/*       */         case 90:
/*       */         case 91:
/*       */         case 92:
/*       */         case 93:
/*       */         case 94:
/*       */         case 95:
/*       */         case 96:
/*       */         case 97:
/*       */         case 98:
/*       */         case 99:
/*       */         case 100:
/*       */         case 101:
/*       */         case 184:
/*       */         case 186:
/*       */         case 194:
/*       */         case 200:
/*       */         case 203:
/*       */         case 206:
/*       */         case 207:
/*  1707 */           alt6 = 1;
/*       */ 
/*  1709 */           break;
/*       */         case 185:
/*       */         case 202:
/*  1713 */           alt6 = 2;
/*       */ 
/*  1715 */           break;
/*       */         case 24:
/*  1718 */           alt6 = 3;
/*       */ 
/*  1720 */           break;
/*       */         case 23:
/*  1723 */           alt6 = 4;
/*       */ 
/*  1725 */           break;
/*       */         case 195:
/*  1728 */           alt6 = 5;
/*       */ 
/*  1730 */           break;
/*       */         case 5:
/*  1733 */           alt6 = 6;
/*       */         case 6:
/*       */         case 8:
/*       */         case 9:
/*       */         case 10:
/*       */         case 11:
/*       */         case 12:
/*       */         case 13:
/*       */         case 14:
/*       */         case 15:
/*       */         case 16:
/*       */         case 17:
/*       */         case 18:
/*       */         case 19:
/*       */         case 20:
/*       */         case 21:
/*       */         case 22:
/*       */         case 25:
/*       */         case 26:
/*       */         case 27:
/*       */         case 28:
/*       */         case 29:
/*       */         case 31:
/*       */         case 32:
/*       */         case 33:
/*       */         case 34:
/*       */         case 35:
/*       */         case 36:
/*       */         case 37:
/*       */         case 38:
/*       */         case 40:
/*       */         case 41:
/*       */         case 42:
/*       */         case 43:
/*       */         case 44:
/*       */         case 45:
/*       */         case 47:
/*       */         case 48:
/*       */         case 49:
/*       */         case 50:
/*       */         case 51:
/*       */         case 52:
/*       */         case 53:
/*       */         case 54:
/*       */         case 55:
/*       */         case 56:
/*       */         case 57:
/*       */         case 102:
/*       */         case 103:
/*       */         case 104:
/*       */         case 105:
/*       */         case 106:
/*       */         case 107:
/*       */         case 108:
/*       */         case 109:
/*       */         case 110:
/*       */         case 111:
/*       */         case 112:
/*       */         case 113:
/*       */         case 114:
/*       */         case 115:
/*       */         case 116:
/*       */         case 117:
/*       */         case 118:
/*       */         case 119:
/*       */         case 120:
/*       */         case 121:
/*       */         case 122:
/*       */         case 123:
/*       */         case 124:
/*       */         case 125:
/*       */         case 126:
/*       */         case 127:
/*       */         case 128:
/*       */         case 129:
/*       */         case 130:
/*       */         case 131:
/*       */         case 132:
/*       */         case 133:
/*       */         case 134:
/*       */         case 135:
/*       */         case 136:
/*       */         case 137:
/*       */         case 138:
/*       */         case 139:
/*       */         case 140:
/*       */         case 141:
/*       */         case 142:
/*       */         case 143:
/*       */         case 144:
/*       */         case 145:
/*       */         case 146:
/*       */         case 147:
/*       */         case 148:
/*       */         case 149:
/*       */         case 150:
/*       */         case 151:
/*       */         case 152:
/*       */         case 153:
/*       */         case 154:
/*       */         case 155:
/*       */         case 156:
/*       */         case 157:
/*       */         case 158:
/*       */         case 159:
/*       */         case 160:
/*       */         case 161:
/*       */         case 162:
/*       */         case 163:
/*       */         case 164:
/*       */         case 165:
/*       */         case 166:
/*       */         case 167:
/*       */         case 168:
/*       */         case 169:
/*       */         case 170:
/*       */         case 171:
/*       */         case 172:
/*       */         case 173:
/*       */         case 174:
/*       */         case 175:
/*       */         case 176:
/*       */         case 177:
/*       */         case 178:
/*       */         case 179:
/*       */         case 180:
/*       */         case 181:
/*       */         case 182:
/*       */         case 183:
/*       */         case 187:
/*       */         case 188:
/*       */         case 189:
/*       */         case 190:
/*       */         case 191:
/*       */         case 192:
/*       */         case 193:
/*       */         case 196:
/*       */         case 197:
/*       */         case 198:
/*       */         case 199:
/*       */         case 201:
/*       */         case 204:
/*  1737 */         case 205: } switch (alt6)
/*       */         {
/*       */         case 1:
/*  1741 */           pushFollow(FOLLOW_ordered_method_item_in_statements_and_directives1617);
/*  1742 */           ordered_method_item34 = ordered_method_item();
/*  1743 */           this.state._fsp -= 1;
/*       */ 
/*  1745 */           stream_ordered_method_item.add(ordered_method_item34.getTree());
/*       */ 
/*  1747 */           break;
/*       */         case 2:
/*  1751 */           pushFollow(FOLLOW_registers_directive_in_statements_and_directives1625);
/*  1752 */           registers_directive35 = registers_directive();
/*  1753 */           this.state._fsp -= 1;
/*       */ 
/*  1755 */           stream_registers_directive.add(registers_directive35.getTree());
/*       */ 
/*  1757 */           break;
/*       */         case 3:
/*  1761 */           pushFollow(FOLLOW_catch_directive_in_statements_and_directives1633);
/*  1762 */           catch_directive36 = catch_directive();
/*  1763 */           this.state._fsp -= 1;
/*       */ 
/*  1765 */           stream_catch_directive.add(catch_directive36.getTree());
/*       */ 
/*  1767 */           break;
/*       */         case 4:
/*  1771 */           pushFollow(FOLLOW_catchall_directive_in_statements_and_directives1641);
/*  1772 */           catchall_directive37 = catchall_directive();
/*  1773 */           this.state._fsp -= 1;
/*       */ 
/*  1775 */           stream_catchall_directive.add(catchall_directive37.getTree());
/*       */ 
/*  1777 */           break;
/*       */         case 5:
/*  1781 */           pushFollow(FOLLOW_parameter_directive_in_statements_and_directives1649);
/*  1782 */           parameter_directive38 = parameter_directive();
/*  1783 */           this.state._fsp -= 1;
/*       */ 
/*  1785 */           stream_parameter_directive.add(parameter_directive38.getTree());
/*       */ 
/*  1787 */           break;
/*       */         case 6:
/*  1791 */           pushFollow(FOLLOW_annotation_in_statements_and_directives1657);
/*  1792 */           annotation39 = annotation();
/*  1793 */           this.state._fsp -= 1;
/*       */ 
/*  1795 */           stream_annotation.add(annotation39.getTree());
/*  1796 */           ((statements_and_directives_scope)this.statements_and_directives_stack.peek()).methodAnnotations.add(annotation39 != null ? (CommonTree)annotation39.getTree() : null);
/*       */ 
/*  1798 */           break;
/*       */         default:
/*  1801 */           break label1378;
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/*  1812 */       label1378: retval.tree = root_0;
/*  1813 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  1815 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  1819 */       if (stream_registers_directive.hasNext()) {
/*  1820 */         this.adaptor.addChild(root_0, stream_registers_directive.nextTree());
/*       */       }
/*  1822 */       stream_registers_directive.reset();
/*       */ 
/*  1826 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1827 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(133, "I_ORDERED_METHOD_ITEMS"), root_1);
/*       */ 
/*  1829 */       while (stream_ordered_method_item.hasNext()) {
/*  1830 */         this.adaptor.addChild(root_1, stream_ordered_method_item.nextTree());
/*       */       }
/*  1832 */       stream_ordered_method_item.reset();
/*       */ 
/*  1834 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1839 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1840 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(112, "I_CATCHES"), root_1);
/*       */ 
/*  1842 */       while (stream_catch_directive.hasNext()) {
/*  1843 */         this.adaptor.addChild(root_1, stream_catch_directive.nextTree());
/*       */       }
/*  1845 */       stream_catch_directive.reset();
/*       */ 
/*  1848 */       while (stream_catchall_directive.hasNext()) {
/*  1849 */         this.adaptor.addChild(root_1, stream_catchall_directive.nextTree());
/*       */       }
/*  1851 */       stream_catchall_directive.reset();
/*       */ 
/*  1853 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1858 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  1859 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(137, "I_PARAMETERS"), root_1);
/*       */ 
/*  1861 */       while (stream_parameter_directive.hasNext()) {
/*  1862 */         this.adaptor.addChild(root_1, stream_parameter_directive.nextTree());
/*       */       }
/*  1864 */       stream_parameter_directive.reset();
/*       */ 
/*  1866 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  1869 */       this.adaptor.addChild(root_0, buildTree(106, "I_ANNOTATIONS", ((statements_and_directives_scope)this.statements_and_directives_stack.peek()).methodAnnotations));
/*       */ 
/*  1873 */       retval.tree = root_0;
/*       */ 
/*  1877 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  1879 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  1880 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  1884 */       reportError(re);
/*  1885 */       recover(this.input, re);
/*  1886 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*  1890 */       this.statements_and_directives_stack.pop();
/*       */     }
/*  1892 */     return retval;
/*       */   }
/*       */ 
/*       */   public final ordered_method_item_return ordered_method_item()
/*       */     throws RecognitionException
/*       */   {
/*  1907 */     ordered_method_item_return retval = new ordered_method_item_return();
/*  1908 */     retval.start = this.input.LT(1);
/*       */ 
/*  1910 */     CommonTree root_0 = null;
/*       */ 
/*  1912 */     ParserRuleReturnScope label40 = null;
/*  1913 */     ParserRuleReturnScope instruction41 = null;
/*  1914 */     ParserRuleReturnScope debug_directive42 = null;
/*       */     try
/*       */     {
/*  1919 */       int alt7 = 3;
/*  1920 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 30:
/*  1923 */         alt7 = 1;
/*       */ 
/*  1925 */         break;
/*       */       case 7:
/*       */       case 58:
/*       */       case 59:
/*       */       case 60:
/*       */       case 61:
/*       */       case 62:
/*       */       case 63:
/*       */       case 64:
/*       */       case 65:
/*       */       case 66:
/*       */       case 67:
/*       */       case 68:
/*       */       case 69:
/*       */       case 70:
/*       */       case 71:
/*       */       case 72:
/*       */       case 73:
/*       */       case 74:
/*       */       case 75:
/*       */       case 76:
/*       */       case 77:
/*       */       case 78:
/*       */       case 79:
/*       */       case 80:
/*       */       case 81:
/*       */       case 82:
/*       */       case 83:
/*       */       case 84:
/*       */       case 85:
/*       */       case 86:
/*       */       case 87:
/*       */       case 88:
/*       */       case 89:
/*       */       case 90:
/*       */       case 91:
/*       */       case 92:
/*       */       case 93:
/*       */       case 94:
/*       */       case 95:
/*       */       case 96:
/*       */       case 97:
/*       */       case 98:
/*       */       case 99:
/*       */       case 100:
/*       */       case 101:
/*       */       case 194:
/*       */       case 207:
/*  1974 */         alt7 = 2;
/*       */ 
/*  1976 */         break;
/*       */       case 39:
/*       */       case 46:
/*       */       case 184:
/*       */       case 186:
/*       */       case 200:
/*       */       case 203:
/*       */       case 206:
/*  1985 */         alt7 = 3;
/*       */ 
/*  1987 */         break;
/*       */       case 8:
/*       */       case 9:
/*       */       case 10:
/*       */       case 11:
/*       */       case 12:
/*       */       case 13:
/*       */       case 14:
/*       */       case 15:
/*       */       case 16:
/*       */       case 17:
/*       */       case 18:
/*       */       case 19:
/*       */       case 20:
/*       */       case 21:
/*       */       case 22:
/*       */       case 23:
/*       */       case 24:
/*       */       case 25:
/*       */       case 26:
/*       */       case 27:
/*       */       case 28:
/*       */       case 29:
/*       */       case 31:
/*       */       case 32:
/*       */       case 33:
/*       */       case 34:
/*       */       case 35:
/*       */       case 36:
/*       */       case 37:
/*       */       case 38:
/*       */       case 40:
/*       */       case 41:
/*       */       case 42:
/*       */       case 43:
/*       */       case 44:
/*       */       case 45:
/*       */       case 47:
/*       */       case 48:
/*       */       case 49:
/*       */       case 50:
/*       */       case 51:
/*       */       case 52:
/*       */       case 53:
/*       */       case 54:
/*       */       case 55:
/*       */       case 56:
/*       */       case 57:
/*       */       case 102:
/*       */       case 103:
/*       */       case 104:
/*       */       case 105:
/*       */       case 106:
/*       */       case 107:
/*       */       case 108:
/*       */       case 109:
/*       */       case 110:
/*       */       case 111:
/*       */       case 112:
/*       */       case 113:
/*       */       case 114:
/*       */       case 115:
/*       */       case 116:
/*       */       case 117:
/*       */       case 118:
/*       */       case 119:
/*       */       case 120:
/*       */       case 121:
/*       */       case 122:
/*       */       case 123:
/*       */       case 124:
/*       */       case 125:
/*       */       case 126:
/*       */       case 127:
/*       */       case 128:
/*       */       case 129:
/*       */       case 130:
/*       */       case 131:
/*       */       case 132:
/*       */       case 133:
/*       */       case 134:
/*       */       case 135:
/*       */       case 136:
/*       */       case 137:
/*       */       case 138:
/*       */       case 139:
/*       */       case 140:
/*       */       case 141:
/*       */       case 142:
/*       */       case 143:
/*       */       case 144:
/*       */       case 145:
/*       */       case 146:
/*       */       case 147:
/*       */       case 148:
/*       */       case 149:
/*       */       case 150:
/*       */       case 151:
/*       */       case 152:
/*       */       case 153:
/*       */       case 154:
/*       */       case 155:
/*       */       case 156:
/*       */       case 157:
/*       */       case 158:
/*       */       case 159:
/*       */       case 160:
/*       */       case 161:
/*       */       case 162:
/*       */       case 163:
/*       */       case 164:
/*       */       case 165:
/*       */       case 166:
/*       */       case 167:
/*       */       case 168:
/*       */       case 169:
/*       */       case 170:
/*       */       case 171:
/*       */       case 172:
/*       */       case 173:
/*       */       case 174:
/*       */       case 175:
/*       */       case 176:
/*       */       case 177:
/*       */       case 178:
/*       */       case 179:
/*       */       case 180:
/*       */       case 181:
/*       */       case 182:
/*       */       case 183:
/*       */       case 185:
/*       */       case 187:
/*       */       case 188:
/*       */       case 189:
/*       */       case 190:
/*       */       case 191:
/*       */       case 192:
/*       */       case 193:
/*       */       case 195:
/*       */       case 196:
/*       */       case 197:
/*       */       case 198:
/*       */       case 199:
/*       */       case 201:
/*       */       case 202:
/*       */       case 204:
/*       */       case 205:
/*       */       default:
/*  1989 */         NoViableAltException nvae = new NoViableAltException("", 7, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  1993 */       switch (alt7)
/*       */       {
/*       */       case 1:
/*  1997 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2000 */         pushFollow(FOLLOW_label_in_ordered_method_item1742);
/*  2001 */         label40 = label();
/*  2002 */         this.state._fsp -= 1;
/*       */ 
/*  2004 */         this.adaptor.addChild(root_0, label40.getTree());
/*       */ 
/*  2007 */         break;
/*       */       case 2:
/*  2011 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2014 */         pushFollow(FOLLOW_instruction_in_ordered_method_item1748);
/*  2015 */         instruction41 = instruction();
/*  2016 */         this.state._fsp -= 1;
/*       */ 
/*  2018 */         this.adaptor.addChild(root_0, instruction41.getTree());
/*       */ 
/*  2021 */         break;
/*       */       case 3:
/*  2025 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2028 */         pushFollow(FOLLOW_debug_directive_in_ordered_method_item1754);
/*  2029 */         debug_directive42 = debug_directive();
/*  2030 */         this.state._fsp -= 1;
/*       */ 
/*  2032 */         this.adaptor.addChild(root_0, debug_directive42.getTree());
/*       */       }
/*       */ 
/*  2038 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  2040 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  2041 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  2045 */       reportError(re);
/*  2046 */       recover(this.input, re);
/*  2047 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  2052 */     return retval;
/*       */   }
/*       */ 
/*       */   public final registers_directive_return registers_directive()
/*       */     throws RecognitionException
/*       */   {
/*  2067 */     registers_directive_return retval = new registers_directive_return();
/*  2068 */     retval.start = this.input.LT(1);
/*       */ 
/*  2070 */     CommonTree root_0 = null;
/*       */ 
/*  2072 */     Token directive = null;
/*  2073 */     ParserRuleReturnScope regCount = null;
/*  2074 */     ParserRuleReturnScope regCount2 = null;
/*       */ 
/*  2076 */     CommonTree directive_tree = null;
/*  2077 */     RewriteRuleTokenStream stream_REGISTERS_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token REGISTERS_DIRECTIVE");
/*  2078 */     RewriteRuleTokenStream stream_LOCALS_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token LOCALS_DIRECTIVE");
/*  2079 */     RewriteRuleSubtreeStream stream_integral_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
/*       */     try
/*       */     {
/*  2086 */       int alt8 = 2;
/*  2087 */       int LA8_0 = this.input.LA(1);
/*  2088 */       if (LA8_0 == 202) {
/*  2089 */         alt8 = 1;
/*       */       }
/*  2091 */       else if (LA8_0 == 185) {
/*  2092 */         alt8 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  2096 */         NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  2101 */       switch (alt8)
/*       */       {
/*       */       case 1:
/*  2105 */         directive = (Token)match(this.input, 202, FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1774);
/*  2106 */         stream_REGISTERS_DIRECTIVE.add(directive);
/*       */ 
/*  2108 */         pushFollow(FOLLOW_integral_literal_in_registers_directive1778);
/*  2109 */         regCount = integral_literal();
/*  2110 */         this.state._fsp -= 1;
/*       */ 
/*  2112 */         stream_integral_literal.add(regCount.getTree());
/*       */ 
/*  2120 */         retval.tree = root_0;
/*  2121 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*  2122 */         RewriteRuleSubtreeStream stream_regCount = new RewriteRuleSubtreeStream(this.adaptor, "rule regCount", regCount != null ? regCount.getTree() : null);
/*       */ 
/*  2124 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2129 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  2130 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(140, directive, "I_REGISTERS"), root_1);
/*  2131 */         this.adaptor.addChild(root_1, stream_regCount.nextTree());
/*  2132 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  2138 */         retval.tree = root_0;
/*       */ 
/*  2141 */         break;
/*       */       case 2:
/*  2145 */         directive = (Token)match(this.input, 185, FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1798);
/*  2146 */         stream_LOCALS_DIRECTIVE.add(directive);
/*       */ 
/*  2148 */         pushFollow(FOLLOW_integral_literal_in_registers_directive1802);
/*  2149 */         regCount2 = integral_literal();
/*  2150 */         this.state._fsp -= 1;
/*       */ 
/*  2152 */         stream_integral_literal.add(regCount2.getTree());
/*       */ 
/*  2160 */         retval.tree = root_0;
/*  2161 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*  2162 */         RewriteRuleSubtreeStream stream_regCount2 = new RewriteRuleSubtreeStream(this.adaptor, "rule regCount2", regCount2 != null ? regCount2.getTree() : null);
/*       */ 
/*  2164 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2169 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  2170 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(128, directive, "I_LOCALS"), root_1);
/*  2171 */         this.adaptor.addChild(root_1, stream_regCount2.nextTree());
/*  2172 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  2178 */         retval.tree = root_0;
/*       */       }
/*       */ 
/*  2186 */       if (((statements_and_directives_scope)this.statements_and_directives_stack.peek()).hasRegistersDirective) {
/*       */         throw new SemanticException(this.input, directive, "There can only be a single .registers or .locals directive in a method", new Object[0]);
/*       */       }
/*  2189 */       ((statements_and_directives_scope)this.statements_and_directives_stack.peek()).hasRegistersDirective = true;
/*       */ 
/*  2193 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  2195 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  2196 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  2200 */       reportError(re);
/*  2201 */       recover(this.input, re);
/*  2202 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  2207 */     return retval;
/*       */   }
/*       */ 
/*       */   public final simple_name_return simple_name()
/*       */     throws RecognitionException
/*       */   {
/*  2222 */     simple_name_return retval = new simple_name_return();
/*  2223 */     retval.start = this.input.LT(1);
/*       */ 
/*  2225 */     CommonTree root_0 = null;
/*       */ 
/*  2227 */     Token SIMPLE_NAME43 = null;
/*  2228 */     Token ACCESS_SPEC44 = null;
/*  2229 */     Token VERIFICATION_ERROR_TYPE45 = null;
/*  2230 */     Token POSITIVE_INTEGER_LITERAL46 = null;
/*  2231 */     Token NEGATIVE_INTEGER_LITERAL47 = null;
/*  2232 */     Token FLOAT_LITERAL_OR_ID48 = null;
/*  2233 */     Token DOUBLE_LITERAL_OR_ID49 = null;
/*  2234 */     Token BOOL_LITERAL50 = null;
/*  2235 */     Token NULL_LITERAL51 = null;
/*  2236 */     Token REGISTER52 = null;
/*  2237 */     Token PARAM_LIST_OR_ID53 = null;
/*  2238 */     Token PRIMITIVE_TYPE54 = null;
/*  2239 */     Token VOID_TYPE55 = null;
/*  2240 */     Token ANNOTATION_VISIBILITY56 = null;
/*  2241 */     Token INSTRUCTION_FORMAT10t57 = null;
/*  2242 */     Token INSTRUCTION_FORMAT10x58 = null;
/*  2243 */     Token INSTRUCTION_FORMAT10x_ODEX59 = null;
/*  2244 */     Token INSTRUCTION_FORMAT11x60 = null;
/*  2245 */     Token INSTRUCTION_FORMAT12x_OR_ID61 = null;
/*  2246 */     Token INSTRUCTION_FORMAT21c_FIELD62 = null;
/*  2247 */     Token INSTRUCTION_FORMAT21c_FIELD_ODEX63 = null;
/*  2248 */     Token INSTRUCTION_FORMAT21c_STRING64 = null;
/*  2249 */     Token INSTRUCTION_FORMAT21c_TYPE65 = null;
/*  2250 */     Token INSTRUCTION_FORMAT21t66 = null;
/*  2251 */     Token INSTRUCTION_FORMAT22c_FIELD67 = null;
/*  2252 */     Token INSTRUCTION_FORMAT22c_FIELD_ODEX68 = null;
/*  2253 */     Token INSTRUCTION_FORMAT22c_TYPE69 = null;
/*  2254 */     Token INSTRUCTION_FORMAT22cs_FIELD70 = null;
/*  2255 */     Token INSTRUCTION_FORMAT22s_OR_ID71 = null;
/*  2256 */     Token INSTRUCTION_FORMAT22t72 = null;
/*  2257 */     Token INSTRUCTION_FORMAT23x73 = null;
/*  2258 */     Token INSTRUCTION_FORMAT31i_OR_ID74 = null;
/*  2259 */     Token INSTRUCTION_FORMAT31t75 = null;
/*  2260 */     Token INSTRUCTION_FORMAT35c_METHOD76 = null;
/*  2261 */     Token INSTRUCTION_FORMAT35c_METHOD_ODEX77 = null;
/*  2262 */     Token INSTRUCTION_FORMAT35c_TYPE78 = null;
/*  2263 */     Token INSTRUCTION_FORMAT35mi_METHOD79 = null;
/*  2264 */     Token INSTRUCTION_FORMAT35ms_METHOD80 = null;
/*  2265 */     Token INSTRUCTION_FORMAT51l81 = null;
/*       */ 
/*  2267 */     CommonTree SIMPLE_NAME43_tree = null;
/*  2268 */     CommonTree ACCESS_SPEC44_tree = null;
/*  2269 */     CommonTree VERIFICATION_ERROR_TYPE45_tree = null;
/*  2270 */     CommonTree POSITIVE_INTEGER_LITERAL46_tree = null;
/*  2271 */     CommonTree NEGATIVE_INTEGER_LITERAL47_tree = null;
/*  2272 */     CommonTree FLOAT_LITERAL_OR_ID48_tree = null;
/*  2273 */     CommonTree DOUBLE_LITERAL_OR_ID49_tree = null;
/*  2274 */     CommonTree BOOL_LITERAL50_tree = null;
/*  2275 */     CommonTree NULL_LITERAL51_tree = null;
/*  2276 */     CommonTree REGISTER52_tree = null;
/*  2277 */     CommonTree PARAM_LIST_OR_ID53_tree = null;
/*  2278 */     CommonTree PRIMITIVE_TYPE54_tree = null;
/*  2279 */     CommonTree VOID_TYPE55_tree = null;
/*  2280 */     CommonTree ANNOTATION_VISIBILITY56_tree = null;
/*  2281 */     CommonTree INSTRUCTION_FORMAT10t57_tree = null;
/*  2282 */     CommonTree INSTRUCTION_FORMAT10x58_tree = null;
/*  2283 */     CommonTree INSTRUCTION_FORMAT10x_ODEX59_tree = null;
/*  2284 */     CommonTree INSTRUCTION_FORMAT11x60_tree = null;
/*  2285 */     CommonTree INSTRUCTION_FORMAT12x_OR_ID61_tree = null;
/*  2286 */     CommonTree INSTRUCTION_FORMAT21c_FIELD62_tree = null;
/*  2287 */     CommonTree INSTRUCTION_FORMAT21c_FIELD_ODEX63_tree = null;
/*  2288 */     CommonTree INSTRUCTION_FORMAT21c_STRING64_tree = null;
/*  2289 */     CommonTree INSTRUCTION_FORMAT21c_TYPE65_tree = null;
/*  2290 */     CommonTree INSTRUCTION_FORMAT21t66_tree = null;
/*  2291 */     CommonTree INSTRUCTION_FORMAT22c_FIELD67_tree = null;
/*  2292 */     CommonTree INSTRUCTION_FORMAT22c_FIELD_ODEX68_tree = null;
/*  2293 */     CommonTree INSTRUCTION_FORMAT22c_TYPE69_tree = null;
/*  2294 */     CommonTree INSTRUCTION_FORMAT22cs_FIELD70_tree = null;
/*  2295 */     CommonTree INSTRUCTION_FORMAT22s_OR_ID71_tree = null;
/*  2296 */     CommonTree INSTRUCTION_FORMAT22t72_tree = null;
/*  2297 */     CommonTree INSTRUCTION_FORMAT23x73_tree = null;
/*  2298 */     CommonTree INSTRUCTION_FORMAT31i_OR_ID74_tree = null;
/*  2299 */     CommonTree INSTRUCTION_FORMAT31t75_tree = null;
/*  2300 */     CommonTree INSTRUCTION_FORMAT35c_METHOD76_tree = null;
/*  2301 */     CommonTree INSTRUCTION_FORMAT35c_METHOD_ODEX77_tree = null;
/*  2302 */     CommonTree INSTRUCTION_FORMAT35c_TYPE78_tree = null;
/*  2303 */     CommonTree INSTRUCTION_FORMAT35mi_METHOD79_tree = null;
/*  2304 */     CommonTree INSTRUCTION_FORMAT35ms_METHOD80_tree = null;
/*  2305 */     CommonTree INSTRUCTION_FORMAT51l81_tree = null;
/*  2306 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22c_TYPE = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_TYPE");
/*  2307 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT35c_METHOD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD");
/*  2308 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT11x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11x");
/*  2309 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21t");
/*  2310 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT35c_TYPE = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_TYPE");
/*  2311 */     RewriteRuleTokenStream stream_ANNOTATION_VISIBILITY = new RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_VISIBILITY");
/*  2312 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT31i_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31i_OR_ID");
/*  2313 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22s_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22s_OR_ID");
/*  2314 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT51l = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT51l");
/*  2315 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT23x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT23x");
/*  2316 */     RewriteRuleTokenStream stream_NULL_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token NULL_LITERAL");
/*  2317 */     RewriteRuleTokenStream stream_BOOL_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token BOOL_LITERAL");
/*  2318 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_FIELD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD");
/*  2319 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT35c_METHOD_ODEX = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD_ODEX");
/*  2320 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22c_FIELD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD");
/*  2321 */     RewriteRuleTokenStream stream_ACCESS_SPEC = new RewriteRuleTokenStream(this.adaptor, "token ACCESS_SPEC");
/*  2322 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_STRING = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_STRING");
/*  2323 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT12x_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT12x_OR_ID");
/*  2324 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT35ms_METHOD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35ms_METHOD");
/*  2325 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT35mi_METHOD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35mi_METHOD");
/*  2326 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22cs_FIELD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22cs_FIELD");
/*  2327 */     RewriteRuleTokenStream stream_VOID_TYPE = new RewriteRuleTokenStream(this.adaptor, "token VOID_TYPE");
/*  2328 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT10x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10x");
/*  2329 */     RewriteRuleTokenStream stream_FLOAT_LITERAL_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token FLOAT_LITERAL_OR_ID");
/*  2330 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22t");
/*  2331 */     RewriteRuleTokenStream stream_PRIMITIVE_TYPE = new RewriteRuleTokenStream(this.adaptor, "token PRIMITIVE_TYPE");
/*  2332 */     RewriteRuleTokenStream stream_PARAM_LIST_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token PARAM_LIST_OR_ID");
/*  2333 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT10x_ODEX = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10x_ODEX");
/*  2334 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT31t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31t");
/*  2335 */     RewriteRuleTokenStream stream_DOUBLE_LITERAL_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_LITERAL_OR_ID");
/*  2336 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22c_FIELD_ODEX = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD_ODEX");
/*  2337 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT10t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10t");
/*  2338 */     RewriteRuleTokenStream stream_NEGATIVE_INTEGER_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token NEGATIVE_INTEGER_LITERAL");
/*  2339 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*  2340 */     RewriteRuleTokenStream stream_VERIFICATION_ERROR_TYPE = new RewriteRuleTokenStream(this.adaptor, "token VERIFICATION_ERROR_TYPE");
/*  2341 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_TYPE = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_TYPE");
/*  2342 */     RewriteRuleTokenStream stream_POSITIVE_INTEGER_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token POSITIVE_INTEGER_LITERAL");
/*  2343 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_FIELD_ODEX = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD_ODEX");
/*       */     try
/*       */     {
/*  2347 */       int alt9 = 39;
/*  2348 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 205:
/*  2351 */         alt9 = 1;
/*       */ 
/*  2353 */         break;
/*       */       case 4:
/*  2356 */         alt9 = 2;
/*       */ 
/*  2358 */         break;
/*       */       case 211:
/*  2361 */         alt9 = 3;
/*       */ 
/*  2363 */         break;
/*       */       case 198:
/*  2366 */         alt9 = 4;
/*       */ 
/*  2368 */         break;
/*       */       case 190:
/*  2371 */         alt9 = 5;
/*       */ 
/*  2373 */         break;
/*       */       case 52:
/*  2376 */         alt9 = 6;
/*       */ 
/*  2378 */         break;
/*       */       case 35:
/*  2381 */         alt9 = 7;
/*       */ 
/*  2383 */         break;
/*       */       case 21:
/*  2386 */         alt9 = 8;
/*       */ 
/*  2388 */         break;
/*       */       case 191:
/*  2391 */         alt9 = 9;
/*       */ 
/*  2393 */         break;
/*       */       case 201:
/*  2396 */         alt9 = 10;
/*       */ 
/*  2398 */         break;
/*       */       case 197:
/*  2401 */         alt9 = 11;
/*       */ 
/*  2403 */         break;
/*       */       case 199:
/*  2406 */         alt9 = 12;
/*       */ 
/*  2408 */         break;
/*       */       case 212:
/*  2411 */         alt9 = 13;
/*       */ 
/*  2413 */         break;
/*       */       case 6:
/*  2416 */         alt9 = 14;
/*       */ 
/*  2418 */         break;
/*       */       case 58:
/*  2421 */         alt9 = 15;
/*       */ 
/*  2423 */         break;
/*       */       case 59:
/*  2426 */         alt9 = 16;
/*       */ 
/*  2428 */         break;
/*       */       case 60:
/*  2431 */         alt9 = 17;
/*       */ 
/*  2433 */         break;
/*       */       case 62:
/*  2436 */         alt9 = 18;
/*       */ 
/*  2438 */         break;
/*       */       case 64:
/*  2441 */         alt9 = 19;
/*       */ 
/*  2443 */         break;
/*       */       case 67:
/*  2446 */         alt9 = 20;
/*       */ 
/*  2448 */         break;
/*       */       case 68:
/*  2451 */         alt9 = 21;
/*       */ 
/*  2453 */         break;
/*       */       case 69:
/*  2456 */         alt9 = 22;
/*       */ 
/*  2458 */         break;
/*       */       case 70:
/*  2461 */         alt9 = 23;
/*       */ 
/*  2463 */         break;
/*       */       case 74:
/*  2466 */         alt9 = 24;
/*       */ 
/*  2468 */         break;
/*       */       case 76:
/*  2471 */         alt9 = 25;
/*       */ 
/*  2473 */         break;
/*       */       case 77:
/*  2476 */         alt9 = 26;
/*       */ 
/*  2478 */         break;
/*       */       case 78:
/*  2481 */         alt9 = 27;
/*       */ 
/*  2483 */         break;
/*       */       case 79:
/*  2486 */         alt9 = 28;
/*       */ 
/*  2488 */         break;
/*       */       case 81:
/*  2491 */         alt9 = 29;
/*       */ 
/*  2493 */         break;
/*       */       case 82:
/*  2496 */         alt9 = 30;
/*       */ 
/*  2498 */         break;
/*       */       case 84:
/*  2501 */         alt9 = 31;
/*       */ 
/*  2503 */         break;
/*       */       case 88:
/*  2506 */         alt9 = 32;
/*       */ 
/*  2508 */         break;
/*       */       case 89:
/*  2511 */         alt9 = 33;
/*       */ 
/*  2513 */         break;
/*       */       case 91:
/*  2516 */         alt9 = 34;
/*       */ 
/*  2518 */         break;
/*       */       case 92:
/*  2521 */         alt9 = 35;
/*       */ 
/*  2523 */         break;
/*       */       case 93:
/*  2526 */         alt9 = 36;
/*       */ 
/*  2528 */         break;
/*       */       case 94:
/*  2531 */         alt9 = 37;
/*       */ 
/*  2533 */         break;
/*       */       case 95:
/*  2536 */         alt9 = 38;
/*       */ 
/*  2538 */         break;
/*       */       case 101:
/*  2541 */         alt9 = 39;
/*       */ 
/*  2543 */         break;
/*       */       default:
/*  2545 */         NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  2549 */       switch (alt9)
/*       */       {
/*       */       case 1:
/*  2553 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2556 */         SIMPLE_NAME43 = (Token)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_simple_name1836);
/*  2557 */         SIMPLE_NAME43_tree = (CommonTree)this.adaptor.create(SIMPLE_NAME43);
/*  2558 */         this.adaptor.addChild(root_0, SIMPLE_NAME43_tree);
/*       */ 
/*  2561 */         break;
/*       */       case 2:
/*  2565 */         ACCESS_SPEC44 = (Token)match(this.input, 4, FOLLOW_ACCESS_SPEC_in_simple_name1842);
/*  2566 */         stream_ACCESS_SPEC.add(ACCESS_SPEC44);
/*       */ 
/*  2575 */         retval.tree = root_0;
/*  2576 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2578 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2581 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, ACCESS_SPEC44));
/*       */ 
/*  2585 */         retval.tree = root_0;
/*       */ 
/*  2588 */         break;
/*       */       case 3:
/*  2592 */         VERIFICATION_ERROR_TYPE45 = (Token)match(this.input, 211, FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1853);
/*  2593 */         stream_VERIFICATION_ERROR_TYPE.add(VERIFICATION_ERROR_TYPE45);
/*       */ 
/*  2602 */         retval.tree = root_0;
/*  2603 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2605 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2608 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, VERIFICATION_ERROR_TYPE45));
/*       */ 
/*  2612 */         retval.tree = root_0;
/*       */ 
/*  2615 */         break;
/*       */       case 4:
/*  2619 */         POSITIVE_INTEGER_LITERAL46 = (Token)match(this.input, 198, FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1864);
/*  2620 */         stream_POSITIVE_INTEGER_LITERAL.add(POSITIVE_INTEGER_LITERAL46);
/*       */ 
/*  2629 */         retval.tree = root_0;
/*  2630 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2632 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2635 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, POSITIVE_INTEGER_LITERAL46));
/*       */ 
/*  2639 */         retval.tree = root_0;
/*       */ 
/*  2642 */         break;
/*       */       case 5:
/*  2646 */         NEGATIVE_INTEGER_LITERAL47 = (Token)match(this.input, 190, FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1875);
/*  2647 */         stream_NEGATIVE_INTEGER_LITERAL.add(NEGATIVE_INTEGER_LITERAL47);
/*       */ 
/*  2656 */         retval.tree = root_0;
/*  2657 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2659 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2662 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, NEGATIVE_INTEGER_LITERAL47));
/*       */ 
/*  2666 */         retval.tree = root_0;
/*       */ 
/*  2669 */         break;
/*       */       case 6:
/*  2673 */         FLOAT_LITERAL_OR_ID48 = (Token)match(this.input, 52, FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1886);
/*  2674 */         stream_FLOAT_LITERAL_OR_ID.add(FLOAT_LITERAL_OR_ID48);
/*       */ 
/*  2683 */         retval.tree = root_0;
/*  2684 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2686 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2689 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, FLOAT_LITERAL_OR_ID48));
/*       */ 
/*  2693 */         retval.tree = root_0;
/*       */ 
/*  2696 */         break;
/*       */       case 7:
/*  2700 */         DOUBLE_LITERAL_OR_ID49 = (Token)match(this.input, 35, FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1897);
/*  2701 */         stream_DOUBLE_LITERAL_OR_ID.add(DOUBLE_LITERAL_OR_ID49);
/*       */ 
/*  2710 */         retval.tree = root_0;
/*  2711 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2713 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2716 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, DOUBLE_LITERAL_OR_ID49));
/*       */ 
/*  2720 */         retval.tree = root_0;
/*       */ 
/*  2723 */         break;
/*       */       case 8:
/*  2727 */         BOOL_LITERAL50 = (Token)match(this.input, 21, FOLLOW_BOOL_LITERAL_in_simple_name1908);
/*  2728 */         stream_BOOL_LITERAL.add(BOOL_LITERAL50);
/*       */ 
/*  2737 */         retval.tree = root_0;
/*  2738 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2740 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2743 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, BOOL_LITERAL50));
/*       */ 
/*  2747 */         retval.tree = root_0;
/*       */ 
/*  2750 */         break;
/*       */       case 9:
/*  2754 */         NULL_LITERAL51 = (Token)match(this.input, 191, FOLLOW_NULL_LITERAL_in_simple_name1919);
/*  2755 */         stream_NULL_LITERAL.add(NULL_LITERAL51);
/*       */ 
/*  2764 */         retval.tree = root_0;
/*  2765 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2767 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2770 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, NULL_LITERAL51));
/*       */ 
/*  2774 */         retval.tree = root_0;
/*       */ 
/*  2777 */         break;
/*       */       case 10:
/*  2781 */         REGISTER52 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_simple_name1930);
/*  2782 */         stream_REGISTER.add(REGISTER52);
/*       */ 
/*  2791 */         retval.tree = root_0;
/*  2792 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2794 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2797 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, REGISTER52));
/*       */ 
/*  2801 */         retval.tree = root_0;
/*       */ 
/*  2804 */         break;
/*       */       case 11:
/*  2808 */         PARAM_LIST_OR_ID53 = (Token)match(this.input, 197, FOLLOW_PARAM_LIST_OR_ID_in_simple_name1941);
/*  2809 */         stream_PARAM_LIST_OR_ID.add(PARAM_LIST_OR_ID53);
/*       */ 
/*  2818 */         retval.tree = root_0;
/*  2819 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2821 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2824 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, PARAM_LIST_OR_ID53));
/*       */ 
/*  2828 */         retval.tree = root_0;
/*       */ 
/*  2831 */         break;
/*       */       case 12:
/*  2835 */         PRIMITIVE_TYPE54 = (Token)match(this.input, 199, FOLLOW_PRIMITIVE_TYPE_in_simple_name1952);
/*  2836 */         stream_PRIMITIVE_TYPE.add(PRIMITIVE_TYPE54);
/*       */ 
/*  2845 */         retval.tree = root_0;
/*  2846 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2848 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2851 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, PRIMITIVE_TYPE54));
/*       */ 
/*  2855 */         retval.tree = root_0;
/*       */ 
/*  2858 */         break;
/*       */       case 13:
/*  2862 */         VOID_TYPE55 = (Token)match(this.input, 212, FOLLOW_VOID_TYPE_in_simple_name1963);
/*  2863 */         stream_VOID_TYPE.add(VOID_TYPE55);
/*       */ 
/*  2872 */         retval.tree = root_0;
/*  2873 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2875 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2878 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, VOID_TYPE55));
/*       */ 
/*  2882 */         retval.tree = root_0;
/*       */ 
/*  2885 */         break;
/*       */       case 14:
/*  2889 */         ANNOTATION_VISIBILITY56 = (Token)match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_simple_name1974);
/*  2890 */         stream_ANNOTATION_VISIBILITY.add(ANNOTATION_VISIBILITY56);
/*       */ 
/*  2899 */         retval.tree = root_0;
/*  2900 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2902 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2905 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, ANNOTATION_VISIBILITY56));
/*       */ 
/*  2909 */         retval.tree = root_0;
/*       */ 
/*  2912 */         break;
/*       */       case 15:
/*  2916 */         INSTRUCTION_FORMAT10t57 = (Token)match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name1985);
/*  2917 */         stream_INSTRUCTION_FORMAT10t.add(INSTRUCTION_FORMAT10t57);
/*       */ 
/*  2926 */         retval.tree = root_0;
/*  2927 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2929 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2932 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT10t57));
/*       */ 
/*  2936 */         retval.tree = root_0;
/*       */ 
/*  2939 */         break;
/*       */       case 16:
/*  2943 */         INSTRUCTION_FORMAT10x58 = (Token)match(this.input, 59, FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name1996);
/*  2944 */         stream_INSTRUCTION_FORMAT10x.add(INSTRUCTION_FORMAT10x58);
/*       */ 
/*  2953 */         retval.tree = root_0;
/*  2954 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2956 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2959 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT10x58));
/*       */ 
/*  2963 */         retval.tree = root_0;
/*       */ 
/*  2966 */         break;
/*       */       case 17:
/*  2970 */         INSTRUCTION_FORMAT10x_ODEX59 = (Token)match(this.input, 60, FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2007);
/*  2971 */         stream_INSTRUCTION_FORMAT10x_ODEX.add(INSTRUCTION_FORMAT10x_ODEX59);
/*       */ 
/*  2980 */         retval.tree = root_0;
/*  2981 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  2983 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  2986 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT10x_ODEX59));
/*       */ 
/*  2990 */         retval.tree = root_0;
/*       */ 
/*  2993 */         break;
/*       */       case 18:
/*  2997 */         INSTRUCTION_FORMAT11x60 = (Token)match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2018);
/*  2998 */         stream_INSTRUCTION_FORMAT11x.add(INSTRUCTION_FORMAT11x60);
/*       */ 
/*  3007 */         retval.tree = root_0;
/*  3008 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3010 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3013 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT11x60));
/*       */ 
/*  3017 */         retval.tree = root_0;
/*       */ 
/*  3020 */         break;
/*       */       case 19:
/*  3024 */         INSTRUCTION_FORMAT12x_OR_ID61 = (Token)match(this.input, 64, FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2029);
/*  3025 */         stream_INSTRUCTION_FORMAT12x_OR_ID.add(INSTRUCTION_FORMAT12x_OR_ID61);
/*       */ 
/*  3034 */         retval.tree = root_0;
/*  3035 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3037 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3040 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT12x_OR_ID61));
/*       */ 
/*  3044 */         retval.tree = root_0;
/*       */ 
/*  3047 */         break;
/*       */       case 20:
/*  3051 */         INSTRUCTION_FORMAT21c_FIELD62 = (Token)match(this.input, 67, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2040);
/*  3052 */         stream_INSTRUCTION_FORMAT21c_FIELD.add(INSTRUCTION_FORMAT21c_FIELD62);
/*       */ 
/*  3061 */         retval.tree = root_0;
/*  3062 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3064 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3067 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT21c_FIELD62));
/*       */ 
/*  3071 */         retval.tree = root_0;
/*       */ 
/*  3074 */         break;
/*       */       case 21:
/*  3078 */         INSTRUCTION_FORMAT21c_FIELD_ODEX63 = (Token)match(this.input, 68, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2051);
/*  3079 */         stream_INSTRUCTION_FORMAT21c_FIELD_ODEX.add(INSTRUCTION_FORMAT21c_FIELD_ODEX63);
/*       */ 
/*  3088 */         retval.tree = root_0;
/*  3089 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3091 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3094 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT21c_FIELD_ODEX63));
/*       */ 
/*  3098 */         retval.tree = root_0;
/*       */ 
/*  3101 */         break;
/*       */       case 22:
/*  3105 */         INSTRUCTION_FORMAT21c_STRING64 = (Token)match(this.input, 69, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2062);
/*  3106 */         stream_INSTRUCTION_FORMAT21c_STRING.add(INSTRUCTION_FORMAT21c_STRING64);
/*       */ 
/*  3115 */         retval.tree = root_0;
/*  3116 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3118 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3121 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT21c_STRING64));
/*       */ 
/*  3125 */         retval.tree = root_0;
/*       */ 
/*  3128 */         break;
/*       */       case 23:
/*  3132 */         INSTRUCTION_FORMAT21c_TYPE65 = (Token)match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2073);
/*  3133 */         stream_INSTRUCTION_FORMAT21c_TYPE.add(INSTRUCTION_FORMAT21c_TYPE65);
/*       */ 
/*  3142 */         retval.tree = root_0;
/*  3143 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3145 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3148 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT21c_TYPE65));
/*       */ 
/*  3152 */         retval.tree = root_0;
/*       */ 
/*  3155 */         break;
/*       */       case 24:
/*  3159 */         INSTRUCTION_FORMAT21t66 = (Token)match(this.input, 74, FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2084);
/*  3160 */         stream_INSTRUCTION_FORMAT21t.add(INSTRUCTION_FORMAT21t66);
/*       */ 
/*  3169 */         retval.tree = root_0;
/*  3170 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3172 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3175 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT21t66));
/*       */ 
/*  3179 */         retval.tree = root_0;
/*       */ 
/*  3182 */         break;
/*       */       case 25:
/*  3186 */         INSTRUCTION_FORMAT22c_FIELD67 = (Token)match(this.input, 76, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2095);
/*  3187 */         stream_INSTRUCTION_FORMAT22c_FIELD.add(INSTRUCTION_FORMAT22c_FIELD67);
/*       */ 
/*  3196 */         retval.tree = root_0;
/*  3197 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3199 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3202 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT22c_FIELD67));
/*       */ 
/*  3206 */         retval.tree = root_0;
/*       */ 
/*  3209 */         break;
/*       */       case 26:
/*  3213 */         INSTRUCTION_FORMAT22c_FIELD_ODEX68 = (Token)match(this.input, 77, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2106);
/*  3214 */         stream_INSTRUCTION_FORMAT22c_FIELD_ODEX.add(INSTRUCTION_FORMAT22c_FIELD_ODEX68);
/*       */ 
/*  3223 */         retval.tree = root_0;
/*  3224 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3226 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3229 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT22c_FIELD_ODEX68));
/*       */ 
/*  3233 */         retval.tree = root_0;
/*       */ 
/*  3236 */         break;
/*       */       case 27:
/*  3240 */         INSTRUCTION_FORMAT22c_TYPE69 = (Token)match(this.input, 78, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2117);
/*  3241 */         stream_INSTRUCTION_FORMAT22c_TYPE.add(INSTRUCTION_FORMAT22c_TYPE69);
/*       */ 
/*  3250 */         retval.tree = root_0;
/*  3251 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3253 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3256 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT22c_TYPE69));
/*       */ 
/*  3260 */         retval.tree = root_0;
/*       */ 
/*  3263 */         break;
/*       */       case 28:
/*  3267 */         INSTRUCTION_FORMAT22cs_FIELD70 = (Token)match(this.input, 79, FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2128);
/*  3268 */         stream_INSTRUCTION_FORMAT22cs_FIELD.add(INSTRUCTION_FORMAT22cs_FIELD70);
/*       */ 
/*  3277 */         retval.tree = root_0;
/*  3278 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3280 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3283 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT22cs_FIELD70));
/*       */ 
/*  3287 */         retval.tree = root_0;
/*       */ 
/*  3290 */         break;
/*       */       case 29:
/*  3294 */         INSTRUCTION_FORMAT22s_OR_ID71 = (Token)match(this.input, 81, FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2139);
/*  3295 */         stream_INSTRUCTION_FORMAT22s_OR_ID.add(INSTRUCTION_FORMAT22s_OR_ID71);
/*       */ 
/*  3304 */         retval.tree = root_0;
/*  3305 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3307 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3310 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT22s_OR_ID71));
/*       */ 
/*  3314 */         retval.tree = root_0;
/*       */ 
/*  3317 */         break;
/*       */       case 30:
/*  3321 */         INSTRUCTION_FORMAT22t72 = (Token)match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2150);
/*  3322 */         stream_INSTRUCTION_FORMAT22t.add(INSTRUCTION_FORMAT22t72);
/*       */ 
/*  3331 */         retval.tree = root_0;
/*  3332 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3334 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3337 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT22t72));
/*       */ 
/*  3341 */         retval.tree = root_0;
/*       */ 
/*  3344 */         break;
/*       */       case 31:
/*  3348 */         INSTRUCTION_FORMAT23x73 = (Token)match(this.input, 84, FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2161);
/*  3349 */         stream_INSTRUCTION_FORMAT23x.add(INSTRUCTION_FORMAT23x73);
/*       */ 
/*  3358 */         retval.tree = root_0;
/*  3359 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3361 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3364 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT23x73));
/*       */ 
/*  3368 */         retval.tree = root_0;
/*       */ 
/*  3371 */         break;
/*       */       case 32:
/*  3375 */         INSTRUCTION_FORMAT31i_OR_ID74 = (Token)match(this.input, 88, FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2172);
/*  3376 */         stream_INSTRUCTION_FORMAT31i_OR_ID.add(INSTRUCTION_FORMAT31i_OR_ID74);
/*       */ 
/*  3385 */         retval.tree = root_0;
/*  3386 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3388 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3391 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT31i_OR_ID74));
/*       */ 
/*  3395 */         retval.tree = root_0;
/*       */ 
/*  3398 */         break;
/*       */       case 33:
/*  3402 */         INSTRUCTION_FORMAT31t75 = (Token)match(this.input, 89, FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2183);
/*  3403 */         stream_INSTRUCTION_FORMAT31t.add(INSTRUCTION_FORMAT31t75);
/*       */ 
/*  3412 */         retval.tree = root_0;
/*  3413 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3415 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3418 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT31t75));
/*       */ 
/*  3422 */         retval.tree = root_0;
/*       */ 
/*  3425 */         break;
/*       */       case 34:
/*  3429 */         INSTRUCTION_FORMAT35c_METHOD76 = (Token)match(this.input, 91, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2194);
/*  3430 */         stream_INSTRUCTION_FORMAT35c_METHOD.add(INSTRUCTION_FORMAT35c_METHOD76);
/*       */ 
/*  3439 */         retval.tree = root_0;
/*  3440 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3442 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3445 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT35c_METHOD76));
/*       */ 
/*  3449 */         retval.tree = root_0;
/*       */ 
/*  3452 */         break;
/*       */       case 35:
/*  3456 */         INSTRUCTION_FORMAT35c_METHOD_ODEX77 = (Token)match(this.input, 92, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2205);
/*  3457 */         stream_INSTRUCTION_FORMAT35c_METHOD_ODEX.add(INSTRUCTION_FORMAT35c_METHOD_ODEX77);
/*       */ 
/*  3466 */         retval.tree = root_0;
/*  3467 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3469 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3472 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT35c_METHOD_ODEX77));
/*       */ 
/*  3476 */         retval.tree = root_0;
/*       */ 
/*  3479 */         break;
/*       */       case 36:
/*  3483 */         INSTRUCTION_FORMAT35c_TYPE78 = (Token)match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2216);
/*  3484 */         stream_INSTRUCTION_FORMAT35c_TYPE.add(INSTRUCTION_FORMAT35c_TYPE78);
/*       */ 
/*  3493 */         retval.tree = root_0;
/*  3494 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3496 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3499 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT35c_TYPE78));
/*       */ 
/*  3503 */         retval.tree = root_0;
/*       */ 
/*  3506 */         break;
/*       */       case 37:
/*  3510 */         INSTRUCTION_FORMAT35mi_METHOD79 = (Token)match(this.input, 94, FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2227);
/*  3511 */         stream_INSTRUCTION_FORMAT35mi_METHOD.add(INSTRUCTION_FORMAT35mi_METHOD79);
/*       */ 
/*  3520 */         retval.tree = root_0;
/*  3521 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3523 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3526 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT35mi_METHOD79));
/*       */ 
/*  3530 */         retval.tree = root_0;
/*       */ 
/*  3533 */         break;
/*       */       case 38:
/*  3537 */         INSTRUCTION_FORMAT35ms_METHOD80 = (Token)match(this.input, 95, FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2238);
/*  3538 */         stream_INSTRUCTION_FORMAT35ms_METHOD.add(INSTRUCTION_FORMAT35ms_METHOD80);
/*       */ 
/*  3547 */         retval.tree = root_0;
/*  3548 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3550 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3553 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT35ms_METHOD80));
/*       */ 
/*  3557 */         retval.tree = root_0;
/*       */ 
/*  3560 */         break;
/*       */       case 39:
/*  3564 */         INSTRUCTION_FORMAT51l81 = (Token)match(this.input, 101, FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2249);
/*  3565 */         stream_INSTRUCTION_FORMAT51l.add(INSTRUCTION_FORMAT51l81);
/*       */ 
/*  3574 */         retval.tree = root_0;
/*  3575 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3577 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3580 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, INSTRUCTION_FORMAT51l81));
/*       */ 
/*  3584 */         retval.tree = root_0;
/*       */       }
/*       */ 
/*  3590 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  3592 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  3593 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  3597 */       reportError(re);
/*  3598 */       recover(this.input, re);
/*  3599 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  3604 */     return retval;
/*       */   }
/*       */ 
/*       */   public final member_name_return member_name()
/*       */     throws RecognitionException
/*       */   {
/*  3619 */     member_name_return retval = new member_name_return();
/*  3620 */     retval.start = this.input.LT(1);
/*       */ 
/*  3622 */     CommonTree root_0 = null;
/*       */ 
/*  3624 */     Token MEMBER_NAME83 = null;
/*  3625 */     ParserRuleReturnScope simple_name82 = null;
/*       */ 
/*  3627 */     CommonTree MEMBER_NAME83_tree = null;
/*  3628 */     RewriteRuleTokenStream stream_MEMBER_NAME = new RewriteRuleTokenStream(this.adaptor, "token MEMBER_NAME");
/*       */     try
/*       */     {
/*  3632 */       int alt10 = 2;
/*  3633 */       int LA10_0 = this.input.LA(1);
/*  3634 */       if ((LA10_0 == 4) || (LA10_0 == 6) || (LA10_0 == 21) || (LA10_0 == 35) || (LA10_0 == 52) || ((LA10_0 >= 58) && (LA10_0 <= 60)) || (LA10_0 == 62) || (LA10_0 == 64) || ((LA10_0 >= 67) && (LA10_0 <= 70)) || (LA10_0 == 74) || ((LA10_0 >= 76) && (LA10_0 <= 79)) || ((LA10_0 >= 81) && (LA10_0 <= 82)) || (LA10_0 == 84) || ((LA10_0 >= 88) && (LA10_0 <= 89)) || ((LA10_0 >= 91) && (LA10_0 <= 95)) || (LA10_0 == 101) || ((LA10_0 >= 190) && (LA10_0 <= 191)) || ((LA10_0 >= 197) && (LA10_0 <= 199)) || (LA10_0 == 201) || (LA10_0 == 205) || ((LA10_0 >= 211) && (LA10_0 <= 212))) {
/*  3635 */         alt10 = 1;
/*       */       }
/*  3637 */       else if (LA10_0 == 188) {
/*  3638 */         alt10 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  3642 */         NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  3647 */       switch (alt10)
/*       */       {
/*       */       case 1:
/*  3651 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3654 */         pushFollow(FOLLOW_simple_name_in_member_name2264);
/*  3655 */         simple_name82 = simple_name();
/*  3656 */         this.state._fsp -= 1;
/*       */ 
/*  3658 */         this.adaptor.addChild(root_0, simple_name82.getTree());
/*       */ 
/*  3661 */         break;
/*       */       case 2:
/*  3665 */         MEMBER_NAME83 = (Token)match(this.input, 188, FOLLOW_MEMBER_NAME_in_member_name2270);
/*  3666 */         stream_MEMBER_NAME.add(MEMBER_NAME83);
/*       */ 
/*  3675 */         retval.tree = root_0;
/*  3676 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3678 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3681 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(205, MEMBER_NAME83));
/*       */ 
/*  3685 */         retval.tree = root_0;
/*       */       }
/*       */ 
/*  3691 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  3693 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  3694 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  3698 */       reportError(re);
/*  3699 */       recover(this.input, re);
/*  3700 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  3705 */     return retval;
/*       */   }
/*       */ 
/*       */   public final method_prototype_return method_prototype()
/*       */     throws RecognitionException
/*       */   {
/*  3720 */     method_prototype_return retval = new method_prototype_return();
/*  3721 */     retval.start = this.input.LT(1);
/*       */ 
/*  3723 */     CommonTree root_0 = null;
/*       */ 
/*  3725 */     Token OPEN_PAREN84 = null;
/*  3726 */     Token CLOSE_PAREN86 = null;
/*  3727 */     ParserRuleReturnScope param_list85 = null;
/*  3728 */     ParserRuleReturnScope type_descriptor87 = null;
/*       */ 
/*  3730 */     CommonTree OPEN_PAREN84_tree = null;
/*  3731 */     CommonTree CLOSE_PAREN86_tree = null;
/*  3732 */     RewriteRuleTokenStream stream_OPEN_PAREN = new RewriteRuleTokenStream(this.adaptor, "token OPEN_PAREN");
/*  3733 */     RewriteRuleTokenStream stream_CLOSE_PAREN = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_PAREN");
/*  3734 */     RewriteRuleSubtreeStream stream_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule type_descriptor");
/*  3735 */     RewriteRuleSubtreeStream stream_param_list = new RewriteRuleSubtreeStream(this.adaptor, "rule param_list");
/*       */     try
/*       */     {
/*  3741 */       OPEN_PAREN84 = (Token)match(this.input, 193, FOLLOW_OPEN_PAREN_in_method_prototype2285);
/*  3742 */       stream_OPEN_PAREN.add(OPEN_PAREN84);
/*       */ 
/*  3744 */       pushFollow(FOLLOW_param_list_in_method_prototype2287);
/*  3745 */       param_list85 = param_list();
/*  3746 */       this.state._fsp -= 1;
/*       */ 
/*  3748 */       stream_param_list.add(param_list85.getTree());
/*  3749 */       CLOSE_PAREN86 = (Token)match(this.input, 29, FOLLOW_CLOSE_PAREN_in_method_prototype2289);
/*  3750 */       stream_CLOSE_PAREN.add(CLOSE_PAREN86);
/*       */ 
/*  3752 */       pushFollow(FOLLOW_type_descriptor_in_method_prototype2291);
/*  3753 */       type_descriptor87 = type_descriptor();
/*  3754 */       this.state._fsp -= 1;
/*       */ 
/*  3756 */       stream_type_descriptor.add(type_descriptor87.getTree());
/*       */ 
/*  3764 */       retval.tree = root_0;
/*  3765 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3767 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3772 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  3773 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(131, retval.start, "I_METHOD_PROTOTYPE"), root_1);
/*       */ 
/*  3776 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  3777 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(132, "I_METHOD_RETURN_TYPE"), root_2);
/*  3778 */       this.adaptor.addChild(root_2, stream_type_descriptor.nextTree());
/*  3779 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/*  3783 */       if (stream_param_list.hasNext()) {
/*  3784 */         this.adaptor.addChild(root_1, stream_param_list.nextTree());
/*       */       }
/*  3786 */       stream_param_list.reset();
/*       */ 
/*  3788 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  3794 */       retval.tree = root_0;
/*       */ 
/*  3798 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  3800 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  3801 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  3805 */       reportError(re);
/*  3806 */       recover(this.input, re);
/*  3807 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  3812 */     return retval;
/*       */   }
/*       */ 
/*       */   public final param_list_return param_list()
/*       */     throws RecognitionException
/*       */   {
/*  3827 */     param_list_return retval = new param_list_return();
/*  3828 */     retval.start = this.input.LT(1);
/*       */ 
/*  3830 */     CommonTree root_0 = null;
/*       */ 
/*  3832 */     Token PARAM_LIST88 = null;
/*  3833 */     Token PARAM_LIST_OR_ID89 = null;
/*  3834 */     ParserRuleReturnScope nonvoid_type_descriptor90 = null;
/*       */ 
/*  3836 */     CommonTree PARAM_LIST88_tree = null;
/*  3837 */     CommonTree PARAM_LIST_OR_ID89_tree = null;
/*  3838 */     RewriteRuleTokenStream stream_PARAM_LIST = new RewriteRuleTokenStream(this.adaptor, "token PARAM_LIST");
/*  3839 */     RewriteRuleTokenStream stream_PARAM_LIST_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token PARAM_LIST_OR_ID");
/*       */     try
/*       */     {
/*  3843 */       int alt12 = 3;
/*  3844 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 196:
/*  3847 */         alt12 = 1;
/*       */ 
/*  3849 */         break;
/*       */       case 197:
/*  3852 */         alt12 = 2;
/*       */ 
/*  3854 */         break;
/*       */       case 8:
/*       */       case 26:
/*       */       case 29:
/*       */       case 199:
/*  3860 */         alt12 = 3;
/*       */ 
/*  3862 */         break;
/*       */       default:
/*  3864 */         NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  3868 */       switch (alt12)
/*       */       {
/*       */       case 1:
/*  3872 */         PARAM_LIST88 = (Token)match(this.input, 196, FOLLOW_PARAM_LIST_in_param_list2321);
/*  3873 */         stream_PARAM_LIST.add(PARAM_LIST88);
/*       */ 
/*  3882 */         retval.tree = root_0;
/*  3883 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3885 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3888 */         this.adaptor.addChild(root_0, parseParamList((CommonToken)PARAM_LIST88));
/*       */ 
/*  3892 */         retval.tree = root_0;
/*       */ 
/*  3895 */         break;
/*       */       case 2:
/*  3899 */         PARAM_LIST_OR_ID89 = (Token)match(this.input, 197, FOLLOW_PARAM_LIST_OR_ID_in_param_list2331);
/*  3900 */         stream_PARAM_LIST_OR_ID.add(PARAM_LIST_OR_ID89);
/*       */ 
/*  3909 */         retval.tree = root_0;
/*  3910 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  3912 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  3915 */         this.adaptor.addChild(root_0, parseParamList((CommonToken)PARAM_LIST_OR_ID89));
/*       */ 
/*  3919 */         retval.tree = root_0;
/*       */ 
/*  3922 */         break;
/*       */       case 3:
/*  3926 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */         while (true)
/*       */         {
/*  3932 */           int alt11 = 2;
/*  3933 */           int LA11_0 = this.input.LA(1);
/*  3934 */           if ((LA11_0 == 8) || (LA11_0 == 26) || (LA11_0 == 199)) {
/*  3935 */             alt11 = 1;
/*       */           }
/*       */ 
/*  3938 */           switch (alt11)
/*       */           {
/*       */           case 1:
/*  3942 */             pushFollow(FOLLOW_nonvoid_type_descriptor_in_param_list2341);
/*  3943 */             nonvoid_type_descriptor90 = nonvoid_type_descriptor();
/*  3944 */             this.state._fsp -= 1;
/*       */ 
/*  3946 */             this.adaptor.addChild(root_0, nonvoid_type_descriptor90.getTree());
/*       */ 
/*  3949 */             break;
/*       */           default:
/*  3952 */             break label526;
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/*  3960 */       label526: retval.stop = this.input.LT(-1);
/*       */ 
/*  3962 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  3963 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  3967 */       reportError(re);
/*  3968 */       recover(this.input, re);
/*  3969 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  3974 */     return retval;
/*       */   }
/*       */ 
/*       */   public final type_descriptor_return type_descriptor()
/*       */     throws RecognitionException
/*       */   {
/*  3989 */     type_descriptor_return retval = new type_descriptor_return();
/*  3990 */     retval.start = this.input.LT(1);
/*       */ 
/*  3992 */     CommonTree root_0 = null;
/*       */ 
/*  3994 */     Token set91 = null;
/*       */ 
/*  3996 */     CommonTree set91_tree = null;
/*       */     try
/*       */     {
/*  4002 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4005 */       set91 = this.input.LT(1);
/*  4006 */       if ((this.input.LA(1) == 8) || (this.input.LA(1) == 26) || (this.input.LA(1) == 199) || (this.input.LA(1) == 212)) {
/*  4007 */         this.input.consume();
/*  4008 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set91));
/*  4009 */         this.state.errorRecovery = false;
/*       */       }
/*       */       else {
/*  4012 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*       */         throw mse;
/*       */       }
/*       */ 
/*  4017 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4019 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4020 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4024 */       reportError(re);
/*  4025 */       recover(this.input, re);
/*  4026 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4031 */     return retval;
/*       */   }
/*       */ 
/*       */   public final nonvoid_type_descriptor_return nonvoid_type_descriptor()
/*       */     throws RecognitionException
/*       */   {
/*  4046 */     nonvoid_type_descriptor_return retval = new nonvoid_type_descriptor_return();
/*  4047 */     retval.start = this.input.LT(1);
/*       */ 
/*  4049 */     CommonTree root_0 = null;
/*       */ 
/*  4051 */     Token set92 = null;
/*       */ 
/*  4053 */     CommonTree set92_tree = null;
/*       */     try
/*       */     {
/*  4059 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4062 */       set92 = this.input.LT(1);
/*  4063 */       if ((this.input.LA(1) == 8) || (this.input.LA(1) == 26) || (this.input.LA(1) == 199)) {
/*  4064 */         this.input.consume();
/*  4065 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set92));
/*  4066 */         this.state.errorRecovery = false;
/*       */       }
/*       */       else {
/*  4069 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*       */         throw mse;
/*       */       }
/*       */ 
/*  4074 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4076 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4077 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4081 */       reportError(re);
/*  4082 */       recover(this.input, re);
/*  4083 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4088 */     return retval;
/*       */   }
/*       */ 
/*       */   public final reference_type_descriptor_return reference_type_descriptor()
/*       */     throws RecognitionException
/*       */   {
/*  4103 */     reference_type_descriptor_return retval = new reference_type_descriptor_return();
/*  4104 */     retval.start = this.input.LT(1);
/*       */ 
/*  4106 */     CommonTree root_0 = null;
/*       */ 
/*  4108 */     Token set93 = null;
/*       */ 
/*  4110 */     CommonTree set93_tree = null;
/*       */     try
/*       */     {
/*  4116 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4119 */       set93 = this.input.LT(1);
/*  4120 */       if ((this.input.LA(1) == 8) || (this.input.LA(1) == 26)) {
/*  4121 */         this.input.consume();
/*  4122 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set93));
/*  4123 */         this.state.errorRecovery = false;
/*       */       }
/*       */       else {
/*  4126 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*       */         throw mse;
/*       */       }
/*       */ 
/*  4131 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4133 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4134 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4138 */       reportError(re);
/*  4139 */       recover(this.input, re);
/*  4140 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4145 */     return retval;
/*       */   }
/*       */ 
/*       */   public final integer_literal_return integer_literal()
/*       */     throws RecognitionException
/*       */   {
/*  4160 */     integer_literal_return retval = new integer_literal_return();
/*  4161 */     retval.start = this.input.LT(1);
/*       */ 
/*  4163 */     CommonTree root_0 = null;
/*       */ 
/*  4165 */     Token POSITIVE_INTEGER_LITERAL94 = null;
/*  4166 */     Token NEGATIVE_INTEGER_LITERAL95 = null;
/*       */ 
/*  4168 */     CommonTree POSITIVE_INTEGER_LITERAL94_tree = null;
/*  4169 */     CommonTree NEGATIVE_INTEGER_LITERAL95_tree = null;
/*  4170 */     RewriteRuleTokenStream stream_NEGATIVE_INTEGER_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token NEGATIVE_INTEGER_LITERAL");
/*  4171 */     RewriteRuleTokenStream stream_POSITIVE_INTEGER_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token POSITIVE_INTEGER_LITERAL");
/*       */     try
/*       */     {
/*  4175 */       int alt13 = 2;
/*  4176 */       int LA13_0 = this.input.LA(1);
/*  4177 */       if (LA13_0 == 198) {
/*  4178 */         alt13 = 1;
/*       */       }
/*  4180 */       else if (LA13_0 == 190) {
/*  4181 */         alt13 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  4185 */         NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  4190 */       switch (alt13)
/*       */       {
/*       */       case 1:
/*  4194 */         POSITIVE_INTEGER_LITERAL94 = (Token)match(this.input, 198, FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2418);
/*  4195 */         stream_POSITIVE_INTEGER_LITERAL.add(POSITIVE_INTEGER_LITERAL94);
/*       */ 
/*  4204 */         retval.tree = root_0;
/*  4205 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  4207 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4210 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(102, POSITIVE_INTEGER_LITERAL94));
/*       */ 
/*  4214 */         retval.tree = root_0;
/*       */ 
/*  4217 */         break;
/*       */       case 2:
/*  4221 */         NEGATIVE_INTEGER_LITERAL95 = (Token)match(this.input, 190, FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2429);
/*  4222 */         stream_NEGATIVE_INTEGER_LITERAL.add(NEGATIVE_INTEGER_LITERAL95);
/*       */ 
/*  4231 */         retval.tree = root_0;
/*  4232 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  4234 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4237 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(102, NEGATIVE_INTEGER_LITERAL95));
/*       */ 
/*  4241 */         retval.tree = root_0;
/*       */       }
/*       */ 
/*  4247 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4249 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4250 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4254 */       reportError(re);
/*  4255 */       recover(this.input, re);
/*  4256 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4261 */     return retval;
/*       */   }
/*       */ 
/*       */   public final float_literal_return float_literal()
/*       */     throws RecognitionException
/*       */   {
/*  4276 */     float_literal_return retval = new float_literal_return();
/*  4277 */     retval.start = this.input.LT(1);
/*       */ 
/*  4279 */     CommonTree root_0 = null;
/*       */ 
/*  4281 */     Token FLOAT_LITERAL_OR_ID96 = null;
/*  4282 */     Token FLOAT_LITERAL97 = null;
/*       */ 
/*  4284 */     CommonTree FLOAT_LITERAL_OR_ID96_tree = null;
/*  4285 */     CommonTree FLOAT_LITERAL97_tree = null;
/*  4286 */     RewriteRuleTokenStream stream_FLOAT_LITERAL_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token FLOAT_LITERAL_OR_ID");
/*       */     try
/*       */     {
/*  4290 */       int alt14 = 2;
/*  4291 */       int LA14_0 = this.input.LA(1);
/*  4292 */       if (LA14_0 == 52) {
/*  4293 */         alt14 = 1;
/*       */       }
/*  4295 */       else if (LA14_0 == 51) {
/*  4296 */         alt14 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  4300 */         NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  4305 */       switch (alt14)
/*       */       {
/*       */       case 1:
/*  4309 */         FLOAT_LITERAL_OR_ID96 = (Token)match(this.input, 52, FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2444);
/*  4310 */         stream_FLOAT_LITERAL_OR_ID.add(FLOAT_LITERAL_OR_ID96);
/*       */ 
/*  4319 */         retval.tree = root_0;
/*  4320 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  4322 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4325 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(51, FLOAT_LITERAL_OR_ID96));
/*       */ 
/*  4329 */         retval.tree = root_0;
/*       */ 
/*  4332 */         break;
/*       */       case 2:
/*  4336 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4339 */         FLOAT_LITERAL97 = (Token)match(this.input, 51, FOLLOW_FLOAT_LITERAL_in_float_literal2455);
/*  4340 */         FLOAT_LITERAL97_tree = (CommonTree)this.adaptor.create(FLOAT_LITERAL97);
/*  4341 */         this.adaptor.addChild(root_0, FLOAT_LITERAL97_tree);
/*       */       }
/*       */ 
/*  4347 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4349 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4350 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4354 */       reportError(re);
/*  4355 */       recover(this.input, re);
/*  4356 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4361 */     return retval;
/*       */   }
/*       */ 
/*       */   public final double_literal_return double_literal()
/*       */     throws RecognitionException
/*       */   {
/*  4376 */     double_literal_return retval = new double_literal_return();
/*  4377 */     retval.start = this.input.LT(1);
/*       */ 
/*  4379 */     CommonTree root_0 = null;
/*       */ 
/*  4381 */     Token DOUBLE_LITERAL_OR_ID98 = null;
/*  4382 */     Token DOUBLE_LITERAL99 = null;
/*       */ 
/*  4384 */     CommonTree DOUBLE_LITERAL_OR_ID98_tree = null;
/*  4385 */     CommonTree DOUBLE_LITERAL99_tree = null;
/*  4386 */     RewriteRuleTokenStream stream_DOUBLE_LITERAL_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_LITERAL_OR_ID");
/*       */     try
/*       */     {
/*  4390 */       int alt15 = 2;
/*  4391 */       int LA15_0 = this.input.LA(1);
/*  4392 */       if (LA15_0 == 35) {
/*  4393 */         alt15 = 1;
/*       */       }
/*  4395 */       else if (LA15_0 == 34) {
/*  4396 */         alt15 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  4400 */         NoViableAltException nvae = new NoViableAltException("", 15, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  4405 */       switch (alt15)
/*       */       {
/*       */       case 1:
/*  4409 */         DOUBLE_LITERAL_OR_ID98 = (Token)match(this.input, 35, FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2465);
/*  4410 */         stream_DOUBLE_LITERAL_OR_ID.add(DOUBLE_LITERAL_OR_ID98);
/*       */ 
/*  4419 */         retval.tree = root_0;
/*  4420 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  4422 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4425 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(34, DOUBLE_LITERAL_OR_ID98));
/*       */ 
/*  4429 */         retval.tree = root_0;
/*       */ 
/*  4432 */         break;
/*       */       case 2:
/*  4436 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4439 */         DOUBLE_LITERAL99 = (Token)match(this.input, 34, FOLLOW_DOUBLE_LITERAL_in_double_literal2476);
/*  4440 */         DOUBLE_LITERAL99_tree = (CommonTree)this.adaptor.create(DOUBLE_LITERAL99);
/*  4441 */         this.adaptor.addChild(root_0, DOUBLE_LITERAL99_tree);
/*       */       }
/*       */ 
/*  4447 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4449 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4450 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4454 */       reportError(re);
/*  4455 */       recover(this.input, re);
/*  4456 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4461 */     return retval;
/*       */   }
/*       */ 
/*       */   public final literal_return literal()
/*       */     throws RecognitionException
/*       */   {
/*  4476 */     literal_return retval = new literal_return();
/*  4477 */     retval.start = this.input.LT(1);
/*       */ 
/*  4479 */     CommonTree root_0 = null;
/*       */ 
/*  4481 */     Token LONG_LITERAL100 = null;
/*  4482 */     Token SHORT_LITERAL102 = null;
/*  4483 */     Token BYTE_LITERAL103 = null;
/*  4484 */     Token CHAR_LITERAL106 = null;
/*  4485 */     Token STRING_LITERAL107 = null;
/*  4486 */     Token BOOL_LITERAL108 = null;
/*  4487 */     Token NULL_LITERAL109 = null;
/*  4488 */     ParserRuleReturnScope integer_literal101 = null;
/*  4489 */     ParserRuleReturnScope float_literal104 = null;
/*  4490 */     ParserRuleReturnScope double_literal105 = null;
/*  4491 */     ParserRuleReturnScope array_literal110 = null;
/*  4492 */     ParserRuleReturnScope subannotation111 = null;
/*  4493 */     ParserRuleReturnScope type_field_method_literal112 = null;
/*  4494 */     ParserRuleReturnScope enum_literal113 = null;
/*       */ 
/*  4496 */     CommonTree LONG_LITERAL100_tree = null;
/*  4497 */     CommonTree SHORT_LITERAL102_tree = null;
/*  4498 */     CommonTree BYTE_LITERAL103_tree = null;
/*  4499 */     CommonTree CHAR_LITERAL106_tree = null;
/*  4500 */     CommonTree STRING_LITERAL107_tree = null;
/*  4501 */     CommonTree BOOL_LITERAL108_tree = null;
/*  4502 */     CommonTree NULL_LITERAL109_tree = null;
/*       */     try
/*       */     {
/*  4506 */       int alt16 = 14;
/*  4507 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 187:
/*  4510 */         alt16 = 1;
/*       */ 
/*  4512 */         break;
/*       */       case 190:
/*       */       case 198:
/*  4516 */         alt16 = 2;
/*       */ 
/*  4518 */         break;
/*       */       case 204:
/*  4521 */         alt16 = 3;
/*       */ 
/*  4523 */         break;
/*       */       case 22:
/*  4526 */         alt16 = 4;
/*       */ 
/*  4528 */         break;
/*       */       case 51:
/*       */       case 52:
/*  4532 */         alt16 = 5;
/*       */ 
/*  4534 */         break;
/*       */       case 34:
/*       */       case 35:
/*  4538 */         alt16 = 6;
/*       */ 
/*  4540 */         break;
/*       */       case 25:
/*  4543 */         alt16 = 7;
/*       */ 
/*  4545 */         break;
/*       */       case 208:
/*  4548 */         alt16 = 8;
/*       */ 
/*  4550 */         break;
/*       */       case 21:
/*  4553 */         alt16 = 9;
/*       */ 
/*  4555 */         break;
/*       */       case 191:
/*  4558 */         alt16 = 10;
/*       */ 
/*  4560 */         break;
/*       */       case 192:
/*  4563 */         alt16 = 11;
/*       */ 
/*  4565 */         break;
/*       */       case 209:
/*  4568 */         alt16 = 12;
/*       */ 
/*  4570 */         break;
/*       */       case 8:
/*       */       case 26:
/*       */       case 199:
/*       */       case 212:
/*  4576 */         alt16 = 13;
/*       */ 
/*  4578 */         break;
/*       */       case 45:
/*  4581 */         alt16 = 14;
/*       */ 
/*  4583 */         break;
/*       */       default:
/*  4585 */         NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  4589 */       switch (alt16)
/*       */       {
/*       */       case 1:
/*  4593 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4596 */         LONG_LITERAL100 = (Token)match(this.input, 187, FOLLOW_LONG_LITERAL_in_literal2486);
/*  4597 */         LONG_LITERAL100_tree = (CommonTree)this.adaptor.create(LONG_LITERAL100);
/*  4598 */         this.adaptor.addChild(root_0, LONG_LITERAL100_tree);
/*       */ 
/*  4601 */         break;
/*       */       case 2:
/*  4605 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4608 */         pushFollow(FOLLOW_integer_literal_in_literal2492);
/*  4609 */         integer_literal101 = integer_literal();
/*  4610 */         this.state._fsp -= 1;
/*       */ 
/*  4612 */         this.adaptor.addChild(root_0, integer_literal101.getTree());
/*       */ 
/*  4615 */         break;
/*       */       case 3:
/*  4619 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4622 */         SHORT_LITERAL102 = (Token)match(this.input, 204, FOLLOW_SHORT_LITERAL_in_literal2498);
/*  4623 */         SHORT_LITERAL102_tree = (CommonTree)this.adaptor.create(SHORT_LITERAL102);
/*  4624 */         this.adaptor.addChild(root_0, SHORT_LITERAL102_tree);
/*       */ 
/*  4627 */         break;
/*       */       case 4:
/*  4631 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4634 */         BYTE_LITERAL103 = (Token)match(this.input, 22, FOLLOW_BYTE_LITERAL_in_literal2504);
/*  4635 */         BYTE_LITERAL103_tree = (CommonTree)this.adaptor.create(BYTE_LITERAL103);
/*  4636 */         this.adaptor.addChild(root_0, BYTE_LITERAL103_tree);
/*       */ 
/*  4639 */         break;
/*       */       case 5:
/*  4643 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4646 */         pushFollow(FOLLOW_float_literal_in_literal2510);
/*  4647 */         float_literal104 = float_literal();
/*  4648 */         this.state._fsp -= 1;
/*       */ 
/*  4650 */         this.adaptor.addChild(root_0, float_literal104.getTree());
/*       */ 
/*  4653 */         break;
/*       */       case 6:
/*  4657 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4660 */         pushFollow(FOLLOW_double_literal_in_literal2516);
/*  4661 */         double_literal105 = double_literal();
/*  4662 */         this.state._fsp -= 1;
/*       */ 
/*  4664 */         this.adaptor.addChild(root_0, double_literal105.getTree());
/*       */ 
/*  4667 */         break;
/*       */       case 7:
/*  4671 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4674 */         CHAR_LITERAL106 = (Token)match(this.input, 25, FOLLOW_CHAR_LITERAL_in_literal2522);
/*  4675 */         CHAR_LITERAL106_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL106);
/*  4676 */         this.adaptor.addChild(root_0, CHAR_LITERAL106_tree);
/*       */ 
/*  4679 */         break;
/*       */       case 8:
/*  4683 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4686 */         STRING_LITERAL107 = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_literal2528);
/*  4687 */         STRING_LITERAL107_tree = (CommonTree)this.adaptor.create(STRING_LITERAL107);
/*  4688 */         this.adaptor.addChild(root_0, STRING_LITERAL107_tree);
/*       */ 
/*  4691 */         break;
/*       */       case 9:
/*  4695 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4698 */         BOOL_LITERAL108 = (Token)match(this.input, 21, FOLLOW_BOOL_LITERAL_in_literal2534);
/*  4699 */         BOOL_LITERAL108_tree = (CommonTree)this.adaptor.create(BOOL_LITERAL108);
/*  4700 */         this.adaptor.addChild(root_0, BOOL_LITERAL108_tree);
/*       */ 
/*  4703 */         break;
/*       */       case 10:
/*  4707 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4710 */         NULL_LITERAL109 = (Token)match(this.input, 191, FOLLOW_NULL_LITERAL_in_literal2540);
/*  4711 */         NULL_LITERAL109_tree = (CommonTree)this.adaptor.create(NULL_LITERAL109);
/*  4712 */         this.adaptor.addChild(root_0, NULL_LITERAL109_tree);
/*       */ 
/*  4715 */         break;
/*       */       case 11:
/*  4719 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4722 */         pushFollow(FOLLOW_array_literal_in_literal2546);
/*  4723 */         array_literal110 = array_literal();
/*  4724 */         this.state._fsp -= 1;
/*       */ 
/*  4726 */         this.adaptor.addChild(root_0, array_literal110.getTree());
/*       */ 
/*  4729 */         break;
/*       */       case 12:
/*  4733 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4736 */         pushFollow(FOLLOW_subannotation_in_literal2552);
/*  4737 */         subannotation111 = subannotation();
/*  4738 */         this.state._fsp -= 1;
/*       */ 
/*  4740 */         this.adaptor.addChild(root_0, subannotation111.getTree());
/*       */ 
/*  4743 */         break;
/*       */       case 13:
/*  4747 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4750 */         pushFollow(FOLLOW_type_field_method_literal_in_literal2558);
/*  4751 */         type_field_method_literal112 = type_field_method_literal();
/*  4752 */         this.state._fsp -= 1;
/*       */ 
/*  4754 */         this.adaptor.addChild(root_0, type_field_method_literal112.getTree());
/*       */ 
/*  4757 */         break;
/*       */       case 14:
/*  4761 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4764 */         pushFollow(FOLLOW_enum_literal_in_literal2564);
/*  4765 */         enum_literal113 = enum_literal();
/*  4766 */         this.state._fsp -= 1;
/*       */ 
/*  4768 */         this.adaptor.addChild(root_0, enum_literal113.getTree());
/*       */       }
/*       */ 
/*  4774 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4776 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4777 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4781 */       reportError(re);
/*  4782 */       recover(this.input, re);
/*  4783 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4788 */     return retval;
/*       */   }
/*       */ 
/*       */   public final parsed_integer_literal_return parsed_integer_literal()
/*       */     throws RecognitionException
/*       */   {
/*  4804 */     parsed_integer_literal_return retval = new parsed_integer_literal_return();
/*  4805 */     retval.start = this.input.LT(1);
/*       */ 
/*  4807 */     CommonTree root_0 = null;
/*       */ 
/*  4809 */     ParserRuleReturnScope integer_literal114 = null;
/*       */     try
/*       */     {
/*  4816 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4819 */       pushFollow(FOLLOW_integer_literal_in_parsed_integer_literal2577);
/*  4820 */       integer_literal114 = integer_literal();
/*  4821 */       this.state._fsp -= 1;
/*       */ 
/*  4823 */       this.adaptor.addChild(root_0, integer_literal114.getTree());
/*       */ 
/*  4825 */       retval.value = LiteralTools.parseInt(integer_literal114 != null ? this.input.toString(integer_literal114.start, integer_literal114.stop) : null);
/*       */ 
/*  4828 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4830 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4831 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4835 */       reportError(re);
/*  4836 */       recover(this.input, re);
/*  4837 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4842 */     return retval;
/*       */   }
/*       */ 
/*       */   public final integral_literal_return integral_literal()
/*       */     throws RecognitionException
/*       */   {
/*  4857 */     integral_literal_return retval = new integral_literal_return();
/*  4858 */     retval.start = this.input.LT(1);
/*       */ 
/*  4860 */     CommonTree root_0 = null;
/*       */ 
/*  4862 */     Token LONG_LITERAL115 = null;
/*  4863 */     Token SHORT_LITERAL117 = null;
/*  4864 */     Token CHAR_LITERAL118 = null;
/*  4865 */     Token BYTE_LITERAL119 = null;
/*  4866 */     ParserRuleReturnScope integer_literal116 = null;
/*       */ 
/*  4868 */     CommonTree LONG_LITERAL115_tree = null;
/*  4869 */     CommonTree SHORT_LITERAL117_tree = null;
/*  4870 */     CommonTree CHAR_LITERAL118_tree = null;
/*  4871 */     CommonTree BYTE_LITERAL119_tree = null;
/*       */     try
/*       */     {
/*  4875 */       int alt17 = 5;
/*  4876 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 187:
/*  4879 */         alt17 = 1;
/*       */ 
/*  4881 */         break;
/*       */       case 190:
/*       */       case 198:
/*  4885 */         alt17 = 2;
/*       */ 
/*  4887 */         break;
/*       */       case 204:
/*  4890 */         alt17 = 3;
/*       */ 
/*  4892 */         break;
/*       */       case 25:
/*  4895 */         alt17 = 4;
/*       */ 
/*  4897 */         break;
/*       */       case 22:
/*  4900 */         alt17 = 5;
/*       */ 
/*  4902 */         break;
/*       */       default:
/*  4904 */         NoViableAltException nvae = new NoViableAltException("", 17, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  4908 */       switch (alt17)
/*       */       {
/*       */       case 1:
/*  4912 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4915 */         LONG_LITERAL115 = (Token)match(this.input, 187, FOLLOW_LONG_LITERAL_in_integral_literal2589);
/*  4916 */         LONG_LITERAL115_tree = (CommonTree)this.adaptor.create(LONG_LITERAL115);
/*  4917 */         this.adaptor.addChild(root_0, LONG_LITERAL115_tree);
/*       */ 
/*  4920 */         break;
/*       */       case 2:
/*  4924 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4927 */         pushFollow(FOLLOW_integer_literal_in_integral_literal2595);
/*  4928 */         integer_literal116 = integer_literal();
/*  4929 */         this.state._fsp -= 1;
/*       */ 
/*  4931 */         this.adaptor.addChild(root_0, integer_literal116.getTree());
/*       */ 
/*  4934 */         break;
/*       */       case 3:
/*  4938 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4941 */         SHORT_LITERAL117 = (Token)match(this.input, 204, FOLLOW_SHORT_LITERAL_in_integral_literal2601);
/*  4942 */         SHORT_LITERAL117_tree = (CommonTree)this.adaptor.create(SHORT_LITERAL117);
/*  4943 */         this.adaptor.addChild(root_0, SHORT_LITERAL117_tree);
/*       */ 
/*  4946 */         break;
/*       */       case 4:
/*  4950 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4953 */         CHAR_LITERAL118 = (Token)match(this.input, 25, FOLLOW_CHAR_LITERAL_in_integral_literal2607);
/*  4954 */         CHAR_LITERAL118_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL118);
/*  4955 */         this.adaptor.addChild(root_0, CHAR_LITERAL118_tree);
/*       */ 
/*  4958 */         break;
/*       */       case 5:
/*  4962 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  4965 */         BYTE_LITERAL119 = (Token)match(this.input, 22, FOLLOW_BYTE_LITERAL_in_integral_literal2613);
/*  4966 */         BYTE_LITERAL119_tree = (CommonTree)this.adaptor.create(BYTE_LITERAL119);
/*  4967 */         this.adaptor.addChild(root_0, BYTE_LITERAL119_tree);
/*       */       }
/*       */ 
/*  4973 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  4975 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  4976 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  4980 */       reportError(re);
/*  4981 */       recover(this.input, re);
/*  4982 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  4987 */     return retval;
/*       */   }
/*       */ 
/*       */   public final fixed_32bit_literal_return fixed_32bit_literal()
/*       */     throws RecognitionException
/*       */   {
/*  5002 */     fixed_32bit_literal_return retval = new fixed_32bit_literal_return();
/*  5003 */     retval.start = this.input.LT(1);
/*       */ 
/*  5005 */     CommonTree root_0 = null;
/*       */ 
/*  5007 */     Token LONG_LITERAL120 = null;
/*  5008 */     Token SHORT_LITERAL122 = null;
/*  5009 */     Token BYTE_LITERAL123 = null;
/*  5010 */     Token CHAR_LITERAL125 = null;
/*  5011 */     Token BOOL_LITERAL126 = null;
/*  5012 */     ParserRuleReturnScope integer_literal121 = null;
/*  5013 */     ParserRuleReturnScope float_literal124 = null;
/*       */ 
/*  5015 */     CommonTree LONG_LITERAL120_tree = null;
/*  5016 */     CommonTree SHORT_LITERAL122_tree = null;
/*  5017 */     CommonTree BYTE_LITERAL123_tree = null;
/*  5018 */     CommonTree CHAR_LITERAL125_tree = null;
/*  5019 */     CommonTree BOOL_LITERAL126_tree = null;
/*       */     try
/*       */     {
/*  5023 */       int alt18 = 7;
/*  5024 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 187:
/*  5027 */         alt18 = 1;
/*       */ 
/*  5029 */         break;
/*       */       case 190:
/*       */       case 198:
/*  5033 */         alt18 = 2;
/*       */ 
/*  5035 */         break;
/*       */       case 204:
/*  5038 */         alt18 = 3;
/*       */ 
/*  5040 */         break;
/*       */       case 22:
/*  5043 */         alt18 = 4;
/*       */ 
/*  5045 */         break;
/*       */       case 51:
/*       */       case 52:
/*  5049 */         alt18 = 5;
/*       */ 
/*  5051 */         break;
/*       */       case 25:
/*  5054 */         alt18 = 6;
/*       */ 
/*  5056 */         break;
/*       */       case 21:
/*  5059 */         alt18 = 7;
/*       */ 
/*  5061 */         break;
/*       */       default:
/*  5063 */         NoViableAltException nvae = new NoViableAltException("", 18, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  5067 */       switch (alt18)
/*       */       {
/*       */       case 1:
/*  5071 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5074 */         LONG_LITERAL120 = (Token)match(this.input, 187, FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2623);
/*  5075 */         LONG_LITERAL120_tree = (CommonTree)this.adaptor.create(LONG_LITERAL120);
/*  5076 */         this.adaptor.addChild(root_0, LONG_LITERAL120_tree);
/*       */ 
/*  5079 */         break;
/*       */       case 2:
/*  5083 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5086 */         pushFollow(FOLLOW_integer_literal_in_fixed_32bit_literal2629);
/*  5087 */         integer_literal121 = integer_literal();
/*  5088 */         this.state._fsp -= 1;
/*       */ 
/*  5090 */         this.adaptor.addChild(root_0, integer_literal121.getTree());
/*       */ 
/*  5093 */         break;
/*       */       case 3:
/*  5097 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5100 */         SHORT_LITERAL122 = (Token)match(this.input, 204, FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2635);
/*  5101 */         SHORT_LITERAL122_tree = (CommonTree)this.adaptor.create(SHORT_LITERAL122);
/*  5102 */         this.adaptor.addChild(root_0, SHORT_LITERAL122_tree);
/*       */ 
/*  5105 */         break;
/*       */       case 4:
/*  5109 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5112 */         BYTE_LITERAL123 = (Token)match(this.input, 22, FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2641);
/*  5113 */         BYTE_LITERAL123_tree = (CommonTree)this.adaptor.create(BYTE_LITERAL123);
/*  5114 */         this.adaptor.addChild(root_0, BYTE_LITERAL123_tree);
/*       */ 
/*  5117 */         break;
/*       */       case 5:
/*  5121 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5124 */         pushFollow(FOLLOW_float_literal_in_fixed_32bit_literal2647);
/*  5125 */         float_literal124 = float_literal();
/*  5126 */         this.state._fsp -= 1;
/*       */ 
/*  5128 */         this.adaptor.addChild(root_0, float_literal124.getTree());
/*       */ 
/*  5131 */         break;
/*       */       case 6:
/*  5135 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5138 */         CHAR_LITERAL125 = (Token)match(this.input, 25, FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2653);
/*  5139 */         CHAR_LITERAL125_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL125);
/*  5140 */         this.adaptor.addChild(root_0, CHAR_LITERAL125_tree);
/*       */ 
/*  5143 */         break;
/*       */       case 7:
/*  5147 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5150 */         BOOL_LITERAL126 = (Token)match(this.input, 21, FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2659);
/*  5151 */         BOOL_LITERAL126_tree = (CommonTree)this.adaptor.create(BOOL_LITERAL126);
/*  5152 */         this.adaptor.addChild(root_0, BOOL_LITERAL126_tree);
/*       */       }
/*       */ 
/*  5158 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  5160 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  5161 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  5165 */       reportError(re);
/*  5166 */       recover(this.input, re);
/*  5167 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  5172 */     return retval;
/*       */   }
/*       */ 
/*       */   public final fixed_literal_return fixed_literal()
/*       */     throws RecognitionException
/*       */   {
/*  5187 */     fixed_literal_return retval = new fixed_literal_return();
/*  5188 */     retval.start = this.input.LT(1);
/*       */ 
/*  5190 */     CommonTree root_0 = null;
/*       */ 
/*  5192 */     Token LONG_LITERAL128 = null;
/*  5193 */     Token SHORT_LITERAL129 = null;
/*  5194 */     Token BYTE_LITERAL130 = null;
/*  5195 */     Token CHAR_LITERAL133 = null;
/*  5196 */     Token BOOL_LITERAL134 = null;
/*  5197 */     ParserRuleReturnScope integer_literal127 = null;
/*  5198 */     ParserRuleReturnScope float_literal131 = null;
/*  5199 */     ParserRuleReturnScope double_literal132 = null;
/*       */ 
/*  5201 */     CommonTree LONG_LITERAL128_tree = null;
/*  5202 */     CommonTree SHORT_LITERAL129_tree = null;
/*  5203 */     CommonTree BYTE_LITERAL130_tree = null;
/*  5204 */     CommonTree CHAR_LITERAL133_tree = null;
/*  5205 */     CommonTree BOOL_LITERAL134_tree = null;
/*       */     try
/*       */     {
/*  5209 */       int alt19 = 8;
/*  5210 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 190:
/*       */       case 198:
/*  5214 */         alt19 = 1;
/*       */ 
/*  5216 */         break;
/*       */       case 187:
/*  5219 */         alt19 = 2;
/*       */ 
/*  5221 */         break;
/*       */       case 204:
/*  5224 */         alt19 = 3;
/*       */ 
/*  5226 */         break;
/*       */       case 22:
/*  5229 */         alt19 = 4;
/*       */ 
/*  5231 */         break;
/*       */       case 51:
/*       */       case 52:
/*  5235 */         alt19 = 5;
/*       */ 
/*  5237 */         break;
/*       */       case 34:
/*       */       case 35:
/*  5241 */         alt19 = 6;
/*       */ 
/*  5243 */         break;
/*       */       case 25:
/*  5246 */         alt19 = 7;
/*       */ 
/*  5248 */         break;
/*       */       case 21:
/*  5251 */         alt19 = 8;
/*       */ 
/*  5253 */         break;
/*       */       default:
/*  5255 */         NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  5259 */       switch (alt19)
/*       */       {
/*       */       case 1:
/*  5263 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5266 */         pushFollow(FOLLOW_integer_literal_in_fixed_literal2669);
/*  5267 */         integer_literal127 = integer_literal();
/*  5268 */         this.state._fsp -= 1;
/*       */ 
/*  5270 */         this.adaptor.addChild(root_0, integer_literal127.getTree());
/*       */ 
/*  5273 */         break;
/*       */       case 2:
/*  5277 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5280 */         LONG_LITERAL128 = (Token)match(this.input, 187, FOLLOW_LONG_LITERAL_in_fixed_literal2675);
/*  5281 */         LONG_LITERAL128_tree = (CommonTree)this.adaptor.create(LONG_LITERAL128);
/*  5282 */         this.adaptor.addChild(root_0, LONG_LITERAL128_tree);
/*       */ 
/*  5285 */         break;
/*       */       case 3:
/*  5289 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5292 */         SHORT_LITERAL129 = (Token)match(this.input, 204, FOLLOW_SHORT_LITERAL_in_fixed_literal2681);
/*  5293 */         SHORT_LITERAL129_tree = (CommonTree)this.adaptor.create(SHORT_LITERAL129);
/*  5294 */         this.adaptor.addChild(root_0, SHORT_LITERAL129_tree);
/*       */ 
/*  5297 */         break;
/*       */       case 4:
/*  5301 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5304 */         BYTE_LITERAL130 = (Token)match(this.input, 22, FOLLOW_BYTE_LITERAL_in_fixed_literal2687);
/*  5305 */         BYTE_LITERAL130_tree = (CommonTree)this.adaptor.create(BYTE_LITERAL130);
/*  5306 */         this.adaptor.addChild(root_0, BYTE_LITERAL130_tree);
/*       */ 
/*  5309 */         break;
/*       */       case 5:
/*  5313 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5316 */         pushFollow(FOLLOW_float_literal_in_fixed_literal2693);
/*  5317 */         float_literal131 = float_literal();
/*  5318 */         this.state._fsp -= 1;
/*       */ 
/*  5320 */         this.adaptor.addChild(root_0, float_literal131.getTree());
/*       */ 
/*  5323 */         break;
/*       */       case 6:
/*  5327 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5330 */         pushFollow(FOLLOW_double_literal_in_fixed_literal2699);
/*  5331 */         double_literal132 = double_literal();
/*  5332 */         this.state._fsp -= 1;
/*       */ 
/*  5334 */         this.adaptor.addChild(root_0, double_literal132.getTree());
/*       */ 
/*  5337 */         break;
/*       */       case 7:
/*  5341 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5344 */         CHAR_LITERAL133 = (Token)match(this.input, 25, FOLLOW_CHAR_LITERAL_in_fixed_literal2705);
/*  5345 */         CHAR_LITERAL133_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL133);
/*  5346 */         this.adaptor.addChild(root_0, CHAR_LITERAL133_tree);
/*       */ 
/*  5349 */         break;
/*       */       case 8:
/*  5353 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5356 */         BOOL_LITERAL134 = (Token)match(this.input, 21, FOLLOW_BOOL_LITERAL_in_fixed_literal2711);
/*  5357 */         BOOL_LITERAL134_tree = (CommonTree)this.adaptor.create(BOOL_LITERAL134);
/*  5358 */         this.adaptor.addChild(root_0, BOOL_LITERAL134_tree);
/*       */       }
/*       */ 
/*  5364 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  5366 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  5367 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  5371 */       reportError(re);
/*  5372 */       recover(this.input, re);
/*  5373 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  5378 */     return retval;
/*       */   }
/*       */ 
/*       */   public final array_literal_return array_literal()
/*       */     throws RecognitionException
/*       */   {
/*  5393 */     array_literal_return retval = new array_literal_return();
/*  5394 */     retval.start = this.input.LT(1);
/*       */ 
/*  5396 */     CommonTree root_0 = null;
/*       */ 
/*  5398 */     Token OPEN_BRACE135 = null;
/*  5399 */     Token COMMA137 = null;
/*  5400 */     Token CLOSE_BRACE139 = null;
/*  5401 */     ParserRuleReturnScope literal136 = null;
/*  5402 */     ParserRuleReturnScope literal138 = null;
/*       */ 
/*  5404 */     CommonTree OPEN_BRACE135_tree = null;
/*  5405 */     CommonTree COMMA137_tree = null;
/*  5406 */     CommonTree CLOSE_BRACE139_tree = null;
/*  5407 */     RewriteRuleTokenStream stream_CLOSE_BRACE = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
/*  5408 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/*  5409 */     RewriteRuleTokenStream stream_OPEN_BRACE = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
/*  5410 */     RewriteRuleSubtreeStream stream_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule literal");
/*       */     try
/*       */     {
/*  5416 */       OPEN_BRACE135 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_array_literal2721);
/*  5417 */       stream_OPEN_BRACE.add(OPEN_BRACE135);
/*       */ 
/*  5420 */       int alt21 = 2;
/*  5421 */       int LA21_0 = this.input.LA(1);
/*  5422 */       if ((LA21_0 == 8) || ((LA21_0 >= 21) && (LA21_0 <= 22)) || ((LA21_0 >= 25) && (LA21_0 <= 26)) || ((LA21_0 >= 34) && (LA21_0 <= 35)) || (LA21_0 == 45) || ((LA21_0 >= 51) && (LA21_0 <= 52)) || (LA21_0 == 187) || ((LA21_0 >= 190) && (LA21_0 <= 192)) || ((LA21_0 >= 198) && (LA21_0 <= 199)) || (LA21_0 == 204) || ((LA21_0 >= 208) && (LA21_0 <= 209)) || (LA21_0 == 212)) {
/*  5423 */         alt21 = 1;
/*       */       }
/*  5425 */       else if (LA21_0 == 28) {
/*  5426 */         alt21 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  5430 */         NoViableAltException nvae = new NoViableAltException("", 21, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  5435 */       switch (alt21)
/*       */       {
/*       */       case 1:
/*  5439 */         pushFollow(FOLLOW_literal_in_array_literal2724);
/*  5440 */         literal136 = literal();
/*  5441 */         this.state._fsp -= 1;
/*       */ 
/*  5443 */         stream_literal.add(literal136.getTree());
/*       */         while (true)
/*       */         {
/*  5447 */           int alt20 = 2;
/*  5448 */           int LA20_0 = this.input.LA(1);
/*  5449 */           if (LA20_0 == 31) {
/*  5450 */             alt20 = 1;
/*       */           }
/*       */ 
/*  5453 */           switch (alt20)
/*       */           {
/*       */           case 1:
/*  5457 */             COMMA137 = (Token)match(this.input, 31, FOLLOW_COMMA_in_array_literal2727);
/*  5458 */             stream_COMMA.add(COMMA137);
/*       */ 
/*  5460 */             pushFollow(FOLLOW_literal_in_array_literal2729);
/*  5461 */             literal138 = literal();
/*  5462 */             this.state._fsp -= 1;
/*       */ 
/*  5464 */             stream_literal.add(literal138.getTree());
/*       */ 
/*  5466 */             break;
/*       */           default:
/*  5469 */             break label510;
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*       */       case 2:
/*       */       }
/*       */ 
/*  5483 */       label510: CLOSE_BRACE139 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_array_literal2737);
/*  5484 */       stream_CLOSE_BRACE.add(CLOSE_BRACE139);
/*       */ 
/*  5493 */       retval.tree = root_0;
/*  5494 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  5496 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5501 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  5502 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(114, retval.start, "I_ENCODED_ARRAY"), root_1);
/*       */ 
/*  5504 */       while (stream_literal.hasNext()) {
/*  5505 */         this.adaptor.addChild(root_1, stream_literal.nextTree());
/*       */       }
/*  5507 */       stream_literal.reset();
/*       */ 
/*  5509 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  5515 */       retval.tree = root_0;
/*       */ 
/*  5519 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  5521 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  5522 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  5526 */       reportError(re);
/*  5527 */       recover(this.input, re);
/*  5528 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  5533 */     return retval;
/*       */   }
/*       */ 
/*       */   public final annotation_element_return annotation_element()
/*       */     throws RecognitionException
/*       */   {
/*  5548 */     annotation_element_return retval = new annotation_element_return();
/*  5549 */     retval.start = this.input.LT(1);
/*       */ 
/*  5551 */     CommonTree root_0 = null;
/*       */ 
/*  5553 */     Token EQUAL141 = null;
/*  5554 */     ParserRuleReturnScope simple_name140 = null;
/*  5555 */     ParserRuleReturnScope literal142 = null;
/*       */ 
/*  5557 */     CommonTree EQUAL141_tree = null;
/*  5558 */     RewriteRuleTokenStream stream_EQUAL = new RewriteRuleTokenStream(this.adaptor, "token EQUAL");
/*  5559 */     RewriteRuleSubtreeStream stream_simple_name = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
/*  5560 */     RewriteRuleSubtreeStream stream_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule literal");
/*       */     try
/*       */     {
/*  5566 */       pushFollow(FOLLOW_simple_name_in_annotation_element2761);
/*  5567 */       simple_name140 = simple_name();
/*  5568 */       this.state._fsp -= 1;
/*       */ 
/*  5570 */       stream_simple_name.add(simple_name140.getTree());
/*  5571 */       EQUAL141 = (Token)match(this.input, 47, FOLLOW_EQUAL_in_annotation_element2763);
/*  5572 */       stream_EQUAL.add(EQUAL141);
/*       */ 
/*  5574 */       pushFollow(FOLLOW_literal_in_annotation_element2765);
/*  5575 */       literal142 = literal();
/*  5576 */       this.state._fsp -= 1;
/*       */ 
/*  5578 */       stream_literal.add(literal142.getTree());
/*       */ 
/*  5586 */       retval.tree = root_0;
/*  5587 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  5589 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5594 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  5595 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(107, retval.start, "I_ANNOTATION_ELEMENT"), root_1);
/*  5596 */       this.adaptor.addChild(root_1, stream_simple_name.nextTree());
/*  5597 */       this.adaptor.addChild(root_1, stream_literal.nextTree());
/*  5598 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  5604 */       retval.tree = root_0;
/*       */ 
/*  5608 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  5610 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  5611 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  5615 */       reportError(re);
/*  5616 */       recover(this.input, re);
/*  5617 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  5622 */     return retval;
/*       */   }
/*       */ 
/*       */   public final annotation_return annotation()
/*       */     throws RecognitionException
/*       */   {
/*  5637 */     annotation_return retval = new annotation_return();
/*  5638 */     retval.start = this.input.LT(1);
/*       */ 
/*  5640 */     CommonTree root_0 = null;
/*       */ 
/*  5642 */     Token ANNOTATION_DIRECTIVE143 = null;
/*  5643 */     Token ANNOTATION_VISIBILITY144 = null;
/*  5644 */     Token CLASS_DESCRIPTOR145 = null;
/*  5645 */     Token END_ANNOTATION_DIRECTIVE147 = null;
/*  5646 */     ParserRuleReturnScope annotation_element146 = null;
/*       */ 
/*  5648 */     CommonTree ANNOTATION_DIRECTIVE143_tree = null;
/*  5649 */     CommonTree ANNOTATION_VISIBILITY144_tree = null;
/*  5650 */     CommonTree CLASS_DESCRIPTOR145_tree = null;
/*  5651 */     CommonTree END_ANNOTATION_DIRECTIVE147_tree = null;
/*  5652 */     RewriteRuleTokenStream stream_ANNOTATION_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_DIRECTIVE");
/*  5653 */     RewriteRuleTokenStream stream_END_ANNOTATION_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_ANNOTATION_DIRECTIVE");
/*  5654 */     RewriteRuleTokenStream stream_ANNOTATION_VISIBILITY = new RewriteRuleTokenStream(this.adaptor, "token ANNOTATION_VISIBILITY");
/*  5655 */     RewriteRuleTokenStream stream_CLASS_DESCRIPTOR = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
/*  5656 */     RewriteRuleSubtreeStream stream_annotation_element = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation_element");
/*       */     try
/*       */     {
/*  5662 */       ANNOTATION_DIRECTIVE143 = (Token)match(this.input, 5, FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2790);
/*  5663 */       stream_ANNOTATION_DIRECTIVE.add(ANNOTATION_DIRECTIVE143);
/*       */ 
/*  5665 */       ANNOTATION_VISIBILITY144 = (Token)match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_annotation2792);
/*  5666 */       stream_ANNOTATION_VISIBILITY.add(ANNOTATION_VISIBILITY144);
/*       */ 
/*  5668 */       CLASS_DESCRIPTOR145 = (Token)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_annotation2794);
/*  5669 */       stream_CLASS_DESCRIPTOR.add(CLASS_DESCRIPTOR145);
/*       */       while (true)
/*       */       {
/*  5674 */         int alt22 = 2;
/*  5675 */         int LA22_0 = this.input.LA(1);
/*  5676 */         if ((LA22_0 == 4) || (LA22_0 == 6) || (LA22_0 == 21) || (LA22_0 == 35) || (LA22_0 == 52) || ((LA22_0 >= 58) && (LA22_0 <= 60)) || (LA22_0 == 62) || (LA22_0 == 64) || ((LA22_0 >= 67) && (LA22_0 <= 70)) || (LA22_0 == 74) || ((LA22_0 >= 76) && (LA22_0 <= 79)) || ((LA22_0 >= 81) && (LA22_0 <= 82)) || (LA22_0 == 84) || ((LA22_0 >= 88) && (LA22_0 <= 89)) || ((LA22_0 >= 91) && (LA22_0 <= 95)) || (LA22_0 == 101) || ((LA22_0 >= 190) && (LA22_0 <= 191)) || ((LA22_0 >= 197) && (LA22_0 <= 199)) || (LA22_0 == 201) || (LA22_0 == 205) || ((LA22_0 >= 211) && (LA22_0 <= 212))) {
/*  5677 */           alt22 = 1;
/*       */         }
/*       */ 
/*  5680 */         switch (alt22)
/*       */         {
/*       */         case 1:
/*  5684 */           pushFollow(FOLLOW_annotation_element_in_annotation2800);
/*  5685 */           annotation_element146 = annotation_element();
/*  5686 */           this.state._fsp -= 1;
/*       */ 
/*  5688 */           stream_annotation_element.add(annotation_element146.getTree());
/*       */ 
/*  5690 */           break;
/*       */         default:
/*  5693 */           break label501;
/*       */         }
/*       */       }
/*       */ 
/*  5697 */       label501: END_ANNOTATION_DIRECTIVE147 = (Token)match(this.input, 36, FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2803);
/*  5698 */       stream_END_ANNOTATION_DIRECTIVE.add(END_ANNOTATION_DIRECTIVE147);
/*       */ 
/*  5707 */       retval.tree = root_0;
/*  5708 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  5710 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5715 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  5716 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(105, retval.start, "I_ANNOTATION"), root_1);
/*  5717 */       this.adaptor.addChild(root_1, stream_ANNOTATION_VISIBILITY.nextNode());
/*       */ 
/*  5720 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/*  5721 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(180, retval.start, "I_SUBANNOTATION"), root_2);
/*  5722 */       this.adaptor.addChild(root_2, stream_CLASS_DESCRIPTOR.nextNode());
/*       */ 
/*  5724 */       while (stream_annotation_element.hasNext()) {
/*  5725 */         this.adaptor.addChild(root_2, stream_annotation_element.nextTree());
/*       */       }
/*  5727 */       stream_annotation_element.reset();
/*       */ 
/*  5729 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/*  5732 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  5738 */       retval.tree = root_0;
/*       */ 
/*  5742 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  5744 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  5745 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  5749 */       reportError(re);
/*  5750 */       recover(this.input, re);
/*  5751 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  5756 */     return retval;
/*       */   }
/*       */ 
/*       */   public final subannotation_return subannotation()
/*       */     throws RecognitionException
/*       */   {
/*  5771 */     subannotation_return retval = new subannotation_return();
/*  5772 */     retval.start = this.input.LT(1);
/*       */ 
/*  5774 */     CommonTree root_0 = null;
/*       */ 
/*  5776 */     Token SUBANNOTATION_DIRECTIVE148 = null;
/*  5777 */     Token CLASS_DESCRIPTOR149 = null;
/*  5778 */     Token END_SUBANNOTATION_DIRECTIVE151 = null;
/*  5779 */     ParserRuleReturnScope annotation_element150 = null;
/*       */ 
/*  5781 */     CommonTree SUBANNOTATION_DIRECTIVE148_tree = null;
/*  5782 */     CommonTree CLASS_DESCRIPTOR149_tree = null;
/*  5783 */     CommonTree END_SUBANNOTATION_DIRECTIVE151_tree = null;
/*  5784 */     RewriteRuleTokenStream stream_SUBANNOTATION_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token SUBANNOTATION_DIRECTIVE");
/*  5785 */     RewriteRuleTokenStream stream_END_SUBANNOTATION_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_SUBANNOTATION_DIRECTIVE");
/*  5786 */     RewriteRuleTokenStream stream_CLASS_DESCRIPTOR = new RewriteRuleTokenStream(this.adaptor, "token CLASS_DESCRIPTOR");
/*  5787 */     RewriteRuleSubtreeStream stream_annotation_element = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation_element");
/*       */     try
/*       */     {
/*  5793 */       SUBANNOTATION_DIRECTIVE148 = (Token)match(this.input, 209, FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation2836);
/*  5794 */       stream_SUBANNOTATION_DIRECTIVE.add(SUBANNOTATION_DIRECTIVE148);
/*       */ 
/*  5796 */       CLASS_DESCRIPTOR149 = (Token)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_subannotation2838);
/*  5797 */       stream_CLASS_DESCRIPTOR.add(CLASS_DESCRIPTOR149);
/*       */       while (true)
/*       */       {
/*  5802 */         int alt23 = 2;
/*  5803 */         int LA23_0 = this.input.LA(1);
/*  5804 */         if ((LA23_0 == 4) || (LA23_0 == 6) || (LA23_0 == 21) || (LA23_0 == 35) || (LA23_0 == 52) || ((LA23_0 >= 58) && (LA23_0 <= 60)) || (LA23_0 == 62) || (LA23_0 == 64) || ((LA23_0 >= 67) && (LA23_0 <= 70)) || (LA23_0 == 74) || ((LA23_0 >= 76) && (LA23_0 <= 79)) || ((LA23_0 >= 81) && (LA23_0 <= 82)) || (LA23_0 == 84) || ((LA23_0 >= 88) && (LA23_0 <= 89)) || ((LA23_0 >= 91) && (LA23_0 <= 95)) || (LA23_0 == 101) || ((LA23_0 >= 190) && (LA23_0 <= 191)) || ((LA23_0 >= 197) && (LA23_0 <= 199)) || (LA23_0 == 201) || (LA23_0 == 205) || ((LA23_0 >= 211) && (LA23_0 <= 212))) {
/*  5805 */           alt23 = 1;
/*       */         }
/*       */ 
/*  5808 */         switch (alt23)
/*       */         {
/*       */         case 1:
/*  5812 */           pushFollow(FOLLOW_annotation_element_in_subannotation2840);
/*  5813 */           annotation_element150 = annotation_element();
/*  5814 */           this.state._fsp -= 1;
/*       */ 
/*  5816 */           stream_annotation_element.add(annotation_element150.getTree());
/*       */ 
/*  5818 */           break;
/*       */         default:
/*  5821 */           break label457;
/*       */         }
/*       */       }
/*       */ 
/*  5825 */       label457: END_SUBANNOTATION_DIRECTIVE151 = (Token)match(this.input, 44, FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation2843);
/*  5826 */       stream_END_SUBANNOTATION_DIRECTIVE.add(END_SUBANNOTATION_DIRECTIVE151);
/*       */ 
/*  5835 */       retval.tree = root_0;
/*  5836 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  5838 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5843 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  5844 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(180, retval.start, "I_SUBANNOTATION"), root_1);
/*  5845 */       this.adaptor.addChild(root_1, stream_CLASS_DESCRIPTOR.nextNode());
/*       */ 
/*  5847 */       while (stream_annotation_element.hasNext()) {
/*  5848 */         this.adaptor.addChild(root_1, stream_annotation_element.nextTree());
/*       */       }
/*  5850 */       stream_annotation_element.reset();
/*       */ 
/*  5852 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  5858 */       retval.tree = root_0;
/*       */ 
/*  5862 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  5864 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  5865 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  5869 */       reportError(re);
/*  5870 */       recover(this.input, re);
/*  5871 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  5876 */     return retval;
/*       */   }
/*       */ 
/*       */   public final enum_literal_return enum_literal()
/*       */     throws RecognitionException
/*       */   {
/*  5891 */     enum_literal_return retval = new enum_literal_return();
/*  5892 */     retval.start = this.input.LT(1);
/*       */ 
/*  5894 */     CommonTree root_0 = null;
/*       */ 
/*  5896 */     Token ENUM_DIRECTIVE152 = null;
/*  5897 */     Token ARROW154 = null;
/*  5898 */     Token COLON156 = null;
/*  5899 */     ParserRuleReturnScope reference_type_descriptor153 = null;
/*  5900 */     ParserRuleReturnScope simple_name155 = null;
/*  5901 */     ParserRuleReturnScope reference_type_descriptor157 = null;
/*       */ 
/*  5903 */     CommonTree ENUM_DIRECTIVE152_tree = null;
/*  5904 */     CommonTree ARROW154_tree = null;
/*  5905 */     CommonTree COLON156_tree = null;
/*  5906 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/*  5907 */     RewriteRuleTokenStream stream_ENUM_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token ENUM_DIRECTIVE");
/*  5908 */     RewriteRuleTokenStream stream_ARROW = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
/*  5909 */     RewriteRuleSubtreeStream stream_reference_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor");
/*  5910 */     RewriteRuleSubtreeStream stream_simple_name = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
/*       */     try
/*       */     {
/*  5916 */       ENUM_DIRECTIVE152 = (Token)match(this.input, 45, FOLLOW_ENUM_DIRECTIVE_in_enum_literal2869);
/*  5917 */       stream_ENUM_DIRECTIVE.add(ENUM_DIRECTIVE152);
/*       */ 
/*  5919 */       pushFollow(FOLLOW_reference_type_descriptor_in_enum_literal2871);
/*  5920 */       reference_type_descriptor153 = reference_type_descriptor();
/*  5921 */       this.state._fsp -= 1;
/*       */ 
/*  5923 */       stream_reference_type_descriptor.add(reference_type_descriptor153.getTree());
/*  5924 */       ARROW154 = (Token)match(this.input, 9, FOLLOW_ARROW_in_enum_literal2873);
/*  5925 */       stream_ARROW.add(ARROW154);
/*       */ 
/*  5927 */       pushFollow(FOLLOW_simple_name_in_enum_literal2875);
/*  5928 */       simple_name155 = simple_name();
/*  5929 */       this.state._fsp -= 1;
/*       */ 
/*  5931 */       stream_simple_name.add(simple_name155.getTree());
/*  5932 */       COLON156 = (Token)match(this.input, 30, FOLLOW_COLON_in_enum_literal2877);
/*  5933 */       stream_COLON.add(COLON156);
/*       */ 
/*  5935 */       pushFollow(FOLLOW_reference_type_descriptor_in_enum_literal2879);
/*  5936 */       reference_type_descriptor157 = reference_type_descriptor();
/*  5937 */       this.state._fsp -= 1;
/*       */ 
/*  5939 */       stream_reference_type_descriptor.add(reference_type_descriptor157.getTree());
/*       */ 
/*  5947 */       retval.tree = root_0;
/*  5948 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  5950 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  5955 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  5956 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(115, "I_ENCODED_ENUM"), root_1);
/*  5957 */       this.adaptor.addChild(root_1, stream_reference_type_descriptor.nextTree());
/*  5958 */       this.adaptor.addChild(root_1, stream_simple_name.nextTree());
/*  5959 */       this.adaptor.addChild(root_1, stream_reference_type_descriptor.nextTree());
/*  5960 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  5966 */       retval.tree = root_0;
/*       */ 
/*  5970 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  5972 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  5973 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  5977 */       reportError(re);
/*  5978 */       recover(this.input, re);
/*  5979 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  5984 */     return retval;
/*       */   }
/*       */ 
/*       */   public final type_field_method_literal_return type_field_method_literal()
/*       */     throws RecognitionException
/*       */   {
/*  5999 */     type_field_method_literal_return retval = new type_field_method_literal_return();
/*  6000 */     retval.start = this.input.LT(1);
/*       */ 
/*  6002 */     CommonTree root_0 = null;
/*       */ 
/*  6004 */     Token ARROW159 = null;
/*  6005 */     Token COLON161 = null;
/*  6006 */     Token PRIMITIVE_TYPE165 = null;
/*  6007 */     Token VOID_TYPE166 = null;
/*  6008 */     ParserRuleReturnScope reference_type_descriptor158 = null;
/*  6009 */     ParserRuleReturnScope member_name160 = null;
/*  6010 */     ParserRuleReturnScope nonvoid_type_descriptor162 = null;
/*  6011 */     ParserRuleReturnScope member_name163 = null;
/*  6012 */     ParserRuleReturnScope method_prototype164 = null;
/*       */ 
/*  6014 */     CommonTree ARROW159_tree = null;
/*  6015 */     CommonTree COLON161_tree = null;
/*  6016 */     CommonTree PRIMITIVE_TYPE165_tree = null;
/*  6017 */     CommonTree VOID_TYPE166_tree = null;
/*  6018 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/*  6019 */     RewriteRuleTokenStream stream_ARROW = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
/*  6020 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/*  6021 */     RewriteRuleSubtreeStream stream_method_prototype = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
/*  6022 */     RewriteRuleSubtreeStream stream_reference_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor");
/*  6023 */     RewriteRuleSubtreeStream stream_member_name = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
/*       */     try
/*       */     {
/*  6027 */       int alt26 = 3;
/*  6028 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 8:
/*       */       case 26:
/*  6032 */         alt26 = 1;
/*       */ 
/*  6034 */         break;
/*       */       case 199:
/*  6037 */         alt26 = 2;
/*       */ 
/*  6039 */         break;
/*       */       case 212:
/*  6042 */         alt26 = 3;
/*       */ 
/*  6044 */         break;
/*       */       default:
/*  6046 */         NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*  6050 */       switch (alt26)
/*       */       {
/*       */       case 1:
/*  6054 */         pushFollow(FOLLOW_reference_type_descriptor_in_type_field_method_literal2903);
/*  6055 */         reference_type_descriptor158 = reference_type_descriptor();
/*  6056 */         this.state._fsp -= 1;
/*       */ 
/*  6058 */         stream_reference_type_descriptor.add(reference_type_descriptor158.getTree());
/*       */ 
/*  6060 */         int alt25 = 2;
/*  6061 */         int LA25_0 = this.input.LA(1);
/*  6062 */         if (LA25_0 == 9) {
/*  6063 */           alt25 = 1;
/*       */         }
/*  6065 */         else if ((LA25_0 == -1) || ((LA25_0 >= 4) && (LA25_0 <= 6)) || (LA25_0 == 21) || ((LA25_0 >= 27) && (LA25_0 <= 28)) || (LA25_0 == 31) || ((LA25_0 >= 35) && (LA25_0 <= 36)) || (LA25_0 == 38) || (LA25_0 == 44) || (LA25_0 == 49) || (LA25_0 == 52) || (LA25_0 == 56) || ((LA25_0 >= 58) && (LA25_0 <= 60)) || (LA25_0 == 62) || (LA25_0 == 64) || ((LA25_0 >= 67) && (LA25_0 <= 70)) || (LA25_0 == 74) || ((LA25_0 >= 76) && (LA25_0 <= 79)) || ((LA25_0 >= 81) && (LA25_0 <= 82)) || (LA25_0 == 84) || ((LA25_0 >= 88) && (LA25_0 <= 89)) || ((LA25_0 >= 91) && (LA25_0 <= 95)) || (LA25_0 == 101) || ((LA25_0 >= 189) && (LA25_0 <= 191)) || ((LA25_0 >= 197) && (LA25_0 <= 199)) || (LA25_0 == 201) || ((LA25_0 >= 205) && (LA25_0 <= 206)) || ((LA25_0 >= 210) && (LA25_0 <= 212))) {
/*  6066 */           alt25 = 2;
/*       */         }
/*       */         else
/*       */         {
/*  6070 */           NoViableAltException nvae = new NoViableAltException("", 25, 0, this.input);
/*       */           throw nvae;
/*       */         }
/*       */ 
/*  6075 */         switch (alt25)
/*       */         {
/*       */         case 1:
/*  6079 */           ARROW159 = (Token)match(this.input, 9, FOLLOW_ARROW_in_type_field_method_literal2911);
/*  6080 */           stream_ARROW.add(ARROW159);
/*       */ 
/*  6083 */           int alt24 = 2;
/*  6084 */           switch (this.input.LA(1))
/*       */           {
/*       */           case 205:
/*  6087 */             int LA24_1 = this.input.LA(2);
/*  6088 */             if (LA24_1 == 30) {
/*  6089 */               alt24 = 1;
/*       */             }
/*  6091 */             else if (LA24_1 == 193) {
/*  6092 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6096 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6098 */                 this.input.consume();
/*  6099 */                 NoViableAltException nvae = new NoViableAltException("", 24, 1, this.input);
/*       */ 
/*  6101 */                 throw nvae;
/*       */               } finally {
/*  6103 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6108 */             break;
/*       */           case 4:
/*  6111 */             int LA24_2 = this.input.LA(2);
/*  6112 */             if (LA24_2 == 30) {
/*  6113 */               alt24 = 1;
/*       */             }
/*  6115 */             else if (LA24_2 == 193) {
/*  6116 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6120 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6122 */                 this.input.consume();
/*  6123 */                 NoViableAltException nvae = new NoViableAltException("", 24, 2, this.input);
/*       */ 
/*  6125 */                 throw nvae;
/*       */               } finally {
/*  6127 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6132 */             break;
/*       */           case 211:
/*  6135 */             int LA24_3 = this.input.LA(2);
/*  6136 */             if (LA24_3 == 30) {
/*  6137 */               alt24 = 1;
/*       */             }
/*  6139 */             else if (LA24_3 == 193) {
/*  6140 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6144 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6146 */                 this.input.consume();
/*  6147 */                 NoViableAltException nvae = new NoViableAltException("", 24, 3, this.input);
/*       */ 
/*  6149 */                 throw nvae;
/*       */               } finally {
/*  6151 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6156 */             break;
/*       */           case 198:
/*  6159 */             int LA24_4 = this.input.LA(2);
/*  6160 */             if (LA24_4 == 30) {
/*  6161 */               alt24 = 1;
/*       */             }
/*  6163 */             else if (LA24_4 == 193) {
/*  6164 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6168 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6170 */                 this.input.consume();
/*  6171 */                 NoViableAltException nvae = new NoViableAltException("", 24, 4, this.input);
/*       */ 
/*  6173 */                 throw nvae;
/*       */               } finally {
/*  6175 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6180 */             break;
/*       */           case 190:
/*  6183 */             int LA24_5 = this.input.LA(2);
/*  6184 */             if (LA24_5 == 30) {
/*  6185 */               alt24 = 1;
/*       */             }
/*  6187 */             else if (LA24_5 == 193) {
/*  6188 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6192 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6194 */                 this.input.consume();
/*  6195 */                 NoViableAltException nvae = new NoViableAltException("", 24, 5, this.input);
/*       */ 
/*  6197 */                 throw nvae;
/*       */               } finally {
/*  6199 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6204 */             break;
/*       */           case 52:
/*  6207 */             int LA24_6 = this.input.LA(2);
/*  6208 */             if (LA24_6 == 30) {
/*  6209 */               alt24 = 1;
/*       */             }
/*  6211 */             else if (LA24_6 == 193) {
/*  6212 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6216 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6218 */                 this.input.consume();
/*  6219 */                 NoViableAltException nvae = new NoViableAltException("", 24, 6, this.input);
/*       */ 
/*  6221 */                 throw nvae;
/*       */               } finally {
/*  6223 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6228 */             break;
/*       */           case 35:
/*  6231 */             int LA24_7 = this.input.LA(2);
/*  6232 */             if (LA24_7 == 30) {
/*  6233 */               alt24 = 1;
/*       */             }
/*  6235 */             else if (LA24_7 == 193) {
/*  6236 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6240 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6242 */                 this.input.consume();
/*  6243 */                 NoViableAltException nvae = new NoViableAltException("", 24, 7, this.input);
/*       */ 
/*  6245 */                 throw nvae;
/*       */               } finally {
/*  6247 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6252 */             break;
/*       */           case 21:
/*  6255 */             int LA24_8 = this.input.LA(2);
/*  6256 */             if (LA24_8 == 30) {
/*  6257 */               alt24 = 1;
/*       */             }
/*  6259 */             else if (LA24_8 == 193) {
/*  6260 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6264 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6266 */                 this.input.consume();
/*  6267 */                 NoViableAltException nvae = new NoViableAltException("", 24, 8, this.input);
/*       */ 
/*  6269 */                 throw nvae;
/*       */               } finally {
/*  6271 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6276 */             break;
/*       */           case 191:
/*  6279 */             int LA24_9 = this.input.LA(2);
/*  6280 */             if (LA24_9 == 30) {
/*  6281 */               alt24 = 1;
/*       */             }
/*  6283 */             else if (LA24_9 == 193) {
/*  6284 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6288 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6290 */                 this.input.consume();
/*  6291 */                 NoViableAltException nvae = new NoViableAltException("", 24, 9, this.input);
/*       */ 
/*  6293 */                 throw nvae;
/*       */               } finally {
/*  6295 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6300 */             break;
/*       */           case 201:
/*  6303 */             int LA24_10 = this.input.LA(2);
/*  6304 */             if (LA24_10 == 30) {
/*  6305 */               alt24 = 1;
/*       */             }
/*  6307 */             else if (LA24_10 == 193) {
/*  6308 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6312 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6314 */                 this.input.consume();
/*  6315 */                 NoViableAltException nvae = new NoViableAltException("", 24, 10, this.input);
/*       */ 
/*  6317 */                 throw nvae;
/*       */               } finally {
/*  6319 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6324 */             break;
/*       */           case 197:
/*  6327 */             int LA24_11 = this.input.LA(2);
/*  6328 */             if (LA24_11 == 30) {
/*  6329 */               alt24 = 1;
/*       */             }
/*  6331 */             else if (LA24_11 == 193) {
/*  6332 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6336 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6338 */                 this.input.consume();
/*  6339 */                 NoViableAltException nvae = new NoViableAltException("", 24, 11, this.input);
/*       */ 
/*  6341 */                 throw nvae;
/*       */               } finally {
/*  6343 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6348 */             break;
/*       */           case 199:
/*  6351 */             int LA24_12 = this.input.LA(2);
/*  6352 */             if (LA24_12 == 30) {
/*  6353 */               alt24 = 1;
/*       */             }
/*  6355 */             else if (LA24_12 == 193) {
/*  6356 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6360 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6362 */                 this.input.consume();
/*  6363 */                 NoViableAltException nvae = new NoViableAltException("", 24, 12, this.input);
/*       */ 
/*  6365 */                 throw nvae;
/*       */               } finally {
/*  6367 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6372 */             break;
/*       */           case 212:
/*  6375 */             int LA24_13 = this.input.LA(2);
/*  6376 */             if (LA24_13 == 30) {
/*  6377 */               alt24 = 1;
/*       */             }
/*  6379 */             else if (LA24_13 == 193) {
/*  6380 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6384 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6386 */                 this.input.consume();
/*  6387 */                 NoViableAltException nvae = new NoViableAltException("", 24, 13, this.input);
/*       */ 
/*  6389 */                 throw nvae;
/*       */               } finally {
/*  6391 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6396 */             break;
/*       */           case 6:
/*  6399 */             int LA24_14 = this.input.LA(2);
/*  6400 */             if (LA24_14 == 30) {
/*  6401 */               alt24 = 1;
/*       */             }
/*  6403 */             else if (LA24_14 == 193) {
/*  6404 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6408 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6410 */                 this.input.consume();
/*  6411 */                 NoViableAltException nvae = new NoViableAltException("", 24, 14, this.input);
/*       */ 
/*  6413 */                 throw nvae;
/*       */               } finally {
/*  6415 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6420 */             break;
/*       */           case 58:
/*  6423 */             int LA24_15 = this.input.LA(2);
/*  6424 */             if (LA24_15 == 30) {
/*  6425 */               alt24 = 1;
/*       */             }
/*  6427 */             else if (LA24_15 == 193) {
/*  6428 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6432 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6434 */                 this.input.consume();
/*  6435 */                 NoViableAltException nvae = new NoViableAltException("", 24, 15, this.input);
/*       */ 
/*  6437 */                 throw nvae;
/*       */               } finally {
/*  6439 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6444 */             break;
/*       */           case 59:
/*  6447 */             int LA24_16 = this.input.LA(2);
/*  6448 */             if (LA24_16 == 30) {
/*  6449 */               alt24 = 1;
/*       */             }
/*  6451 */             else if (LA24_16 == 193) {
/*  6452 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6456 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6458 */                 this.input.consume();
/*  6459 */                 NoViableAltException nvae = new NoViableAltException("", 24, 16, this.input);
/*       */ 
/*  6461 */                 throw nvae;
/*       */               } finally {
/*  6463 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6468 */             break;
/*       */           case 60:
/*  6471 */             int LA24_17 = this.input.LA(2);
/*  6472 */             if (LA24_17 == 30) {
/*  6473 */               alt24 = 1;
/*       */             }
/*  6475 */             else if (LA24_17 == 193) {
/*  6476 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6480 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6482 */                 this.input.consume();
/*  6483 */                 NoViableAltException nvae = new NoViableAltException("", 24, 17, this.input);
/*       */ 
/*  6485 */                 throw nvae;
/*       */               } finally {
/*  6487 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6492 */             break;
/*       */           case 62:
/*  6495 */             int LA24_18 = this.input.LA(2);
/*  6496 */             if (LA24_18 == 30) {
/*  6497 */               alt24 = 1;
/*       */             }
/*  6499 */             else if (LA24_18 == 193) {
/*  6500 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6504 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6506 */                 this.input.consume();
/*  6507 */                 NoViableAltException nvae = new NoViableAltException("", 24, 18, this.input);
/*       */ 
/*  6509 */                 throw nvae;
/*       */               } finally {
/*  6511 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6516 */             break;
/*       */           case 64:
/*  6519 */             int LA24_19 = this.input.LA(2);
/*  6520 */             if (LA24_19 == 30) {
/*  6521 */               alt24 = 1;
/*       */             }
/*  6523 */             else if (LA24_19 == 193) {
/*  6524 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6528 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6530 */                 this.input.consume();
/*  6531 */                 NoViableAltException nvae = new NoViableAltException("", 24, 19, this.input);
/*       */ 
/*  6533 */                 throw nvae;
/*       */               } finally {
/*  6535 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6540 */             break;
/*       */           case 67:
/*  6543 */             int LA24_20 = this.input.LA(2);
/*  6544 */             if (LA24_20 == 30) {
/*  6545 */               alt24 = 1;
/*       */             }
/*  6547 */             else if (LA24_20 == 193) {
/*  6548 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6552 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6554 */                 this.input.consume();
/*  6555 */                 NoViableAltException nvae = new NoViableAltException("", 24, 20, this.input);
/*       */ 
/*  6557 */                 throw nvae;
/*       */               } finally {
/*  6559 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6564 */             break;
/*       */           case 68:
/*  6567 */             int LA24_21 = this.input.LA(2);
/*  6568 */             if (LA24_21 == 30) {
/*  6569 */               alt24 = 1;
/*       */             }
/*  6571 */             else if (LA24_21 == 193) {
/*  6572 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6576 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6578 */                 this.input.consume();
/*  6579 */                 NoViableAltException nvae = new NoViableAltException("", 24, 21, this.input);
/*       */ 
/*  6581 */                 throw nvae;
/*       */               } finally {
/*  6583 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6588 */             break;
/*       */           case 69:
/*  6591 */             int LA24_22 = this.input.LA(2);
/*  6592 */             if (LA24_22 == 30) {
/*  6593 */               alt24 = 1;
/*       */             }
/*  6595 */             else if (LA24_22 == 193) {
/*  6596 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6600 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6602 */                 this.input.consume();
/*  6603 */                 NoViableAltException nvae = new NoViableAltException("", 24, 22, this.input);
/*       */ 
/*  6605 */                 throw nvae;
/*       */               } finally {
/*  6607 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6612 */             break;
/*       */           case 70:
/*  6615 */             int LA24_23 = this.input.LA(2);
/*  6616 */             if (LA24_23 == 30) {
/*  6617 */               alt24 = 1;
/*       */             }
/*  6619 */             else if (LA24_23 == 193) {
/*  6620 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6624 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6626 */                 this.input.consume();
/*  6627 */                 NoViableAltException nvae = new NoViableAltException("", 24, 23, this.input);
/*       */ 
/*  6629 */                 throw nvae;
/*       */               } finally {
/*  6631 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6636 */             break;
/*       */           case 74:
/*  6639 */             int LA24_24 = this.input.LA(2);
/*  6640 */             if (LA24_24 == 30) {
/*  6641 */               alt24 = 1;
/*       */             }
/*  6643 */             else if (LA24_24 == 193) {
/*  6644 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6648 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6650 */                 this.input.consume();
/*  6651 */                 NoViableAltException nvae = new NoViableAltException("", 24, 24, this.input);
/*       */ 
/*  6653 */                 throw nvae;
/*       */               } finally {
/*  6655 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6660 */             break;
/*       */           case 76:
/*  6663 */             int LA24_25 = this.input.LA(2);
/*  6664 */             if (LA24_25 == 30) {
/*  6665 */               alt24 = 1;
/*       */             }
/*  6667 */             else if (LA24_25 == 193) {
/*  6668 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6672 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6674 */                 this.input.consume();
/*  6675 */                 NoViableAltException nvae = new NoViableAltException("", 24, 25, this.input);
/*       */ 
/*  6677 */                 throw nvae;
/*       */               } finally {
/*  6679 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6684 */             break;
/*       */           case 77:
/*  6687 */             int LA24_26 = this.input.LA(2);
/*  6688 */             if (LA24_26 == 30) {
/*  6689 */               alt24 = 1;
/*       */             }
/*  6691 */             else if (LA24_26 == 193) {
/*  6692 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6696 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6698 */                 this.input.consume();
/*  6699 */                 NoViableAltException nvae = new NoViableAltException("", 24, 26, this.input);
/*       */ 
/*  6701 */                 throw nvae;
/*       */               } finally {
/*  6703 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6708 */             break;
/*       */           case 78:
/*  6711 */             int LA24_27 = this.input.LA(2);
/*  6712 */             if (LA24_27 == 30) {
/*  6713 */               alt24 = 1;
/*       */             }
/*  6715 */             else if (LA24_27 == 193) {
/*  6716 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6720 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6722 */                 this.input.consume();
/*  6723 */                 NoViableAltException nvae = new NoViableAltException("", 24, 27, this.input);
/*       */ 
/*  6725 */                 throw nvae;
/*       */               } finally {
/*  6727 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6732 */             break;
/*       */           case 79:
/*  6735 */             int LA24_28 = this.input.LA(2);
/*  6736 */             if (LA24_28 == 30) {
/*  6737 */               alt24 = 1;
/*       */             }
/*  6739 */             else if (LA24_28 == 193) {
/*  6740 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6744 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6746 */                 this.input.consume();
/*  6747 */                 NoViableAltException nvae = new NoViableAltException("", 24, 28, this.input);
/*       */ 
/*  6749 */                 throw nvae;
/*       */               } finally {
/*  6751 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6756 */             break;
/*       */           case 81:
/*  6759 */             int LA24_29 = this.input.LA(2);
/*  6760 */             if (LA24_29 == 30) {
/*  6761 */               alt24 = 1;
/*       */             }
/*  6763 */             else if (LA24_29 == 193) {
/*  6764 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6768 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6770 */                 this.input.consume();
/*  6771 */                 NoViableAltException nvae = new NoViableAltException("", 24, 29, this.input);
/*       */ 
/*  6773 */                 throw nvae;
/*       */               } finally {
/*  6775 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6780 */             break;
/*       */           case 82:
/*  6783 */             int LA24_30 = this.input.LA(2);
/*  6784 */             if (LA24_30 == 30) {
/*  6785 */               alt24 = 1;
/*       */             }
/*  6787 */             else if (LA24_30 == 193) {
/*  6788 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6792 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6794 */                 this.input.consume();
/*  6795 */                 NoViableAltException nvae = new NoViableAltException("", 24, 30, this.input);
/*       */ 
/*  6797 */                 throw nvae;
/*       */               } finally {
/*  6799 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6804 */             break;
/*       */           case 84:
/*  6807 */             int LA24_31 = this.input.LA(2);
/*  6808 */             if (LA24_31 == 30) {
/*  6809 */               alt24 = 1;
/*       */             }
/*  6811 */             else if (LA24_31 == 193) {
/*  6812 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6816 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6818 */                 this.input.consume();
/*  6819 */                 NoViableAltException nvae = new NoViableAltException("", 24, 31, this.input);
/*       */ 
/*  6821 */                 throw nvae;
/*       */               } finally {
/*  6823 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6828 */             break;
/*       */           case 88:
/*  6831 */             int LA24_32 = this.input.LA(2);
/*  6832 */             if (LA24_32 == 30) {
/*  6833 */               alt24 = 1;
/*       */             }
/*  6835 */             else if (LA24_32 == 193) {
/*  6836 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6840 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6842 */                 this.input.consume();
/*  6843 */                 NoViableAltException nvae = new NoViableAltException("", 24, 32, this.input);
/*       */ 
/*  6845 */                 throw nvae;
/*       */               } finally {
/*  6847 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6852 */             break;
/*       */           case 89:
/*  6855 */             int LA24_33 = this.input.LA(2);
/*  6856 */             if (LA24_33 == 30) {
/*  6857 */               alt24 = 1;
/*       */             }
/*  6859 */             else if (LA24_33 == 193) {
/*  6860 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6864 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6866 */                 this.input.consume();
/*  6867 */                 NoViableAltException nvae = new NoViableAltException("", 24, 33, this.input);
/*       */ 
/*  6869 */                 throw nvae;
/*       */               } finally {
/*  6871 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6876 */             break;
/*       */           case 91:
/*  6879 */             int LA24_34 = this.input.LA(2);
/*  6880 */             if (LA24_34 == 30) {
/*  6881 */               alt24 = 1;
/*       */             }
/*  6883 */             else if (LA24_34 == 193) {
/*  6884 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6888 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6890 */                 this.input.consume();
/*  6891 */                 NoViableAltException nvae = new NoViableAltException("", 24, 34, this.input);
/*       */ 
/*  6893 */                 throw nvae;
/*       */               } finally {
/*  6895 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6900 */             break;
/*       */           case 92:
/*  6903 */             int LA24_35 = this.input.LA(2);
/*  6904 */             if (LA24_35 == 30) {
/*  6905 */               alt24 = 1;
/*       */             }
/*  6907 */             else if (LA24_35 == 193) {
/*  6908 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6912 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6914 */                 this.input.consume();
/*  6915 */                 NoViableAltException nvae = new NoViableAltException("", 24, 35, this.input);
/*       */ 
/*  6917 */                 throw nvae;
/*       */               } finally {
/*  6919 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6924 */             break;
/*       */           case 93:
/*  6927 */             int LA24_36 = this.input.LA(2);
/*  6928 */             if (LA24_36 == 30) {
/*  6929 */               alt24 = 1;
/*       */             }
/*  6931 */             else if (LA24_36 == 193) {
/*  6932 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6936 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6938 */                 this.input.consume();
/*  6939 */                 NoViableAltException nvae = new NoViableAltException("", 24, 36, this.input);
/*       */ 
/*  6941 */                 throw nvae;
/*       */               } finally {
/*  6943 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6948 */             break;
/*       */           case 94:
/*  6951 */             int LA24_37 = this.input.LA(2);
/*  6952 */             if (LA24_37 == 30) {
/*  6953 */               alt24 = 1;
/*       */             }
/*  6955 */             else if (LA24_37 == 193) {
/*  6956 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6960 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6962 */                 this.input.consume();
/*  6963 */                 NoViableAltException nvae = new NoViableAltException("", 24, 37, this.input);
/*       */ 
/*  6965 */                 throw nvae;
/*       */               } finally {
/*  6967 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6972 */             break;
/*       */           case 95:
/*  6975 */             int LA24_38 = this.input.LA(2);
/*  6976 */             if (LA24_38 == 30) {
/*  6977 */               alt24 = 1;
/*       */             }
/*  6979 */             else if (LA24_38 == 193) {
/*  6980 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  6984 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  6986 */                 this.input.consume();
/*  6987 */                 NoViableAltException nvae = new NoViableAltException("", 24, 38, this.input);
/*       */ 
/*  6989 */                 throw nvae;
/*       */               } finally {
/*  6991 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  6996 */             break;
/*       */           case 101:
/*  6999 */             int LA24_39 = this.input.LA(2);
/*  7000 */             if (LA24_39 == 30) {
/*  7001 */               alt24 = 1;
/*       */             }
/*  7003 */             else if (LA24_39 == 193) {
/*  7004 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  7008 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  7010 */                 this.input.consume();
/*  7011 */                 NoViableAltException nvae = new NoViableAltException("", 24, 39, this.input);
/*       */ 
/*  7013 */                 throw nvae;
/*       */               } finally {
/*  7015 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  7020 */             break;
/*       */           case 188:
/*  7023 */             int LA24_40 = this.input.LA(2);
/*  7024 */             if (LA24_40 == 30) {
/*  7025 */               alt24 = 1;
/*       */             }
/*  7027 */             else if (LA24_40 == 193) {
/*  7028 */               alt24 = 2;
/*       */             }
/*       */             else
/*       */             {
/*  7032 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  7034 */                 this.input.consume();
/*  7035 */                 NoViableAltException nvae = new NoViableAltException("", 24, 40, this.input);
/*       */ 
/*  7037 */                 throw nvae;
/*       */               } finally {
/*  7039 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  7044 */             break;
/*       */           default:
/*  7046 */             NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
/*       */             throw nvae;
/*       */           }
/*  7050 */           switch (alt24)
/*       */           {
/*       */           case 1:
/*  7054 */             pushFollow(FOLLOW_member_name_in_type_field_method_literal2921);
/*  7055 */             member_name160 = member_name();
/*  7056 */             this.state._fsp -= 1;
/*       */ 
/*  7058 */             stream_member_name.add(member_name160.getTree());
/*  7059 */             COLON161 = (Token)match(this.input, 30, FOLLOW_COLON_in_type_field_method_literal2923);
/*  7060 */             stream_COLON.add(COLON161);
/*       */ 
/*  7062 */             pushFollow(FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal2925);
/*  7063 */             nonvoid_type_descriptor162 = nonvoid_type_descriptor();
/*  7064 */             this.state._fsp -= 1;
/*       */ 
/*  7066 */             stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor162.getTree());
/*       */ 
/*  7074 */             retval.tree = root_0;
/*  7075 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7077 */             root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7082 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  7083 */             root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(116, "I_ENCODED_FIELD"), root_1);
/*  7084 */             this.adaptor.addChild(root_1, stream_reference_type_descriptor.nextTree());
/*  7085 */             this.adaptor.addChild(root_1, stream_member_name.nextTree());
/*  7086 */             this.adaptor.addChild(root_1, stream_nonvoid_type_descriptor.nextTree());
/*  7087 */             this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  7093 */             retval.tree = root_0;
/*       */ 
/*  7096 */             break;
/*       */           case 2:
/*  7100 */             pushFollow(FOLLOW_member_name_in_type_field_method_literal2947);
/*  7101 */             member_name163 = member_name();
/*  7102 */             this.state._fsp -= 1;
/*       */ 
/*  7104 */             stream_member_name.add(member_name163.getTree());
/*  7105 */             pushFollow(FOLLOW_method_prototype_in_type_field_method_literal2949);
/*  7106 */             method_prototype164 = method_prototype();
/*  7107 */             this.state._fsp -= 1;
/*       */ 
/*  7109 */             stream_method_prototype.add(method_prototype164.getTree());
/*       */ 
/*  7117 */             retval.tree = root_0;
/*  7118 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7120 */             root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7125 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  7126 */             root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(117, "I_ENCODED_METHOD"), root_1);
/*  7127 */             this.adaptor.addChild(root_1, stream_reference_type_descriptor.nextTree());
/*  7128 */             this.adaptor.addChild(root_1, stream_member_name.nextTree());
/*  7129 */             this.adaptor.addChild(root_1, stream_method_prototype.nextTree());
/*  7130 */             this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  7136 */             retval.tree = root_0;
/*       */           }
/*       */ 
/*  7144 */           break;
/*       */         case 2:
/*  7155 */           retval.tree = root_0;
/*  7156 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7158 */           root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7161 */           this.adaptor.addChild(root_0, stream_reference_type_descriptor.nextTree());
/*       */ 
/*  7165 */           retval.tree = root_0;
/*       */         }
/*       */ 
/*  7173 */         break;
/*       */       case 2:
/*  7177 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7180 */         PRIMITIVE_TYPE165 = (Token)match(this.input, 199, FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal2991);
/*  7181 */         PRIMITIVE_TYPE165_tree = (CommonTree)this.adaptor.create(PRIMITIVE_TYPE165);
/*  7182 */         this.adaptor.addChild(root_0, PRIMITIVE_TYPE165_tree);
/*       */ 
/*  7185 */         break;
/*       */       case 3:
/*  7189 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7192 */         VOID_TYPE166 = (Token)match(this.input, 212, FOLLOW_VOID_TYPE_in_type_field_method_literal2997);
/*  7193 */         VOID_TYPE166_tree = (CommonTree)this.adaptor.create(VOID_TYPE166);
/*  7194 */         this.adaptor.addChild(root_0, VOID_TYPE166_tree);
/*       */       }
/*       */ 
/*  7200 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  7202 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  7203 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  7207 */       reportError(re);
/*  7208 */       recover(this.input, re);
/*  7209 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  7214 */     return retval;
/*       */   }
/*       */ 
/*       */   public final fully_qualified_method_return fully_qualified_method()
/*       */     throws RecognitionException
/*       */   {
/*  7229 */     fully_qualified_method_return retval = new fully_qualified_method_return();
/*  7230 */     retval.start = this.input.LT(1);
/*       */ 
/*  7232 */     CommonTree root_0 = null;
/*       */ 
/*  7234 */     Token ARROW168 = null;
/*  7235 */     ParserRuleReturnScope reference_type_descriptor167 = null;
/*  7236 */     ParserRuleReturnScope member_name169 = null;
/*  7237 */     ParserRuleReturnScope method_prototype170 = null;
/*       */ 
/*  7239 */     CommonTree ARROW168_tree = null;
/*  7240 */     RewriteRuleTokenStream stream_ARROW = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
/*  7241 */     RewriteRuleSubtreeStream stream_method_prototype = new RewriteRuleSubtreeStream(this.adaptor, "rule method_prototype");
/*  7242 */     RewriteRuleSubtreeStream stream_reference_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor");
/*  7243 */     RewriteRuleSubtreeStream stream_member_name = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
/*       */     try
/*       */     {
/*  7249 */       pushFollow(FOLLOW_reference_type_descriptor_in_fully_qualified_method3007);
/*  7250 */       reference_type_descriptor167 = reference_type_descriptor();
/*  7251 */       this.state._fsp -= 1;
/*       */ 
/*  7253 */       stream_reference_type_descriptor.add(reference_type_descriptor167.getTree());
/*  7254 */       ARROW168 = (Token)match(this.input, 9, FOLLOW_ARROW_in_fully_qualified_method3009);
/*  7255 */       stream_ARROW.add(ARROW168);
/*       */ 
/*  7257 */       pushFollow(FOLLOW_member_name_in_fully_qualified_method3011);
/*  7258 */       member_name169 = member_name();
/*  7259 */       this.state._fsp -= 1;
/*       */ 
/*  7261 */       stream_member_name.add(member_name169.getTree());
/*  7262 */       pushFollow(FOLLOW_method_prototype_in_fully_qualified_method3013);
/*  7263 */       method_prototype170 = method_prototype();
/*  7264 */       this.state._fsp -= 1;
/*       */ 
/*  7266 */       stream_method_prototype.add(method_prototype170.getTree());
/*       */ 
/*  7274 */       retval.tree = root_0;
/*  7275 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7277 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7280 */       this.adaptor.addChild(root_0, stream_reference_type_descriptor.nextTree());
/*  7281 */       this.adaptor.addChild(root_0, stream_member_name.nextTree());
/*  7282 */       this.adaptor.addChild(root_0, stream_method_prototype.nextTree());
/*       */ 
/*  7286 */       retval.tree = root_0;
/*       */ 
/*  7290 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  7292 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  7293 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  7297 */       reportError(re);
/*  7298 */       recover(this.input, re);
/*  7299 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  7304 */     return retval;
/*       */   }
/*       */ 
/*       */   public final fully_qualified_field_return fully_qualified_field()
/*       */     throws RecognitionException
/*       */   {
/*  7319 */     fully_qualified_field_return retval = new fully_qualified_field_return();
/*  7320 */     retval.start = this.input.LT(1);
/*       */ 
/*  7322 */     CommonTree root_0 = null;
/*       */ 
/*  7324 */     Token ARROW172 = null;
/*  7325 */     Token COLON174 = null;
/*  7326 */     ParserRuleReturnScope reference_type_descriptor171 = null;
/*  7327 */     ParserRuleReturnScope member_name173 = null;
/*  7328 */     ParserRuleReturnScope nonvoid_type_descriptor175 = null;
/*       */ 
/*  7330 */     CommonTree ARROW172_tree = null;
/*  7331 */     CommonTree COLON174_tree = null;
/*  7332 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/*  7333 */     RewriteRuleTokenStream stream_ARROW = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
/*  7334 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/*  7335 */     RewriteRuleSubtreeStream stream_reference_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule reference_type_descriptor");
/*  7336 */     RewriteRuleSubtreeStream stream_member_name = new RewriteRuleSubtreeStream(this.adaptor, "rule member_name");
/*       */     try
/*       */     {
/*  7342 */       pushFollow(FOLLOW_reference_type_descriptor_in_fully_qualified_field3033);
/*  7343 */       reference_type_descriptor171 = reference_type_descriptor();
/*  7344 */       this.state._fsp -= 1;
/*       */ 
/*  7346 */       stream_reference_type_descriptor.add(reference_type_descriptor171.getTree());
/*  7347 */       ARROW172 = (Token)match(this.input, 9, FOLLOW_ARROW_in_fully_qualified_field3035);
/*  7348 */       stream_ARROW.add(ARROW172);
/*       */ 
/*  7350 */       pushFollow(FOLLOW_member_name_in_fully_qualified_field3037);
/*  7351 */       member_name173 = member_name();
/*  7352 */       this.state._fsp -= 1;
/*       */ 
/*  7354 */       stream_member_name.add(member_name173.getTree());
/*  7355 */       COLON174 = (Token)match(this.input, 30, FOLLOW_COLON_in_fully_qualified_field3039);
/*  7356 */       stream_COLON.add(COLON174);
/*       */ 
/*  7358 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_fully_qualified_field3041);
/*  7359 */       nonvoid_type_descriptor175 = nonvoid_type_descriptor();
/*  7360 */       this.state._fsp -= 1;
/*       */ 
/*  7362 */       stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor175.getTree());
/*       */ 
/*  7370 */       retval.tree = root_0;
/*  7371 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7373 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7376 */       this.adaptor.addChild(root_0, stream_reference_type_descriptor.nextTree());
/*  7377 */       this.adaptor.addChild(root_0, stream_member_name.nextTree());
/*  7378 */       this.adaptor.addChild(root_0, stream_nonvoid_type_descriptor.nextTree());
/*       */ 
/*  7382 */       retval.tree = root_0;
/*       */ 
/*  7386 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  7388 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  7389 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  7393 */       reportError(re);
/*  7394 */       recover(this.input, re);
/*  7395 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  7400 */     return retval;
/*       */   }
/*       */ 
/*       */   public final label_return label()
/*       */     throws RecognitionException
/*       */   {
/*  7415 */     label_return retval = new label_return();
/*  7416 */     retval.start = this.input.LT(1);
/*       */ 
/*  7418 */     CommonTree root_0 = null;
/*       */ 
/*  7420 */     Token COLON176 = null;
/*  7421 */     ParserRuleReturnScope simple_name177 = null;
/*       */ 
/*  7423 */     CommonTree COLON176_tree = null;
/*  7424 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/*  7425 */     RewriteRuleSubtreeStream stream_simple_name = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
/*       */     try
/*       */     {
/*  7431 */       COLON176 = (Token)match(this.input, 30, FOLLOW_COLON_in_label3061);
/*  7432 */       stream_COLON.add(COLON176);
/*       */ 
/*  7434 */       pushFollow(FOLLOW_simple_name_in_label3063);
/*  7435 */       simple_name177 = simple_name();
/*  7436 */       this.state._fsp -= 1;
/*       */ 
/*  7438 */       stream_simple_name.add(simple_name177.getTree());
/*       */ 
/*  7446 */       retval.tree = root_0;
/*  7447 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7449 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7454 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  7455 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(125, COLON176, "I_LABEL"), root_1);
/*  7456 */       this.adaptor.addChild(root_1, stream_simple_name.nextTree());
/*  7457 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  7463 */       retval.tree = root_0;
/*       */ 
/*  7467 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  7469 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  7470 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  7474 */       reportError(re);
/*  7475 */       recover(this.input, re);
/*  7476 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  7481 */     return retval;
/*       */   }
/*       */ 
/*       */   public final label_ref_return label_ref()
/*       */     throws RecognitionException
/*       */   {
/*  7496 */     label_ref_return retval = new label_ref_return();
/*  7497 */     retval.start = this.input.LT(1);
/*       */ 
/*  7499 */     CommonTree root_0 = null;
/*       */ 
/*  7501 */     Token COLON178 = null;
/*  7502 */     ParserRuleReturnScope simple_name179 = null;
/*       */ 
/*  7504 */     CommonTree COLON178_tree = null;
/*  7505 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/*  7506 */     RewriteRuleSubtreeStream stream_simple_name = new RewriteRuleSubtreeStream(this.adaptor, "rule simple_name");
/*       */     try
/*       */     {
/*  7512 */       COLON178 = (Token)match(this.input, 30, FOLLOW_COLON_in_label_ref3082);
/*  7513 */       stream_COLON.add(COLON178);
/*       */ 
/*  7515 */       pushFollow(FOLLOW_simple_name_in_label_ref3084);
/*  7516 */       simple_name179 = simple_name();
/*  7517 */       this.state._fsp -= 1;
/*       */ 
/*  7519 */       stream_simple_name.add(simple_name179.getTree());
/*       */ 
/*  7527 */       retval.tree = root_0;
/*  7528 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7530 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7533 */       this.adaptor.addChild(root_0, stream_simple_name.nextTree());
/*       */ 
/*  7537 */       retval.tree = root_0;
/*       */ 
/*  7541 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  7543 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  7544 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  7548 */       reportError(re);
/*  7549 */       recover(this.input, re);
/*  7550 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  7555 */     return retval;
/*       */   }
/*       */ 
/*       */   public final register_list_return register_list()
/*       */     throws RecognitionException
/*       */   {
/*  7570 */     register_list_return retval = new register_list_return();
/*  7571 */     retval.start = this.input.LT(1);
/*       */ 
/*  7573 */     CommonTree root_0 = null;
/*       */ 
/*  7575 */     Token REGISTER180 = null;
/*  7576 */     Token COMMA181 = null;
/*  7577 */     Token REGISTER182 = null;
/*       */ 
/*  7579 */     CommonTree REGISTER180_tree = null;
/*  7580 */     CommonTree COMMA181_tree = null;
/*  7581 */     CommonTree REGISTER182_tree = null;
/*  7582 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/*  7583 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/*  7587 */       int alt28 = 2;
/*  7588 */       int LA28_0 = this.input.LA(1);
/*  7589 */       if (LA28_0 == 201) {
/*  7590 */         alt28 = 1;
/*       */       }
/*  7592 */       else if (LA28_0 == 28) {
/*  7593 */         alt28 = 2;
/*       */       }
/*       */       else
/*       */       {
/*  7597 */         NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/*  7602 */       switch (alt28)
/*       */       {
/*       */       case 1:
/*  7606 */         REGISTER180 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_register_list3098);
/*  7607 */         stream_REGISTER.add(REGISTER180);
/*       */         while (true)
/*       */         {
/*  7612 */           int alt27 = 2;
/*  7613 */           int LA27_0 = this.input.LA(1);
/*  7614 */           if (LA27_0 == 31) {
/*  7615 */             alt27 = 1;
/*       */           }
/*       */ 
/*  7618 */           switch (alt27)
/*       */           {
/*       */           case 1:
/*  7622 */             COMMA181 = (Token)match(this.input, 31, FOLLOW_COMMA_in_register_list3101);
/*  7623 */             stream_COMMA.add(COMMA181);
/*       */ 
/*  7625 */             REGISTER182 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_register_list3103);
/*  7626 */             stream_REGISTER.add(REGISTER182);
/*       */ 
/*  7629 */             break;
/*       */           default:
/*  7632 */             break label292;
/*       */           }
/*       */ 
/*       */         }
/*       */ 
/*  7643 */         retval.tree = root_0;
/*  7644 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7646 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7651 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  7652 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(141, retval.start, "I_REGISTER_LIST"), root_1);
/*       */ 
/*  7654 */         while (stream_REGISTER.hasNext()) {
/*  7655 */           this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/*       */         }
/*  7657 */         stream_REGISTER.reset();
/*       */ 
/*  7659 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  7665 */         retval.tree = root_0;
/*       */ 
/*  7668 */         break;
/*       */       case 2:
/*  7679 */         label292: retval.tree = root_0;
/*  7680 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7682 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7687 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  7688 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(141, retval.start, "I_REGISTER_LIST"), root_1);
/*  7689 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  7695 */         retval.tree = root_0;
/*       */       }
/*       */ 
/*  7701 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  7703 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  7704 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  7708 */       reportError(re);
/*  7709 */       recover(this.input, re);
/*  7710 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  7715 */     return retval;
/*       */   }
/*       */ 
/*       */   public final register_range_return register_range()
/*       */     throws RecognitionException
/*       */   {
/*  7730 */     register_range_return retval = new register_range_return();
/*  7731 */     retval.start = this.input.LT(1);
/*       */ 
/*  7733 */     CommonTree root_0 = null;
/*       */ 
/*  7735 */     Token startreg = null;
/*  7736 */     Token endreg = null;
/*  7737 */     Token DOTDOT183 = null;
/*       */ 
/*  7739 */     CommonTree startreg_tree = null;
/*  7740 */     CommonTree endreg_tree = null;
/*  7741 */     CommonTree DOTDOT183_tree = null;
/*  7742 */     RewriteRuleTokenStream stream_DOTDOT = new RewriteRuleTokenStream(this.adaptor, "token DOTDOT");
/*  7743 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/*  7750 */       int alt30 = 2;
/*  7751 */       int LA30_0 = this.input.LA(1);
/*  7752 */       if (LA30_0 == 201) {
/*  7753 */         alt30 = 1;
/*       */       }
/*  7755 */       switch (alt30)
/*       */       {
/*       */       case 1:
/*  7759 */         startreg = (Token)match(this.input, 201, FOLLOW_REGISTER_in_register_range3138);
/*  7760 */         stream_REGISTER.add(startreg);
/*       */ 
/*  7763 */         int alt29 = 2;
/*  7764 */         int LA29_0 = this.input.LA(1);
/*  7765 */         if (LA29_0 == 33) {
/*  7766 */           alt29 = 1;
/*       */         }
/*  7768 */         switch (alt29)
/*       */         {
/*       */         case 1:
/*  7772 */           DOTDOT183 = (Token)match(this.input, 33, FOLLOW_DOTDOT_in_register_range3141);
/*  7773 */           stream_DOTDOT.add(DOTDOT183);
/*       */ 
/*  7775 */           endreg = (Token)match(this.input, 201, FOLLOW_REGISTER_in_register_range3145);
/*  7776 */           stream_REGISTER.add(endreg);
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/*  7795 */       retval.tree = root_0;
/*  7796 */       RewriteRuleTokenStream stream_endreg = new RewriteRuleTokenStream(this.adaptor, "token endreg", endreg);
/*  7797 */       RewriteRuleTokenStream stream_startreg = new RewriteRuleTokenStream(this.adaptor, "token startreg", startreg);
/*  7798 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/*  7800 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/*  7805 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  7806 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(142, retval.start, "I_REGISTER_RANGE"), root_1);
/*       */ 
/*  7808 */       if (stream_startreg.hasNext()) {
/*  7809 */         this.adaptor.addChild(root_1, stream_startreg.nextNode());
/*       */       }
/*  7811 */       stream_startreg.reset();
/*       */ 
/*  7814 */       if (stream_endreg.hasNext()) {
/*  7815 */         this.adaptor.addChild(root_1, stream_endreg.nextNode());
/*       */       }
/*  7817 */       stream_endreg.reset();
/*       */ 
/*  7819 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/*  7825 */       retval.tree = root_0;
/*       */ 
/*  7829 */       retval.stop = this.input.LT(-1);
/*       */ 
/*  7831 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  7832 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/*  7836 */       reportError(re);
/*  7837 */       recover(this.input, re);
/*  7838 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/*  7843 */     return retval;
/*       */   }
/*       */ 
/*       */   public final verification_error_reference_return verification_error_reference()
/*       */     throws RecognitionException
/*       */   {
/*  7858 */     verification_error_reference_return retval = new verification_error_reference_return();
/*  7859 */     retval.start = this.input.LT(1);
/*       */ 
/*  7861 */     CommonTree root_0 = null;
/*       */ 
/*  7863 */     Token CLASS_DESCRIPTOR184 = null;
/*  7864 */     ParserRuleReturnScope fully_qualified_field185 = null;
/*  7865 */     ParserRuleReturnScope fully_qualified_method186 = null;
/*       */ 
/*  7867 */     CommonTree CLASS_DESCRIPTOR184_tree = null;
/*       */     try
/*       */     {
/*  7871 */       int alt31 = 3;
/*  7872 */       int LA31_0 = this.input.LA(1);
/*  7873 */       if (LA31_0 == 26) {
/*  7874 */         int LA31_1 = this.input.LA(2);
/*  7875 */         if ((LA31_1 == 5) || (LA31_1 == 7) || ((LA31_1 >= 23) && (LA31_1 <= 24)) || (LA31_1 == 30) || ((LA31_1 >= 39) && (LA31_1 <= 40)) || (LA31_1 == 46) || ((LA31_1 >= 58) && (LA31_1 <= 101)) || ((LA31_1 >= 184) && (LA31_1 <= 186)) || ((LA31_1 >= 194) && (LA31_1 <= 195)) || (LA31_1 == 200) || ((LA31_1 >= 202) && (LA31_1 <= 203)) || ((LA31_1 >= 206) && (LA31_1 <= 207))) {
/*  7876 */           alt31 = 1;
/*       */         }
/*  7878 */         else if (LA31_1 == 9) {
/*  7879 */           switch (this.input.LA(3))
/*       */           {
/*       */           case 205:
/*  7882 */             int LA31_5 = this.input.LA(4);
/*  7883 */             if (LA31_5 == 30) {
/*  7884 */               alt31 = 2;
/*       */             }
/*  7886 */             else if (LA31_5 == 193) {
/*  7887 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  7891 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  7893 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  7894 */                   this.input.consume();
/*       */                 }
/*  7896 */                 NoViableAltException nvae = new NoViableAltException("", 31, 5, this.input);
/*       */ 
/*  7898 */                 throw nvae;
/*       */               } finally {
/*  7900 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  7905 */             break;
/*       */           case 4:
/*  7908 */             int LA31_6 = this.input.LA(4);
/*  7909 */             if (LA31_6 == 30) {
/*  7910 */               alt31 = 2;
/*       */             }
/*  7912 */             else if (LA31_6 == 193) {
/*  7913 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  7917 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  7919 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  7920 */                   this.input.consume();
/*       */                 }
/*  7922 */                 NoViableAltException nvae = new NoViableAltException("", 31, 6, this.input);
/*       */ 
/*  7924 */                 throw nvae;
/*       */               } finally {
/*  7926 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  7931 */             break;
/*       */           case 211:
/*  7934 */             int LA31_7 = this.input.LA(4);
/*  7935 */             if (LA31_7 == 30) {
/*  7936 */               alt31 = 2;
/*       */             }
/*  7938 */             else if (LA31_7 == 193) {
/*  7939 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  7943 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  7945 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  7946 */                   this.input.consume();
/*       */                 }
/*  7948 */                 NoViableAltException nvae = new NoViableAltException("", 31, 7, this.input);
/*       */ 
/*  7950 */                 throw nvae;
/*       */               } finally {
/*  7952 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  7957 */             break;
/*       */           case 198:
/*  7960 */             int LA31_8 = this.input.LA(4);
/*  7961 */             if (LA31_8 == 30) {
/*  7962 */               alt31 = 2;
/*       */             }
/*  7964 */             else if (LA31_8 == 193) {
/*  7965 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  7969 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  7971 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  7972 */                   this.input.consume();
/*       */                 }
/*  7974 */                 NoViableAltException nvae = new NoViableAltException("", 31, 8, this.input);
/*       */ 
/*  7976 */                 throw nvae;
/*       */               } finally {
/*  7978 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  7983 */             break;
/*       */           case 190:
/*  7986 */             int LA31_9 = this.input.LA(4);
/*  7987 */             if (LA31_9 == 30) {
/*  7988 */               alt31 = 2;
/*       */             }
/*  7990 */             else if (LA31_9 == 193) {
/*  7991 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  7995 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  7997 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  7998 */                   this.input.consume();
/*       */                 }
/*  8000 */                 NoViableAltException nvae = new NoViableAltException("", 31, 9, this.input);
/*       */ 
/*  8002 */                 throw nvae;
/*       */               } finally {
/*  8004 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8009 */             break;
/*       */           case 52:
/*  8012 */             int LA31_10 = this.input.LA(4);
/*  8013 */             if (LA31_10 == 30) {
/*  8014 */               alt31 = 2;
/*       */             }
/*  8016 */             else if (LA31_10 == 193) {
/*  8017 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8021 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8023 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8024 */                   this.input.consume();
/*       */                 }
/*  8026 */                 NoViableAltException nvae = new NoViableAltException("", 31, 10, this.input);
/*       */ 
/*  8028 */                 throw nvae;
/*       */               } finally {
/*  8030 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8035 */             break;
/*       */           case 35:
/*  8038 */             int LA31_11 = this.input.LA(4);
/*  8039 */             if (LA31_11 == 30) {
/*  8040 */               alt31 = 2;
/*       */             }
/*  8042 */             else if (LA31_11 == 193) {
/*  8043 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8047 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8049 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8050 */                   this.input.consume();
/*       */                 }
/*  8052 */                 NoViableAltException nvae = new NoViableAltException("", 31, 11, this.input);
/*       */ 
/*  8054 */                 throw nvae;
/*       */               } finally {
/*  8056 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8061 */             break;
/*       */           case 21:
/*  8064 */             int LA31_12 = this.input.LA(4);
/*  8065 */             if (LA31_12 == 30) {
/*  8066 */               alt31 = 2;
/*       */             }
/*  8068 */             else if (LA31_12 == 193) {
/*  8069 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8073 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8075 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8076 */                   this.input.consume();
/*       */                 }
/*  8078 */                 NoViableAltException nvae = new NoViableAltException("", 31, 12, this.input);
/*       */ 
/*  8080 */                 throw nvae;
/*       */               } finally {
/*  8082 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8087 */             break;
/*       */           case 191:
/*  8090 */             int LA31_13 = this.input.LA(4);
/*  8091 */             if (LA31_13 == 30) {
/*  8092 */               alt31 = 2;
/*       */             }
/*  8094 */             else if (LA31_13 == 193) {
/*  8095 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8099 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8101 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8102 */                   this.input.consume();
/*       */                 }
/*  8104 */                 NoViableAltException nvae = new NoViableAltException("", 31, 13, this.input);
/*       */ 
/*  8106 */                 throw nvae;
/*       */               } finally {
/*  8108 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8113 */             break;
/*       */           case 201:
/*  8116 */             int LA31_14 = this.input.LA(4);
/*  8117 */             if (LA31_14 == 30) {
/*  8118 */               alt31 = 2;
/*       */             }
/*  8120 */             else if (LA31_14 == 193) {
/*  8121 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8125 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8127 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8128 */                   this.input.consume();
/*       */                 }
/*  8130 */                 NoViableAltException nvae = new NoViableAltException("", 31, 14, this.input);
/*       */ 
/*  8132 */                 throw nvae;
/*       */               } finally {
/*  8134 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8139 */             break;
/*       */           case 197:
/*  8142 */             int LA31_15 = this.input.LA(4);
/*  8143 */             if (LA31_15 == 30) {
/*  8144 */               alt31 = 2;
/*       */             }
/*  8146 */             else if (LA31_15 == 193) {
/*  8147 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8151 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8153 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8154 */                   this.input.consume();
/*       */                 }
/*  8156 */                 NoViableAltException nvae = new NoViableAltException("", 31, 15, this.input);
/*       */ 
/*  8158 */                 throw nvae;
/*       */               } finally {
/*  8160 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8165 */             break;
/*       */           case 199:
/*  8168 */             int LA31_16 = this.input.LA(4);
/*  8169 */             if (LA31_16 == 30) {
/*  8170 */               alt31 = 2;
/*       */             }
/*  8172 */             else if (LA31_16 == 193) {
/*  8173 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8177 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8179 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8180 */                   this.input.consume();
/*       */                 }
/*  8182 */                 NoViableAltException nvae = new NoViableAltException("", 31, 16, this.input);
/*       */ 
/*  8184 */                 throw nvae;
/*       */               } finally {
/*  8186 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8191 */             break;
/*       */           case 212:
/*  8194 */             int LA31_17 = this.input.LA(4);
/*  8195 */             if (LA31_17 == 30) {
/*  8196 */               alt31 = 2;
/*       */             }
/*  8198 */             else if (LA31_17 == 193) {
/*  8199 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8203 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8205 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8206 */                   this.input.consume();
/*       */                 }
/*  8208 */                 NoViableAltException nvae = new NoViableAltException("", 31, 17, this.input);
/*       */ 
/*  8210 */                 throw nvae;
/*       */               } finally {
/*  8212 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8217 */             break;
/*       */           case 6:
/*  8220 */             int LA31_18 = this.input.LA(4);
/*  8221 */             if (LA31_18 == 30) {
/*  8222 */               alt31 = 2;
/*       */             }
/*  8224 */             else if (LA31_18 == 193) {
/*  8225 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8229 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8231 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8232 */                   this.input.consume();
/*       */                 }
/*  8234 */                 NoViableAltException nvae = new NoViableAltException("", 31, 18, this.input);
/*       */ 
/*  8236 */                 throw nvae;
/*       */               } finally {
/*  8238 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8243 */             break;
/*       */           case 58:
/*  8246 */             int LA31_19 = this.input.LA(4);
/*  8247 */             if (LA31_19 == 30) {
/*  8248 */               alt31 = 2;
/*       */             }
/*  8250 */             else if (LA31_19 == 193) {
/*  8251 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8255 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8257 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8258 */                   this.input.consume();
/*       */                 }
/*  8260 */                 NoViableAltException nvae = new NoViableAltException("", 31, 19, this.input);
/*       */ 
/*  8262 */                 throw nvae;
/*       */               } finally {
/*  8264 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8269 */             break;
/*       */           case 59:
/*  8272 */             int LA31_20 = this.input.LA(4);
/*  8273 */             if (LA31_20 == 30) {
/*  8274 */               alt31 = 2;
/*       */             }
/*  8276 */             else if (LA31_20 == 193) {
/*  8277 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8281 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8283 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8284 */                   this.input.consume();
/*       */                 }
/*  8286 */                 NoViableAltException nvae = new NoViableAltException("", 31, 20, this.input);
/*       */ 
/*  8288 */                 throw nvae;
/*       */               } finally {
/*  8290 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8295 */             break;
/*       */           case 60:
/*  8298 */             int LA31_21 = this.input.LA(4);
/*  8299 */             if (LA31_21 == 30) {
/*  8300 */               alt31 = 2;
/*       */             }
/*  8302 */             else if (LA31_21 == 193) {
/*  8303 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8307 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8309 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8310 */                   this.input.consume();
/*       */                 }
/*  8312 */                 NoViableAltException nvae = new NoViableAltException("", 31, 21, this.input);
/*       */ 
/*  8314 */                 throw nvae;
/*       */               } finally {
/*  8316 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8321 */             break;
/*       */           case 62:
/*  8324 */             int LA31_22 = this.input.LA(4);
/*  8325 */             if (LA31_22 == 30) {
/*  8326 */               alt31 = 2;
/*       */             }
/*  8328 */             else if (LA31_22 == 193) {
/*  8329 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8333 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8335 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8336 */                   this.input.consume();
/*       */                 }
/*  8338 */                 NoViableAltException nvae = new NoViableAltException("", 31, 22, this.input);
/*       */ 
/*  8340 */                 throw nvae;
/*       */               } finally {
/*  8342 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8347 */             break;
/*       */           case 64:
/*  8350 */             int LA31_23 = this.input.LA(4);
/*  8351 */             if (LA31_23 == 30) {
/*  8352 */               alt31 = 2;
/*       */             }
/*  8354 */             else if (LA31_23 == 193) {
/*  8355 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8359 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8361 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8362 */                   this.input.consume();
/*       */                 }
/*  8364 */                 NoViableAltException nvae = new NoViableAltException("", 31, 23, this.input);
/*       */ 
/*  8366 */                 throw nvae;
/*       */               } finally {
/*  8368 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8373 */             break;
/*       */           case 67:
/*  8376 */             int LA31_24 = this.input.LA(4);
/*  8377 */             if (LA31_24 == 30) {
/*  8378 */               alt31 = 2;
/*       */             }
/*  8380 */             else if (LA31_24 == 193) {
/*  8381 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8385 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8387 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8388 */                   this.input.consume();
/*       */                 }
/*  8390 */                 NoViableAltException nvae = new NoViableAltException("", 31, 24, this.input);
/*       */ 
/*  8392 */                 throw nvae;
/*       */               } finally {
/*  8394 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8399 */             break;
/*       */           case 68:
/*  8402 */             int LA31_25 = this.input.LA(4);
/*  8403 */             if (LA31_25 == 30) {
/*  8404 */               alt31 = 2;
/*       */             }
/*  8406 */             else if (LA31_25 == 193) {
/*  8407 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8411 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8413 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8414 */                   this.input.consume();
/*       */                 }
/*  8416 */                 NoViableAltException nvae = new NoViableAltException("", 31, 25, this.input);
/*       */ 
/*  8418 */                 throw nvae;
/*       */               } finally {
/*  8420 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8425 */             break;
/*       */           case 69:
/*  8428 */             int LA31_26 = this.input.LA(4);
/*  8429 */             if (LA31_26 == 30) {
/*  8430 */               alt31 = 2;
/*       */             }
/*  8432 */             else if (LA31_26 == 193) {
/*  8433 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8437 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8439 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8440 */                   this.input.consume();
/*       */                 }
/*  8442 */                 NoViableAltException nvae = new NoViableAltException("", 31, 26, this.input);
/*       */ 
/*  8444 */                 throw nvae;
/*       */               } finally {
/*  8446 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8451 */             break;
/*       */           case 70:
/*  8454 */             int LA31_27 = this.input.LA(4);
/*  8455 */             if (LA31_27 == 30) {
/*  8456 */               alt31 = 2;
/*       */             }
/*  8458 */             else if (LA31_27 == 193) {
/*  8459 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8463 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8465 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8466 */                   this.input.consume();
/*       */                 }
/*  8468 */                 NoViableAltException nvae = new NoViableAltException("", 31, 27, this.input);
/*       */ 
/*  8470 */                 throw nvae;
/*       */               } finally {
/*  8472 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8477 */             break;
/*       */           case 74:
/*  8480 */             int LA31_28 = this.input.LA(4);
/*  8481 */             if (LA31_28 == 30) {
/*  8482 */               alt31 = 2;
/*       */             }
/*  8484 */             else if (LA31_28 == 193) {
/*  8485 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8489 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8491 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8492 */                   this.input.consume();
/*       */                 }
/*  8494 */                 NoViableAltException nvae = new NoViableAltException("", 31, 28, this.input);
/*       */ 
/*  8496 */                 throw nvae;
/*       */               } finally {
/*  8498 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8503 */             break;
/*       */           case 76:
/*  8506 */             int LA31_29 = this.input.LA(4);
/*  8507 */             if (LA31_29 == 30) {
/*  8508 */               alt31 = 2;
/*       */             }
/*  8510 */             else if (LA31_29 == 193) {
/*  8511 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8515 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8517 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8518 */                   this.input.consume();
/*       */                 }
/*  8520 */                 NoViableAltException nvae = new NoViableAltException("", 31, 29, this.input);
/*       */ 
/*  8522 */                 throw nvae;
/*       */               } finally {
/*  8524 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8529 */             break;
/*       */           case 77:
/*  8532 */             int LA31_30 = this.input.LA(4);
/*  8533 */             if (LA31_30 == 30) {
/*  8534 */               alt31 = 2;
/*       */             }
/*  8536 */             else if (LA31_30 == 193) {
/*  8537 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8541 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8543 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8544 */                   this.input.consume();
/*       */                 }
/*  8546 */                 NoViableAltException nvae = new NoViableAltException("", 31, 30, this.input);
/*       */ 
/*  8548 */                 throw nvae;
/*       */               } finally {
/*  8550 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8555 */             break;
/*       */           case 78:
/*  8558 */             int LA31_31 = this.input.LA(4);
/*  8559 */             if (LA31_31 == 30) {
/*  8560 */               alt31 = 2;
/*       */             }
/*  8562 */             else if (LA31_31 == 193) {
/*  8563 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8567 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8569 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8570 */                   this.input.consume();
/*       */                 }
/*  8572 */                 NoViableAltException nvae = new NoViableAltException("", 31, 31, this.input);
/*       */ 
/*  8574 */                 throw nvae;
/*       */               } finally {
/*  8576 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8581 */             break;
/*       */           case 79:
/*  8584 */             int LA31_32 = this.input.LA(4);
/*  8585 */             if (LA31_32 == 30) {
/*  8586 */               alt31 = 2;
/*       */             }
/*  8588 */             else if (LA31_32 == 193) {
/*  8589 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8593 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8595 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8596 */                   this.input.consume();
/*       */                 }
/*  8598 */                 NoViableAltException nvae = new NoViableAltException("", 31, 32, this.input);
/*       */ 
/*  8600 */                 throw nvae;
/*       */               } finally {
/*  8602 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8607 */             break;
/*       */           case 81:
/*  8610 */             int LA31_33 = this.input.LA(4);
/*  8611 */             if (LA31_33 == 30) {
/*  8612 */               alt31 = 2;
/*       */             }
/*  8614 */             else if (LA31_33 == 193) {
/*  8615 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8619 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8621 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8622 */                   this.input.consume();
/*       */                 }
/*  8624 */                 NoViableAltException nvae = new NoViableAltException("", 31, 33, this.input);
/*       */ 
/*  8626 */                 throw nvae;
/*       */               } finally {
/*  8628 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8633 */             break;
/*       */           case 82:
/*  8636 */             int LA31_34 = this.input.LA(4);
/*  8637 */             if (LA31_34 == 30) {
/*  8638 */               alt31 = 2;
/*       */             }
/*  8640 */             else if (LA31_34 == 193) {
/*  8641 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8645 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8647 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8648 */                   this.input.consume();
/*       */                 }
/*  8650 */                 NoViableAltException nvae = new NoViableAltException("", 31, 34, this.input);
/*       */ 
/*  8652 */                 throw nvae;
/*       */               } finally {
/*  8654 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8659 */             break;
/*       */           case 84:
/*  8662 */             int LA31_35 = this.input.LA(4);
/*  8663 */             if (LA31_35 == 30) {
/*  8664 */               alt31 = 2;
/*       */             }
/*  8666 */             else if (LA31_35 == 193) {
/*  8667 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8671 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8673 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8674 */                   this.input.consume();
/*       */                 }
/*  8676 */                 NoViableAltException nvae = new NoViableAltException("", 31, 35, this.input);
/*       */ 
/*  8678 */                 throw nvae;
/*       */               } finally {
/*  8680 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8685 */             break;
/*       */           case 88:
/*  8688 */             int LA31_36 = this.input.LA(4);
/*  8689 */             if (LA31_36 == 30) {
/*  8690 */               alt31 = 2;
/*       */             }
/*  8692 */             else if (LA31_36 == 193) {
/*  8693 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8697 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8699 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8700 */                   this.input.consume();
/*       */                 }
/*  8702 */                 NoViableAltException nvae = new NoViableAltException("", 31, 36, this.input);
/*       */ 
/*  8704 */                 throw nvae;
/*       */               } finally {
/*  8706 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8711 */             break;
/*       */           case 89:
/*  8714 */             int LA31_37 = this.input.LA(4);
/*  8715 */             if (LA31_37 == 30) {
/*  8716 */               alt31 = 2;
/*       */             }
/*  8718 */             else if (LA31_37 == 193) {
/*  8719 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8723 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8725 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8726 */                   this.input.consume();
/*       */                 }
/*  8728 */                 NoViableAltException nvae = new NoViableAltException("", 31, 37, this.input);
/*       */ 
/*  8730 */                 throw nvae;
/*       */               } finally {
/*  8732 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8737 */             break;
/*       */           case 91:
/*  8740 */             int LA31_38 = this.input.LA(4);
/*  8741 */             if (LA31_38 == 30) {
/*  8742 */               alt31 = 2;
/*       */             }
/*  8744 */             else if (LA31_38 == 193) {
/*  8745 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8749 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8751 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8752 */                   this.input.consume();
/*       */                 }
/*  8754 */                 NoViableAltException nvae = new NoViableAltException("", 31, 38, this.input);
/*       */ 
/*  8756 */                 throw nvae;
/*       */               } finally {
/*  8758 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8763 */             break;
/*       */           case 92:
/*  8766 */             int LA31_39 = this.input.LA(4);
/*  8767 */             if (LA31_39 == 30) {
/*  8768 */               alt31 = 2;
/*       */             }
/*  8770 */             else if (LA31_39 == 193) {
/*  8771 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8775 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8777 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8778 */                   this.input.consume();
/*       */                 }
/*  8780 */                 NoViableAltException nvae = new NoViableAltException("", 31, 39, this.input);
/*       */ 
/*  8782 */                 throw nvae;
/*       */               } finally {
/*  8784 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8789 */             break;
/*       */           case 93:
/*  8792 */             int LA31_40 = this.input.LA(4);
/*  8793 */             if (LA31_40 == 30) {
/*  8794 */               alt31 = 2;
/*       */             }
/*  8796 */             else if (LA31_40 == 193) {
/*  8797 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8801 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8803 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8804 */                   this.input.consume();
/*       */                 }
/*  8806 */                 NoViableAltException nvae = new NoViableAltException("", 31, 40, this.input);
/*       */ 
/*  8808 */                 throw nvae;
/*       */               } finally {
/*  8810 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8815 */             break;
/*       */           case 94:
/*  8818 */             int LA31_41 = this.input.LA(4);
/*  8819 */             if (LA31_41 == 30) {
/*  8820 */               alt31 = 2;
/*       */             }
/*  8822 */             else if (LA31_41 == 193) {
/*  8823 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8827 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8829 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8830 */                   this.input.consume();
/*       */                 }
/*  8832 */                 NoViableAltException nvae = new NoViableAltException("", 31, 41, this.input);
/*       */ 
/*  8834 */                 throw nvae;
/*       */               } finally {
/*  8836 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8841 */             break;
/*       */           case 95:
/*  8844 */             int LA31_42 = this.input.LA(4);
/*  8845 */             if (LA31_42 == 30) {
/*  8846 */               alt31 = 2;
/*       */             }
/*  8848 */             else if (LA31_42 == 193) {
/*  8849 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8853 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8855 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8856 */                   this.input.consume();
/*       */                 }
/*  8858 */                 NoViableAltException nvae = new NoViableAltException("", 31, 42, this.input);
/*       */ 
/*  8860 */                 throw nvae;
/*       */               } finally {
/*  8862 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8867 */             break;
/*       */           case 101:
/*  8870 */             int LA31_43 = this.input.LA(4);
/*  8871 */             if (LA31_43 == 30) {
/*  8872 */               alt31 = 2;
/*       */             }
/*  8874 */             else if (LA31_43 == 193) {
/*  8875 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8879 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8881 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8882 */                   this.input.consume();
/*       */                 }
/*  8884 */                 NoViableAltException nvae = new NoViableAltException("", 31, 43, this.input);
/*       */ 
/*  8886 */                 throw nvae;
/*       */               } finally {
/*  8888 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8893 */             break;
/*       */           case 188:
/*  8896 */             int LA31_44 = this.input.LA(4);
/*  8897 */             if (LA31_44 == 30) {
/*  8898 */               alt31 = 2;
/*       */             }
/*  8900 */             else if (LA31_44 == 193) {
/*  8901 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8905 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8907 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8908 */                   this.input.consume();
/*       */                 }
/*  8910 */                 NoViableAltException nvae = new NoViableAltException("", 31, 44, this.input);
/*       */ 
/*  8912 */                 throw nvae;
/*       */               } finally {
/*  8914 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8919 */             break;
/*       */           default:
/*  8921 */             int nvaeMark = this.input.mark();
/*       */             try {
/*  8923 */               for (int nvaeConsume = 0; nvaeConsume < 2; nvaeConsume++) {
/*  8924 */                 this.input.consume();
/*       */               }
/*  8926 */               NoViableAltException nvae = new NoViableAltException("", 31, 4, this.input);
/*       */ 
/*  8928 */               throw nvae;
/*       */             } finally {
/*  8930 */               this.input.rewind(nvaeMark);
/*       */             }
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/*  8936 */           int nvaeMark = this.input.mark();
/*       */           try {
/*  8938 */             this.input.consume();
/*  8939 */             NoViableAltException nvae = new NoViableAltException("", 31, 1, this.input);
/*       */ 
/*  8941 */             throw nvae;
/*       */           } finally {
/*  8943 */             this.input.rewind(nvaeMark);
/*       */           }
/*       */         }
/*       */ 
/*       */       }
/*  8948 */       else if (LA31_0 == 8) {
/*  8949 */         int LA31_2 = this.input.LA(2);
/*  8950 */         if (LA31_2 == 9) {
/*  8951 */           switch (this.input.LA(3))
/*       */           {
/*       */           case 205:
/*  8954 */             int LA31_5 = this.input.LA(4);
/*  8955 */             if (LA31_5 == 30) {
/*  8956 */               alt31 = 2;
/*       */             }
/*  8958 */             else if (LA31_5 == 193) {
/*  8959 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8963 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8965 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8966 */                   this.input.consume();
/*       */                 }
/*  8968 */                 NoViableAltException nvae = new NoViableAltException("", 31, 5, this.input);
/*       */ 
/*  8970 */                 throw nvae;
/*       */               } finally {
/*  8972 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  8977 */             break;
/*       */           case 4:
/*  8980 */             int LA31_6 = this.input.LA(4);
/*  8981 */             if (LA31_6 == 30) {
/*  8982 */               alt31 = 2;
/*       */             }
/*  8984 */             else if (LA31_6 == 193) {
/*  8985 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  8989 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  8991 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  8992 */                   this.input.consume();
/*       */                 }
/*  8994 */                 NoViableAltException nvae = new NoViableAltException("", 31, 6, this.input);
/*       */ 
/*  8996 */                 throw nvae;
/*       */               } finally {
/*  8998 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9003 */             break;
/*       */           case 211:
/*  9006 */             int LA31_7 = this.input.LA(4);
/*  9007 */             if (LA31_7 == 30) {
/*  9008 */               alt31 = 2;
/*       */             }
/*  9010 */             else if (LA31_7 == 193) {
/*  9011 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9015 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9017 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9018 */                   this.input.consume();
/*       */                 }
/*  9020 */                 NoViableAltException nvae = new NoViableAltException("", 31, 7, this.input);
/*       */ 
/*  9022 */                 throw nvae;
/*       */               } finally {
/*  9024 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9029 */             break;
/*       */           case 198:
/*  9032 */             int LA31_8 = this.input.LA(4);
/*  9033 */             if (LA31_8 == 30) {
/*  9034 */               alt31 = 2;
/*       */             }
/*  9036 */             else if (LA31_8 == 193) {
/*  9037 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9041 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9043 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9044 */                   this.input.consume();
/*       */                 }
/*  9046 */                 NoViableAltException nvae = new NoViableAltException("", 31, 8, this.input);
/*       */ 
/*  9048 */                 throw nvae;
/*       */               } finally {
/*  9050 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9055 */             break;
/*       */           case 190:
/*  9058 */             int LA31_9 = this.input.LA(4);
/*  9059 */             if (LA31_9 == 30) {
/*  9060 */               alt31 = 2;
/*       */             }
/*  9062 */             else if (LA31_9 == 193) {
/*  9063 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9067 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9069 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9070 */                   this.input.consume();
/*       */                 }
/*  9072 */                 NoViableAltException nvae = new NoViableAltException("", 31, 9, this.input);
/*       */ 
/*  9074 */                 throw nvae;
/*       */               } finally {
/*  9076 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9081 */             break;
/*       */           case 52:
/*  9084 */             int LA31_10 = this.input.LA(4);
/*  9085 */             if (LA31_10 == 30) {
/*  9086 */               alt31 = 2;
/*       */             }
/*  9088 */             else if (LA31_10 == 193) {
/*  9089 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9093 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9095 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9096 */                   this.input.consume();
/*       */                 }
/*  9098 */                 NoViableAltException nvae = new NoViableAltException("", 31, 10, this.input);
/*       */ 
/*  9100 */                 throw nvae;
/*       */               } finally {
/*  9102 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9107 */             break;
/*       */           case 35:
/*  9110 */             int LA31_11 = this.input.LA(4);
/*  9111 */             if (LA31_11 == 30) {
/*  9112 */               alt31 = 2;
/*       */             }
/*  9114 */             else if (LA31_11 == 193) {
/*  9115 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9119 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9121 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9122 */                   this.input.consume();
/*       */                 }
/*  9124 */                 NoViableAltException nvae = new NoViableAltException("", 31, 11, this.input);
/*       */ 
/*  9126 */                 throw nvae;
/*       */               } finally {
/*  9128 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9133 */             break;
/*       */           case 21:
/*  9136 */             int LA31_12 = this.input.LA(4);
/*  9137 */             if (LA31_12 == 30) {
/*  9138 */               alt31 = 2;
/*       */             }
/*  9140 */             else if (LA31_12 == 193) {
/*  9141 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9145 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9147 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9148 */                   this.input.consume();
/*       */                 }
/*  9150 */                 NoViableAltException nvae = new NoViableAltException("", 31, 12, this.input);
/*       */ 
/*  9152 */                 throw nvae;
/*       */               } finally {
/*  9154 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9159 */             break;
/*       */           case 191:
/*  9162 */             int LA31_13 = this.input.LA(4);
/*  9163 */             if (LA31_13 == 30) {
/*  9164 */               alt31 = 2;
/*       */             }
/*  9166 */             else if (LA31_13 == 193) {
/*  9167 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9171 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9173 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9174 */                   this.input.consume();
/*       */                 }
/*  9176 */                 NoViableAltException nvae = new NoViableAltException("", 31, 13, this.input);
/*       */ 
/*  9178 */                 throw nvae;
/*       */               } finally {
/*  9180 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9185 */             break;
/*       */           case 201:
/*  9188 */             int LA31_14 = this.input.LA(4);
/*  9189 */             if (LA31_14 == 30) {
/*  9190 */               alt31 = 2;
/*       */             }
/*  9192 */             else if (LA31_14 == 193) {
/*  9193 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9197 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9199 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9200 */                   this.input.consume();
/*       */                 }
/*  9202 */                 NoViableAltException nvae = new NoViableAltException("", 31, 14, this.input);
/*       */ 
/*  9204 */                 throw nvae;
/*       */               } finally {
/*  9206 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9211 */             break;
/*       */           case 197:
/*  9214 */             int LA31_15 = this.input.LA(4);
/*  9215 */             if (LA31_15 == 30) {
/*  9216 */               alt31 = 2;
/*       */             }
/*  9218 */             else if (LA31_15 == 193) {
/*  9219 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9223 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9225 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9226 */                   this.input.consume();
/*       */                 }
/*  9228 */                 NoViableAltException nvae = new NoViableAltException("", 31, 15, this.input);
/*       */ 
/*  9230 */                 throw nvae;
/*       */               } finally {
/*  9232 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9237 */             break;
/*       */           case 199:
/*  9240 */             int LA31_16 = this.input.LA(4);
/*  9241 */             if (LA31_16 == 30) {
/*  9242 */               alt31 = 2;
/*       */             }
/*  9244 */             else if (LA31_16 == 193) {
/*  9245 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9249 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9251 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9252 */                   this.input.consume();
/*       */                 }
/*  9254 */                 NoViableAltException nvae = new NoViableAltException("", 31, 16, this.input);
/*       */ 
/*  9256 */                 throw nvae;
/*       */               } finally {
/*  9258 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9263 */             break;
/*       */           case 212:
/*  9266 */             int LA31_17 = this.input.LA(4);
/*  9267 */             if (LA31_17 == 30) {
/*  9268 */               alt31 = 2;
/*       */             }
/*  9270 */             else if (LA31_17 == 193) {
/*  9271 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9275 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9277 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9278 */                   this.input.consume();
/*       */                 }
/*  9280 */                 NoViableAltException nvae = new NoViableAltException("", 31, 17, this.input);
/*       */ 
/*  9282 */                 throw nvae;
/*       */               } finally {
/*  9284 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9289 */             break;
/*       */           case 6:
/*  9292 */             int LA31_18 = this.input.LA(4);
/*  9293 */             if (LA31_18 == 30) {
/*  9294 */               alt31 = 2;
/*       */             }
/*  9296 */             else if (LA31_18 == 193) {
/*  9297 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9301 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9303 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9304 */                   this.input.consume();
/*       */                 }
/*  9306 */                 NoViableAltException nvae = new NoViableAltException("", 31, 18, this.input);
/*       */ 
/*  9308 */                 throw nvae;
/*       */               } finally {
/*  9310 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9315 */             break;
/*       */           case 58:
/*  9318 */             int LA31_19 = this.input.LA(4);
/*  9319 */             if (LA31_19 == 30) {
/*  9320 */               alt31 = 2;
/*       */             }
/*  9322 */             else if (LA31_19 == 193) {
/*  9323 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9327 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9329 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9330 */                   this.input.consume();
/*       */                 }
/*  9332 */                 NoViableAltException nvae = new NoViableAltException("", 31, 19, this.input);
/*       */ 
/*  9334 */                 throw nvae;
/*       */               } finally {
/*  9336 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9341 */             break;
/*       */           case 59:
/*  9344 */             int LA31_20 = this.input.LA(4);
/*  9345 */             if (LA31_20 == 30) {
/*  9346 */               alt31 = 2;
/*       */             }
/*  9348 */             else if (LA31_20 == 193) {
/*  9349 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9353 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9355 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9356 */                   this.input.consume();
/*       */                 }
/*  9358 */                 NoViableAltException nvae = new NoViableAltException("", 31, 20, this.input);
/*       */ 
/*  9360 */                 throw nvae;
/*       */               } finally {
/*  9362 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9367 */             break;
/*       */           case 60:
/*  9370 */             int LA31_21 = this.input.LA(4);
/*  9371 */             if (LA31_21 == 30) {
/*  9372 */               alt31 = 2;
/*       */             }
/*  9374 */             else if (LA31_21 == 193) {
/*  9375 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9379 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9381 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9382 */                   this.input.consume();
/*       */                 }
/*  9384 */                 NoViableAltException nvae = new NoViableAltException("", 31, 21, this.input);
/*       */ 
/*  9386 */                 throw nvae;
/*       */               } finally {
/*  9388 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9393 */             break;
/*       */           case 62:
/*  9396 */             int LA31_22 = this.input.LA(4);
/*  9397 */             if (LA31_22 == 30) {
/*  9398 */               alt31 = 2;
/*       */             }
/*  9400 */             else if (LA31_22 == 193) {
/*  9401 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9405 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9407 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9408 */                   this.input.consume();
/*       */                 }
/*  9410 */                 NoViableAltException nvae = new NoViableAltException("", 31, 22, this.input);
/*       */ 
/*  9412 */                 throw nvae;
/*       */               } finally {
/*  9414 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9419 */             break;
/*       */           case 64:
/*  9422 */             int LA31_23 = this.input.LA(4);
/*  9423 */             if (LA31_23 == 30) {
/*  9424 */               alt31 = 2;
/*       */             }
/*  9426 */             else if (LA31_23 == 193) {
/*  9427 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9431 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9433 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9434 */                   this.input.consume();
/*       */                 }
/*  9436 */                 NoViableAltException nvae = new NoViableAltException("", 31, 23, this.input);
/*       */ 
/*  9438 */                 throw nvae;
/*       */               } finally {
/*  9440 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9445 */             break;
/*       */           case 67:
/*  9448 */             int LA31_24 = this.input.LA(4);
/*  9449 */             if (LA31_24 == 30) {
/*  9450 */               alt31 = 2;
/*       */             }
/*  9452 */             else if (LA31_24 == 193) {
/*  9453 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9457 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9459 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9460 */                   this.input.consume();
/*       */                 }
/*  9462 */                 NoViableAltException nvae = new NoViableAltException("", 31, 24, this.input);
/*       */ 
/*  9464 */                 throw nvae;
/*       */               } finally {
/*  9466 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9471 */             break;
/*       */           case 68:
/*  9474 */             int LA31_25 = this.input.LA(4);
/*  9475 */             if (LA31_25 == 30) {
/*  9476 */               alt31 = 2;
/*       */             }
/*  9478 */             else if (LA31_25 == 193) {
/*  9479 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9483 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9485 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9486 */                   this.input.consume();
/*       */                 }
/*  9488 */                 NoViableAltException nvae = new NoViableAltException("", 31, 25, this.input);
/*       */ 
/*  9490 */                 throw nvae;
/*       */               } finally {
/*  9492 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9497 */             break;
/*       */           case 69:
/*  9500 */             int LA31_26 = this.input.LA(4);
/*  9501 */             if (LA31_26 == 30) {
/*  9502 */               alt31 = 2;
/*       */             }
/*  9504 */             else if (LA31_26 == 193) {
/*  9505 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9509 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9511 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9512 */                   this.input.consume();
/*       */                 }
/*  9514 */                 NoViableAltException nvae = new NoViableAltException("", 31, 26, this.input);
/*       */ 
/*  9516 */                 throw nvae;
/*       */               } finally {
/*  9518 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9523 */             break;
/*       */           case 70:
/*  9526 */             int LA31_27 = this.input.LA(4);
/*  9527 */             if (LA31_27 == 30) {
/*  9528 */               alt31 = 2;
/*       */             }
/*  9530 */             else if (LA31_27 == 193) {
/*  9531 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9535 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9537 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9538 */                   this.input.consume();
/*       */                 }
/*  9540 */                 NoViableAltException nvae = new NoViableAltException("", 31, 27, this.input);
/*       */ 
/*  9542 */                 throw nvae;
/*       */               } finally {
/*  9544 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9549 */             break;
/*       */           case 74:
/*  9552 */             int LA31_28 = this.input.LA(4);
/*  9553 */             if (LA31_28 == 30) {
/*  9554 */               alt31 = 2;
/*       */             }
/*  9556 */             else if (LA31_28 == 193) {
/*  9557 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9561 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9563 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9564 */                   this.input.consume();
/*       */                 }
/*  9566 */                 NoViableAltException nvae = new NoViableAltException("", 31, 28, this.input);
/*       */ 
/*  9568 */                 throw nvae;
/*       */               } finally {
/*  9570 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9575 */             break;
/*       */           case 76:
/*  9578 */             int LA31_29 = this.input.LA(4);
/*  9579 */             if (LA31_29 == 30) {
/*  9580 */               alt31 = 2;
/*       */             }
/*  9582 */             else if (LA31_29 == 193) {
/*  9583 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9587 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9589 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9590 */                   this.input.consume();
/*       */                 }
/*  9592 */                 NoViableAltException nvae = new NoViableAltException("", 31, 29, this.input);
/*       */ 
/*  9594 */                 throw nvae;
/*       */               } finally {
/*  9596 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9601 */             break;
/*       */           case 77:
/*  9604 */             int LA31_30 = this.input.LA(4);
/*  9605 */             if (LA31_30 == 30) {
/*  9606 */               alt31 = 2;
/*       */             }
/*  9608 */             else if (LA31_30 == 193) {
/*  9609 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9613 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9615 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9616 */                   this.input.consume();
/*       */                 }
/*  9618 */                 NoViableAltException nvae = new NoViableAltException("", 31, 30, this.input);
/*       */ 
/*  9620 */                 throw nvae;
/*       */               } finally {
/*  9622 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9627 */             break;
/*       */           case 78:
/*  9630 */             int LA31_31 = this.input.LA(4);
/*  9631 */             if (LA31_31 == 30) {
/*  9632 */               alt31 = 2;
/*       */             }
/*  9634 */             else if (LA31_31 == 193) {
/*  9635 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9639 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9641 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9642 */                   this.input.consume();
/*       */                 }
/*  9644 */                 NoViableAltException nvae = new NoViableAltException("", 31, 31, this.input);
/*       */ 
/*  9646 */                 throw nvae;
/*       */               } finally {
/*  9648 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9653 */             break;
/*       */           case 79:
/*  9656 */             int LA31_32 = this.input.LA(4);
/*  9657 */             if (LA31_32 == 30) {
/*  9658 */               alt31 = 2;
/*       */             }
/*  9660 */             else if (LA31_32 == 193) {
/*  9661 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9665 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9667 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9668 */                   this.input.consume();
/*       */                 }
/*  9670 */                 NoViableAltException nvae = new NoViableAltException("", 31, 32, this.input);
/*       */ 
/*  9672 */                 throw nvae;
/*       */               } finally {
/*  9674 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9679 */             break;
/*       */           case 81:
/*  9682 */             int LA31_33 = this.input.LA(4);
/*  9683 */             if (LA31_33 == 30) {
/*  9684 */               alt31 = 2;
/*       */             }
/*  9686 */             else if (LA31_33 == 193) {
/*  9687 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9691 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9693 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9694 */                   this.input.consume();
/*       */                 }
/*  9696 */                 NoViableAltException nvae = new NoViableAltException("", 31, 33, this.input);
/*       */ 
/*  9698 */                 throw nvae;
/*       */               } finally {
/*  9700 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9705 */             break;
/*       */           case 82:
/*  9708 */             int LA31_34 = this.input.LA(4);
/*  9709 */             if (LA31_34 == 30) {
/*  9710 */               alt31 = 2;
/*       */             }
/*  9712 */             else if (LA31_34 == 193) {
/*  9713 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9717 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9719 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9720 */                   this.input.consume();
/*       */                 }
/*  9722 */                 NoViableAltException nvae = new NoViableAltException("", 31, 34, this.input);
/*       */ 
/*  9724 */                 throw nvae;
/*       */               } finally {
/*  9726 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9731 */             break;
/*       */           case 84:
/*  9734 */             int LA31_35 = this.input.LA(4);
/*  9735 */             if (LA31_35 == 30) {
/*  9736 */               alt31 = 2;
/*       */             }
/*  9738 */             else if (LA31_35 == 193) {
/*  9739 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9743 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9745 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9746 */                   this.input.consume();
/*       */                 }
/*  9748 */                 NoViableAltException nvae = new NoViableAltException("", 31, 35, this.input);
/*       */ 
/*  9750 */                 throw nvae;
/*       */               } finally {
/*  9752 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9757 */             break;
/*       */           case 88:
/*  9760 */             int LA31_36 = this.input.LA(4);
/*  9761 */             if (LA31_36 == 30) {
/*  9762 */               alt31 = 2;
/*       */             }
/*  9764 */             else if (LA31_36 == 193) {
/*  9765 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9769 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9771 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9772 */                   this.input.consume();
/*       */                 }
/*  9774 */                 NoViableAltException nvae = new NoViableAltException("", 31, 36, this.input);
/*       */ 
/*  9776 */                 throw nvae;
/*       */               } finally {
/*  9778 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9783 */             break;
/*       */           case 89:
/*  9786 */             int LA31_37 = this.input.LA(4);
/*  9787 */             if (LA31_37 == 30) {
/*  9788 */               alt31 = 2;
/*       */             }
/*  9790 */             else if (LA31_37 == 193) {
/*  9791 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9795 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9797 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9798 */                   this.input.consume();
/*       */                 }
/*  9800 */                 NoViableAltException nvae = new NoViableAltException("", 31, 37, this.input);
/*       */ 
/*  9802 */                 throw nvae;
/*       */               } finally {
/*  9804 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9809 */             break;
/*       */           case 91:
/*  9812 */             int LA31_38 = this.input.LA(4);
/*  9813 */             if (LA31_38 == 30) {
/*  9814 */               alt31 = 2;
/*       */             }
/*  9816 */             else if (LA31_38 == 193) {
/*  9817 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9821 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9823 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9824 */                   this.input.consume();
/*       */                 }
/*  9826 */                 NoViableAltException nvae = new NoViableAltException("", 31, 38, this.input);
/*       */ 
/*  9828 */                 throw nvae;
/*       */               } finally {
/*  9830 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9835 */             break;
/*       */           case 92:
/*  9838 */             int LA31_39 = this.input.LA(4);
/*  9839 */             if (LA31_39 == 30) {
/*  9840 */               alt31 = 2;
/*       */             }
/*  9842 */             else if (LA31_39 == 193) {
/*  9843 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9847 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9849 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9850 */                   this.input.consume();
/*       */                 }
/*  9852 */                 NoViableAltException nvae = new NoViableAltException("", 31, 39, this.input);
/*       */ 
/*  9854 */                 throw nvae;
/*       */               } finally {
/*  9856 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9861 */             break;
/*       */           case 93:
/*  9864 */             int LA31_40 = this.input.LA(4);
/*  9865 */             if (LA31_40 == 30) {
/*  9866 */               alt31 = 2;
/*       */             }
/*  9868 */             else if (LA31_40 == 193) {
/*  9869 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9873 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9875 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9876 */                   this.input.consume();
/*       */                 }
/*  9878 */                 NoViableAltException nvae = new NoViableAltException("", 31, 40, this.input);
/*       */ 
/*  9880 */                 throw nvae;
/*       */               } finally {
/*  9882 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9887 */             break;
/*       */           case 94:
/*  9890 */             int LA31_41 = this.input.LA(4);
/*  9891 */             if (LA31_41 == 30) {
/*  9892 */               alt31 = 2;
/*       */             }
/*  9894 */             else if (LA31_41 == 193) {
/*  9895 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9899 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9901 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9902 */                   this.input.consume();
/*       */                 }
/*  9904 */                 NoViableAltException nvae = new NoViableAltException("", 31, 41, this.input);
/*       */ 
/*  9906 */                 throw nvae;
/*       */               } finally {
/*  9908 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9913 */             break;
/*       */           case 95:
/*  9916 */             int LA31_42 = this.input.LA(4);
/*  9917 */             if (LA31_42 == 30) {
/*  9918 */               alt31 = 2;
/*       */             }
/*  9920 */             else if (LA31_42 == 193) {
/*  9921 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9925 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9927 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9928 */                   this.input.consume();
/*       */                 }
/*  9930 */                 NoViableAltException nvae = new NoViableAltException("", 31, 42, this.input);
/*       */ 
/*  9932 */                 throw nvae;
/*       */               } finally {
/*  9934 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9939 */             break;
/*       */           case 101:
/*  9942 */             int LA31_43 = this.input.LA(4);
/*  9943 */             if (LA31_43 == 30) {
/*  9944 */               alt31 = 2;
/*       */             }
/*  9946 */             else if (LA31_43 == 193) {
/*  9947 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9951 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9953 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9954 */                   this.input.consume();
/*       */                 }
/*  9956 */                 NoViableAltException nvae = new NoViableAltException("", 31, 43, this.input);
/*       */ 
/*  9958 */                 throw nvae;
/*       */               } finally {
/*  9960 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9965 */             break;
/*       */           case 188:
/*  9968 */             int LA31_44 = this.input.LA(4);
/*  9969 */             if (LA31_44 == 30) {
/*  9970 */               alt31 = 2;
/*       */             }
/*  9972 */             else if (LA31_44 == 193) {
/*  9973 */               alt31 = 3;
/*       */             }
/*       */             else
/*       */             {
/*  9977 */               int nvaeMark = this.input.mark();
/*       */               try {
/*  9979 */                 for (int nvaeConsume = 0; nvaeConsume < 3; nvaeConsume++) {
/*  9980 */                   this.input.consume();
/*       */                 }
/*  9982 */                 NoViableAltException nvae = new NoViableAltException("", 31, 44, this.input);
/*       */ 
/*  9984 */                 throw nvae;
/*       */               } finally {
/*  9986 */                 this.input.rewind(nvaeMark);
/*       */               }
/*       */ 
/*       */             }
/*       */ 
/*  9991 */             break;
/*       */           default:
/*  9993 */             int nvaeMark = this.input.mark();
/*       */             try {
/*  9995 */               for (int nvaeConsume = 0; nvaeConsume < 2; nvaeConsume++) {
/*  9996 */                 this.input.consume();
/*       */               }
/*  9998 */               NoViableAltException nvae = new NoViableAltException("", 31, 4, this.input);
/*       */ 
/* 10000 */               throw nvae;
/*       */             } finally {
/* 10002 */               this.input.rewind(nvaeMark);
/*       */             }
/*       */           }
/*       */         }
/*       */         else
/*       */         {
/* 10008 */           int nvaeMark = this.input.mark();
/*       */           try {
/* 10010 */             this.input.consume();
/* 10011 */             NoViableAltException nvae = new NoViableAltException("", 31, 2, this.input);
/*       */ 
/* 10013 */             throw nvae;
/*       */           } finally {
/* 10015 */             this.input.rewind(nvaeMark);
/*       */           }
/*       */         }
/*       */ 
/*       */       }
/*       */       else
/*       */       {
/* 10022 */         NoViableAltException nvae = new NoViableAltException("", 31, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/* 10027 */       switch (alt31)
/*       */       {
/*       */       case 1:
/* 10031 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10034 */         CLASS_DESCRIPTOR184 = (Token)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3174);
/* 10035 */         CLASS_DESCRIPTOR184_tree = (CommonTree)this.adaptor.create(CLASS_DESCRIPTOR184);
/* 10036 */         this.adaptor.addChild(root_0, CLASS_DESCRIPTOR184_tree);
/*       */ 
/* 10039 */         break;
/*       */       case 2:
/* 10043 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10046 */         pushFollow(FOLLOW_fully_qualified_field_in_verification_error_reference3178);
/* 10047 */         fully_qualified_field185 = fully_qualified_field();
/* 10048 */         this.state._fsp -= 1;
/*       */ 
/* 10050 */         this.adaptor.addChild(root_0, fully_qualified_field185.getTree());
/*       */ 
/* 10053 */         break;
/*       */       case 3:
/* 10057 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10060 */         pushFollow(FOLLOW_fully_qualified_method_in_verification_error_reference3182);
/* 10061 */         fully_qualified_method186 = fully_qualified_method();
/* 10062 */         this.state._fsp -= 1;
/*       */ 
/* 10064 */         this.adaptor.addChild(root_0, fully_qualified_method186.getTree());
/*       */       }
/*       */ 
/* 10070 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 10072 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 10073 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 10077 */       reportError(re);
/* 10078 */       recover(this.input, re);
/* 10079 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 10084 */     return retval;
/*       */   }
/*       */ 
/*       */   public final catch_directive_return catch_directive()
/*       */     throws RecognitionException
/*       */   {
/* 10099 */     catch_directive_return retval = new catch_directive_return();
/* 10100 */     retval.start = this.input.LT(1);
/*       */ 
/* 10102 */     CommonTree root_0 = null;
/*       */ 
/* 10104 */     Token CATCH_DIRECTIVE187 = null;
/* 10105 */     Token OPEN_BRACE189 = null;
/* 10106 */     Token DOTDOT190 = null;
/* 10107 */     Token CLOSE_BRACE191 = null;
/* 10108 */     ParserRuleReturnScope from = null;
/* 10109 */     ParserRuleReturnScope to = null;
/* 10110 */     ParserRuleReturnScope using = null;
/* 10111 */     ParserRuleReturnScope nonvoid_type_descriptor188 = null;
/*       */ 
/* 10113 */     CommonTree CATCH_DIRECTIVE187_tree = null;
/* 10114 */     CommonTree OPEN_BRACE189_tree = null;
/* 10115 */     CommonTree DOTDOT190_tree = null;
/* 10116 */     CommonTree CLOSE_BRACE191_tree = null;
/* 10117 */     RewriteRuleTokenStream stream_DOTDOT = new RewriteRuleTokenStream(this.adaptor, "token DOTDOT");
/* 10118 */     RewriteRuleTokenStream stream_CLOSE_BRACE = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
/* 10119 */     RewriteRuleTokenStream stream_OPEN_BRACE = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
/* 10120 */     RewriteRuleTokenStream stream_CATCH_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token CATCH_DIRECTIVE");
/* 10121 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/* 10122 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 10128 */       CATCH_DIRECTIVE187 = (Token)match(this.input, 24, FOLLOW_CATCH_DIRECTIVE_in_catch_directive3192);
/* 10129 */       stream_CATCH_DIRECTIVE.add(CATCH_DIRECTIVE187);
/*       */ 
/* 10131 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_catch_directive3194);
/* 10132 */       nonvoid_type_descriptor188 = nonvoid_type_descriptor();
/* 10133 */       this.state._fsp -= 1;
/*       */ 
/* 10135 */       stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor188.getTree());
/* 10136 */       OPEN_BRACE189 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_catch_directive3196);
/* 10137 */       stream_OPEN_BRACE.add(OPEN_BRACE189);
/*       */ 
/* 10139 */       pushFollow(FOLLOW_label_ref_in_catch_directive3200);
/* 10140 */       from = label_ref();
/* 10141 */       this.state._fsp -= 1;
/*       */ 
/* 10143 */       stream_label_ref.add(from.getTree());
/* 10144 */       DOTDOT190 = (Token)match(this.input, 33, FOLLOW_DOTDOT_in_catch_directive3202);
/* 10145 */       stream_DOTDOT.add(DOTDOT190);
/*       */ 
/* 10147 */       pushFollow(FOLLOW_label_ref_in_catch_directive3206);
/* 10148 */       to = label_ref();
/* 10149 */       this.state._fsp -= 1;
/*       */ 
/* 10151 */       stream_label_ref.add(to.getTree());
/* 10152 */       CLOSE_BRACE191 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_catch_directive3208);
/* 10153 */       stream_CLOSE_BRACE.add(CLOSE_BRACE191);
/*       */ 
/* 10155 */       pushFollow(FOLLOW_label_ref_in_catch_directive3212);
/* 10156 */       using = label_ref();
/* 10157 */       this.state._fsp -= 1;
/*       */ 
/* 10159 */       stream_label_ref.add(using.getTree());
/*       */ 
/* 10167 */       retval.tree = root_0;
/* 10168 */       RewriteRuleSubtreeStream stream_to = new RewriteRuleSubtreeStream(this.adaptor, "rule to", to != null ? to.getTree() : null);
/* 10169 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/* 10170 */       RewriteRuleSubtreeStream stream_using = new RewriteRuleSubtreeStream(this.adaptor, "rule using", using != null ? using.getTree() : null);
/* 10171 */       RewriteRuleSubtreeStream stream_from = new RewriteRuleSubtreeStream(this.adaptor, "rule from", from != null ? from.getTree() : null);
/*       */ 
/* 10173 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10178 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 10179 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(110, retval.start, "I_CATCH"), root_1);
/* 10180 */       this.adaptor.addChild(root_1, stream_nonvoid_type_descriptor.nextTree());
/* 10181 */       this.adaptor.addChild(root_1, stream_from.nextTree());
/* 10182 */       this.adaptor.addChild(root_1, stream_to.nextTree());
/* 10183 */       this.adaptor.addChild(root_1, stream_using.nextTree());
/* 10184 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 10190 */       retval.tree = root_0;
/*       */ 
/* 10194 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 10196 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 10197 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 10201 */       reportError(re);
/* 10202 */       recover(this.input, re);
/* 10203 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 10208 */     return retval;
/*       */   }
/*       */ 
/*       */   public final catchall_directive_return catchall_directive()
/*       */     throws RecognitionException
/*       */   {
/* 10223 */     catchall_directive_return retval = new catchall_directive_return();
/* 10224 */     retval.start = this.input.LT(1);
/*       */ 
/* 10226 */     CommonTree root_0 = null;
/*       */ 
/* 10228 */     Token CATCHALL_DIRECTIVE192 = null;
/* 10229 */     Token OPEN_BRACE193 = null;
/* 10230 */     Token DOTDOT194 = null;
/* 10231 */     Token CLOSE_BRACE195 = null;
/* 10232 */     ParserRuleReturnScope from = null;
/* 10233 */     ParserRuleReturnScope to = null;
/* 10234 */     ParserRuleReturnScope using = null;
/*       */ 
/* 10236 */     CommonTree CATCHALL_DIRECTIVE192_tree = null;
/* 10237 */     CommonTree OPEN_BRACE193_tree = null;
/* 10238 */     CommonTree DOTDOT194_tree = null;
/* 10239 */     CommonTree CLOSE_BRACE195_tree = null;
/* 10240 */     RewriteRuleTokenStream stream_DOTDOT = new RewriteRuleTokenStream(this.adaptor, "token DOTDOT");
/* 10241 */     RewriteRuleTokenStream stream_CLOSE_BRACE = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
/* 10242 */     RewriteRuleTokenStream stream_OPEN_BRACE = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
/* 10243 */     RewriteRuleTokenStream stream_CATCHALL_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token CATCHALL_DIRECTIVE");
/* 10244 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 10250 */       CATCHALL_DIRECTIVE192 = (Token)match(this.input, 23, FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3244);
/* 10251 */       stream_CATCHALL_DIRECTIVE.add(CATCHALL_DIRECTIVE192);
/*       */ 
/* 10253 */       OPEN_BRACE193 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_catchall_directive3246);
/* 10254 */       stream_OPEN_BRACE.add(OPEN_BRACE193);
/*       */ 
/* 10256 */       pushFollow(FOLLOW_label_ref_in_catchall_directive3250);
/* 10257 */       from = label_ref();
/* 10258 */       this.state._fsp -= 1;
/*       */ 
/* 10260 */       stream_label_ref.add(from.getTree());
/* 10261 */       DOTDOT194 = (Token)match(this.input, 33, FOLLOW_DOTDOT_in_catchall_directive3252);
/* 10262 */       stream_DOTDOT.add(DOTDOT194);
/*       */ 
/* 10264 */       pushFollow(FOLLOW_label_ref_in_catchall_directive3256);
/* 10265 */       to = label_ref();
/* 10266 */       this.state._fsp -= 1;
/*       */ 
/* 10268 */       stream_label_ref.add(to.getTree());
/* 10269 */       CLOSE_BRACE195 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_catchall_directive3258);
/* 10270 */       stream_CLOSE_BRACE.add(CLOSE_BRACE195);
/*       */ 
/* 10272 */       pushFollow(FOLLOW_label_ref_in_catchall_directive3262);
/* 10273 */       using = label_ref();
/* 10274 */       this.state._fsp -= 1;
/*       */ 
/* 10276 */       stream_label_ref.add(using.getTree());
/*       */ 
/* 10284 */       retval.tree = root_0;
/* 10285 */       RewriteRuleSubtreeStream stream_to = new RewriteRuleSubtreeStream(this.adaptor, "rule to", to != null ? to.getTree() : null);
/* 10286 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/* 10287 */       RewriteRuleSubtreeStream stream_using = new RewriteRuleSubtreeStream(this.adaptor, "rule using", using != null ? using.getTree() : null);
/* 10288 */       RewriteRuleSubtreeStream stream_from = new RewriteRuleSubtreeStream(this.adaptor, "rule from", from != null ? from.getTree() : null);
/*       */ 
/* 10290 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10295 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 10296 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(111, retval.start, "I_CATCHALL"), root_1);
/* 10297 */       this.adaptor.addChild(root_1, stream_from.nextTree());
/* 10298 */       this.adaptor.addChild(root_1, stream_to.nextTree());
/* 10299 */       this.adaptor.addChild(root_1, stream_using.nextTree());
/* 10300 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 10306 */       retval.tree = root_0;
/*       */ 
/* 10310 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 10312 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 10313 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 10317 */       reportError(re);
/* 10318 */       recover(this.input, re);
/* 10319 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 10324 */     return retval;
/*       */   }
/*       */ 
/*       */   public final parameter_directive_return parameter_directive()
/*       */     throws RecognitionException
/*       */   {
/* 10339 */     parameter_directive_return retval = new parameter_directive_return();
/* 10340 */     retval.start = this.input.LT(1);
/*       */ 
/* 10342 */     CommonTree root_0 = null;
/*       */ 
/* 10344 */     Token PARAMETER_DIRECTIVE196 = null;
/* 10345 */     Token REGISTER197 = null;
/* 10346 */     Token COMMA198 = null;
/* 10347 */     Token STRING_LITERAL199 = null;
/* 10348 */     Token END_PARAMETER_DIRECTIVE201 = null;
/* 10349 */     ParserRuleReturnScope annotation200 = null;
/*       */ 
/* 10351 */     CommonTree PARAMETER_DIRECTIVE196_tree = null;
/* 10352 */     CommonTree REGISTER197_tree = null;
/* 10353 */     CommonTree COMMA198_tree = null;
/* 10354 */     CommonTree STRING_LITERAL199_tree = null;
/* 10355 */     CommonTree END_PARAMETER_DIRECTIVE201_tree = null;
/* 10356 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/* 10357 */     RewriteRuleTokenStream stream_END_PARAMETER_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_PARAMETER_DIRECTIVE");
/* 10358 */     RewriteRuleTokenStream stream_PARAMETER_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token PARAMETER_DIRECTIVE");
/* 10359 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 10360 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 10361 */     RewriteRuleSubtreeStream stream_annotation = new RewriteRuleSubtreeStream(this.adaptor, "rule annotation");
/*       */ 
/* 10363 */     List annotations = new ArrayList();
/*       */     try
/*       */     {
/* 10368 */       PARAMETER_DIRECTIVE196 = (Token)match(this.input, 195, FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3301);
/* 10369 */       stream_PARAMETER_DIRECTIVE.add(PARAMETER_DIRECTIVE196);
/*       */ 
/* 10371 */       REGISTER197 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_parameter_directive3303);
/* 10372 */       stream_REGISTER.add(REGISTER197);
/*       */ 
/* 10375 */       int alt32 = 2;
/* 10376 */       int LA32_0 = this.input.LA(1);
/* 10377 */       if (LA32_0 == 31) {
/* 10378 */         alt32 = 1;
/*       */       }
/* 10380 */       switch (alt32)
/*       */       {
/*       */       case 1:
/* 10384 */         COMMA198 = (Token)match(this.input, 31, FOLLOW_COMMA_in_parameter_directive3306);
/* 10385 */         stream_COMMA.add(COMMA198);
/*       */ 
/* 10387 */         STRING_LITERAL199 = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_parameter_directive3308);
/* 10388 */         stream_STRING_LITERAL.add(STRING_LITERAL199);
/*       */       }
/*       */ 
/*       */       while (true)
/*       */       {
/* 10398 */         int alt33 = 2;
/* 10399 */         alt33 = this.dfa33.predict(this.input);
/* 10400 */         switch (alt33)
/*       */         {
/*       */         case 1:
/* 10404 */           if (this.input.LA(1) != 5) {
/*       */             throw new FailedPredicateException(this.input, "parameter_directive", "input.LA(1) == ANNOTATION_DIRECTIVE");
/*       */           }
/* 10407 */           pushFollow(FOLLOW_annotation_in_parameter_directive3319);
/* 10408 */           annotation200 = annotation();
/* 10409 */           this.state._fsp -= 1;
/*       */ 
/* 10411 */           stream_annotation.add(annotation200.getTree());
/* 10412 */           annotations.add(annotation200 != null ? (CommonTree)annotation200.getTree() : null);
/*       */ 
/* 10414 */           break;
/*       */         default:
/* 10417 */           break label446;
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/* 10422 */       label446: int alt34 = 2;
/* 10423 */       int LA34_0 = this.input.LA(1);
/* 10424 */       if (LA34_0 == 42) {
/* 10425 */         alt34 = 1;
/*       */       }
/* 10427 */       else if ((LA34_0 == 5) || (LA34_0 == 7) || ((LA34_0 >= 23) && (LA34_0 <= 24)) || (LA34_0 == 30) || ((LA34_0 >= 39) && (LA34_0 <= 40)) || (LA34_0 == 46) || ((LA34_0 >= 58) && (LA34_0 <= 101)) || ((LA34_0 >= 184) && (LA34_0 <= 186)) || ((LA34_0 >= 194) && (LA34_0 <= 195)) || (LA34_0 == 200) || ((LA34_0 >= 202) && (LA34_0 <= 203)) || ((LA34_0 >= 206) && (LA34_0 <= 207))) {
/* 10428 */         alt34 = 2;
/*       */       }
/*       */       else
/*       */       {
/* 10432 */         NoViableAltException nvae = new NoViableAltException("", 34, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/* 10437 */       switch (alt34)
/*       */       {
/*       */       case 1:
/* 10441 */         END_PARAMETER_DIRECTIVE201 = (Token)match(this.input, 42, FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3332);
/* 10442 */         stream_END_PARAMETER_DIRECTIVE.add(END_PARAMETER_DIRECTIVE201);
/*       */ 
/* 10451 */         retval.tree = root_0;
/* 10452 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 10454 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10459 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 10460 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(136, retval.start, "I_PARAMETER"), root_1);
/* 10461 */         this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/*       */ 
/* 10463 */         if (stream_STRING_LITERAL.hasNext()) {
/* 10464 */           this.adaptor.addChild(root_1, stream_STRING_LITERAL.nextNode());
/*       */         }
/* 10466 */         stream_STRING_LITERAL.reset();
/*       */ 
/* 10470 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 10471 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(106, "I_ANNOTATIONS"), root_2);
/*       */ 
/* 10473 */         while (stream_annotation.hasNext()) {
/* 10474 */           this.adaptor.addChild(root_2, stream_annotation.nextTree());
/*       */         }
/* 10476 */         stream_annotation.reset();
/*       */ 
/* 10478 */         this.adaptor.addChild(root_1, root_2);
/*       */ 
/* 10481 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 10487 */         retval.tree = root_0;
/*       */ 
/* 10490 */         break;
/*       */       case 2:
/* 10494 */         ((statements_and_directives_scope)this.statements_and_directives_stack.peek()).methodAnnotations.addAll(annotations);
/*       */ 
/* 10502 */         retval.tree = root_0;
/* 10503 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 10505 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10510 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 10511 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(136, retval.start, "I_PARAMETER"), root_1);
/* 10512 */         this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/*       */ 
/* 10514 */         if (stream_STRING_LITERAL.hasNext()) {
/* 10515 */           this.adaptor.addChild(root_1, stream_STRING_LITERAL.nextNode());
/*       */         }
/* 10517 */         stream_STRING_LITERAL.reset();
/*       */ 
/* 10521 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 10522 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(106, "I_ANNOTATIONS"), root_2);
/* 10523 */         this.adaptor.addChild(root_1, root_2);
/*       */ 
/* 10526 */         this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 10532 */         retval.tree = root_0;
/*       */       }
/*       */ 
/* 10541 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 10543 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 10544 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 10548 */       reportError(re);
/* 10549 */       recover(this.input, re);
/* 10550 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 10555 */     return retval;
/*       */   }
/*       */ 
/*       */   public final debug_directive_return debug_directive()
/*       */     throws RecognitionException
/*       */   {
/* 10570 */     debug_directive_return retval = new debug_directive_return();
/* 10571 */     retval.start = this.input.LT(1);
/*       */ 
/* 10573 */     CommonTree root_0 = null;
/*       */ 
/* 10575 */     ParserRuleReturnScope line_directive202 = null;
/* 10576 */     ParserRuleReturnScope local_directive203 = null;
/* 10577 */     ParserRuleReturnScope end_local_directive204 = null;
/* 10578 */     ParserRuleReturnScope restart_local_directive205 = null;
/* 10579 */     ParserRuleReturnScope prologue_directive206 = null;
/* 10580 */     ParserRuleReturnScope epilogue_directive207 = null;
/* 10581 */     ParserRuleReturnScope source_directive208 = null;
/*       */     try
/*       */     {
/* 10586 */       int alt35 = 7;
/* 10587 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 184:
/* 10590 */         alt35 = 1;
/*       */ 
/* 10592 */         break;
/*       */       case 186:
/* 10595 */         alt35 = 2;
/*       */ 
/* 10597 */         break;
/*       */       case 39:
/* 10600 */         alt35 = 3;
/*       */ 
/* 10602 */         break;
/*       */       case 203:
/* 10605 */         alt35 = 4;
/*       */ 
/* 10607 */         break;
/*       */       case 200:
/* 10610 */         alt35 = 5;
/*       */ 
/* 10612 */         break;
/*       */       case 46:
/* 10615 */         alt35 = 6;
/*       */ 
/* 10617 */         break;
/*       */       case 206:
/* 10620 */         alt35 = 7;
/*       */ 
/* 10622 */         break;
/*       */       default:
/* 10624 */         NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
/*       */         throw nvae;
/*       */       }
/* 10628 */       switch (alt35)
/*       */       {
/*       */       case 1:
/* 10632 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10635 */         pushFollow(FOLLOW_line_directive_in_debug_directive3405);
/* 10636 */         line_directive202 = line_directive();
/* 10637 */         this.state._fsp -= 1;
/*       */ 
/* 10639 */         this.adaptor.addChild(root_0, line_directive202.getTree());
/*       */ 
/* 10642 */         break;
/*       */       case 2:
/* 10646 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10649 */         pushFollow(FOLLOW_local_directive_in_debug_directive3411);
/* 10650 */         local_directive203 = local_directive();
/* 10651 */         this.state._fsp -= 1;
/*       */ 
/* 10653 */         this.adaptor.addChild(root_0, local_directive203.getTree());
/*       */ 
/* 10656 */         break;
/*       */       case 3:
/* 10660 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10663 */         pushFollow(FOLLOW_end_local_directive_in_debug_directive3417);
/* 10664 */         end_local_directive204 = end_local_directive();
/* 10665 */         this.state._fsp -= 1;
/*       */ 
/* 10667 */         this.adaptor.addChild(root_0, end_local_directive204.getTree());
/*       */ 
/* 10670 */         break;
/*       */       case 4:
/* 10674 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10677 */         pushFollow(FOLLOW_restart_local_directive_in_debug_directive3423);
/* 10678 */         restart_local_directive205 = restart_local_directive();
/* 10679 */         this.state._fsp -= 1;
/*       */ 
/* 10681 */         this.adaptor.addChild(root_0, restart_local_directive205.getTree());
/*       */ 
/* 10684 */         break;
/*       */       case 5:
/* 10688 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10691 */         pushFollow(FOLLOW_prologue_directive_in_debug_directive3429);
/* 10692 */         prologue_directive206 = prologue_directive();
/* 10693 */         this.state._fsp -= 1;
/*       */ 
/* 10695 */         this.adaptor.addChild(root_0, prologue_directive206.getTree());
/*       */ 
/* 10698 */         break;
/*       */       case 6:
/* 10702 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10705 */         pushFollow(FOLLOW_epilogue_directive_in_debug_directive3435);
/* 10706 */         epilogue_directive207 = epilogue_directive();
/* 10707 */         this.state._fsp -= 1;
/*       */ 
/* 10709 */         this.adaptor.addChild(root_0, epilogue_directive207.getTree());
/*       */ 
/* 10712 */         break;
/*       */       case 7:
/* 10716 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10719 */         pushFollow(FOLLOW_source_directive_in_debug_directive3441);
/* 10720 */         source_directive208 = source_directive();
/* 10721 */         this.state._fsp -= 1;
/*       */ 
/* 10723 */         this.adaptor.addChild(root_0, source_directive208.getTree());
/*       */       }
/*       */ 
/* 10729 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 10731 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 10732 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 10736 */       reportError(re);
/* 10737 */       recover(this.input, re);
/* 10738 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 10743 */     return retval;
/*       */   }
/*       */ 
/*       */   public final line_directive_return line_directive()
/*       */     throws RecognitionException
/*       */   {
/* 10758 */     line_directive_return retval = new line_directive_return();
/* 10759 */     retval.start = this.input.LT(1);
/*       */ 
/* 10761 */     CommonTree root_0 = null;
/*       */ 
/* 10763 */     Token LINE_DIRECTIVE209 = null;
/* 10764 */     ParserRuleReturnScope integral_literal210 = null;
/*       */ 
/* 10766 */     CommonTree LINE_DIRECTIVE209_tree = null;
/* 10767 */     RewriteRuleTokenStream stream_LINE_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token LINE_DIRECTIVE");
/* 10768 */     RewriteRuleSubtreeStream stream_integral_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
/*       */     try
/*       */     {
/* 10774 */       LINE_DIRECTIVE209 = (Token)match(this.input, 184, FOLLOW_LINE_DIRECTIVE_in_line_directive3451);
/* 10775 */       stream_LINE_DIRECTIVE.add(LINE_DIRECTIVE209);
/*       */ 
/* 10777 */       pushFollow(FOLLOW_integral_literal_in_line_directive3453);
/* 10778 */       integral_literal210 = integral_literal();
/* 10779 */       this.state._fsp -= 1;
/*       */ 
/* 10781 */       stream_integral_literal.add(integral_literal210.getTree());
/*       */ 
/* 10789 */       retval.tree = root_0;
/* 10790 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 10792 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 10797 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 10798 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(126, retval.start, "I_LINE"), root_1);
/* 10799 */       this.adaptor.addChild(root_1, stream_integral_literal.nextTree());
/* 10800 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 10806 */       retval.tree = root_0;
/*       */ 
/* 10810 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 10812 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 10813 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 10817 */       reportError(re);
/* 10818 */       recover(this.input, re);
/* 10819 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 10824 */     return retval;
/*       */   }
/*       */ 
/*       */   public final local_directive_return local_directive()
/*       */     throws RecognitionException
/*       */   {
/* 10839 */     local_directive_return retval = new local_directive_return();
/* 10840 */     retval.start = this.input.LT(1);
/*       */ 
/* 10842 */     CommonTree root_0 = null;
/*       */ 
/* 10844 */     Token name = null;
/* 10845 */     Token signature = null;
/* 10846 */     Token LOCAL_DIRECTIVE211 = null;
/* 10847 */     Token REGISTER212 = null;
/* 10848 */     Token COMMA213 = null;
/* 10849 */     Token NULL_LITERAL214 = null;
/* 10850 */     Token COLON215 = null;
/* 10851 */     Token VOID_TYPE216 = null;
/* 10852 */     Token COMMA218 = null;
/* 10853 */     ParserRuleReturnScope nonvoid_type_descriptor217 = null;
/*       */ 
/* 10855 */     CommonTree name_tree = null;
/* 10856 */     CommonTree signature_tree = null;
/* 10857 */     CommonTree LOCAL_DIRECTIVE211_tree = null;
/* 10858 */     CommonTree REGISTER212_tree = null;
/* 10859 */     CommonTree COMMA213_tree = null;
/* 10860 */     CommonTree NULL_LITERAL214_tree = null;
/* 10861 */     CommonTree COLON215_tree = null;
/* 10862 */     CommonTree VOID_TYPE216_tree = null;
/* 10863 */     CommonTree COMMA218_tree = null;
/* 10864 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/* 10865 */     RewriteRuleTokenStream stream_NULL_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token NULL_LITERAL");
/* 10866 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/* 10867 */     RewriteRuleTokenStream stream_LOCAL_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token LOCAL_DIRECTIVE");
/* 10868 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 10869 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 10870 */     RewriteRuleTokenStream stream_VOID_TYPE = new RewriteRuleTokenStream(this.adaptor, "token VOID_TYPE");
/* 10871 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/*       */     try
/*       */     {
/* 10877 */       LOCAL_DIRECTIVE211 = (Token)match(this.input, 186, FOLLOW_LOCAL_DIRECTIVE_in_local_directive3476);
/* 10878 */       stream_LOCAL_DIRECTIVE.add(LOCAL_DIRECTIVE211);
/*       */ 
/* 10880 */       REGISTER212 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_local_directive3478);
/* 10881 */       stream_REGISTER.add(REGISTER212);
/*       */ 
/* 10884 */       int alt39 = 2;
/* 10885 */       int LA39_0 = this.input.LA(1);
/* 10886 */       if (LA39_0 == 31) {
/* 10887 */         alt39 = 1;
/*       */       }
/* 10889 */       switch (alt39)
/*       */       {
/*       */       case 1:
/* 10893 */         COMMA213 = (Token)match(this.input, 31, FOLLOW_COMMA_in_local_directive3481);
/* 10894 */         stream_COMMA.add(COMMA213);
/*       */ 
/* 10897 */         int alt36 = 2;
/* 10898 */         int LA36_0 = this.input.LA(1);
/* 10899 */         if (LA36_0 == 191) {
/* 10900 */           alt36 = 1;
/*       */         }
/* 10902 */         else if (LA36_0 == 208) {
/* 10903 */           alt36 = 2;
/*       */         }
/*       */         else
/*       */         {
/* 10907 */           NoViableAltException nvae = new NoViableAltException("", 36, 0, this.input);
/*       */           throw nvae;
/*       */         }
/*       */ 
/* 10912 */         switch (alt36)
/*       */         {
/*       */         case 1:
/* 10916 */           NULL_LITERAL214 = (Token)match(this.input, 191, FOLLOW_NULL_LITERAL_in_local_directive3484);
/* 10917 */           stream_NULL_LITERAL.add(NULL_LITERAL214);
/*       */ 
/* 10920 */           break;
/*       */         case 2:
/* 10924 */           name = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_local_directive3490);
/* 10925 */           stream_STRING_LITERAL.add(name);
/*       */         }
/*       */ 
/* 10932 */         COLON215 = (Token)match(this.input, 30, FOLLOW_COLON_in_local_directive3493);
/* 10933 */         stream_COLON.add(COLON215);
/*       */ 
/* 10936 */         int alt37 = 2;
/* 10937 */         int LA37_0 = this.input.LA(1);
/* 10938 */         if (LA37_0 == 212) {
/* 10939 */           alt37 = 1;
/*       */         }
/* 10941 */         else if ((LA37_0 == 8) || (LA37_0 == 26) || (LA37_0 == 199)) {
/* 10942 */           alt37 = 2;
/*       */         }
/*       */         else
/*       */         {
/* 10946 */           NoViableAltException nvae = new NoViableAltException("", 37, 0, this.input);
/*       */           throw nvae;
/*       */         }
/*       */ 
/* 10951 */         switch (alt37)
/*       */         {
/*       */         case 1:
/* 10955 */           VOID_TYPE216 = (Token)match(this.input, 212, FOLLOW_VOID_TYPE_in_local_directive3496);
/* 10956 */           stream_VOID_TYPE.add(VOID_TYPE216);
/*       */ 
/* 10959 */           break;
/*       */         case 2:
/* 10963 */           pushFollow(FOLLOW_nonvoid_type_descriptor_in_local_directive3500);
/* 10964 */           nonvoid_type_descriptor217 = nonvoid_type_descriptor();
/* 10965 */           this.state._fsp -= 1;
/*       */ 
/* 10967 */           stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor217.getTree());
/*       */         }
/*       */ 
/* 10974 */         int alt38 = 2;
/* 10975 */         int LA38_0 = this.input.LA(1);
/* 10976 */         if (LA38_0 == 31) {
/* 10977 */           alt38 = 1;
/*       */         }
/* 10979 */         switch (alt38)
/*       */         {
/*       */         case 1:
/* 10983 */           COMMA218 = (Token)match(this.input, 31, FOLLOW_COMMA_in_local_directive3534);
/* 10984 */           stream_COMMA.add(COMMA218);
/*       */ 
/* 10986 */           signature = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_local_directive3538);
/* 10987 */           stream_STRING_LITERAL.add(signature);
/*       */         }
/*       */ 
/*       */       }
/*       */ 
/* 11006 */       retval.tree = root_0;
/* 11007 */       RewriteRuleTokenStream stream_name = new RewriteRuleTokenStream(this.adaptor, "token name", name);
/* 11008 */       RewriteRuleTokenStream stream_signature = new RewriteRuleTokenStream(this.adaptor, "token signature", signature);
/* 11009 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11011 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11016 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 11017 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(127, retval.start, "I_LOCAL"), root_1);
/* 11018 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/*       */ 
/* 11020 */       if (stream_NULL_LITERAL.hasNext()) {
/* 11021 */         this.adaptor.addChild(root_1, stream_NULL_LITERAL.nextNode());
/*       */       }
/* 11023 */       stream_NULL_LITERAL.reset();
/*       */ 
/* 11026 */       if (stream_name.hasNext()) {
/* 11027 */         this.adaptor.addChild(root_1, stream_name.nextNode());
/*       */       }
/* 11029 */       stream_name.reset();
/*       */ 
/* 11032 */       if (stream_nonvoid_type_descriptor.hasNext()) {
/* 11033 */         this.adaptor.addChild(root_1, stream_nonvoid_type_descriptor.nextTree());
/*       */       }
/* 11035 */       stream_nonvoid_type_descriptor.reset();
/*       */ 
/* 11038 */       if (stream_signature.hasNext()) {
/* 11039 */         this.adaptor.addChild(root_1, stream_signature.nextNode());
/*       */       }
/* 11041 */       stream_signature.reset();
/*       */ 
/* 11043 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 11049 */       retval.tree = root_0;
/*       */ 
/* 11053 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11055 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11056 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11060 */       reportError(re);
/* 11061 */       recover(this.input, re);
/* 11062 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11067 */     return retval;
/*       */   }
/*       */ 
/*       */   public final end_local_directive_return end_local_directive()
/*       */     throws RecognitionException
/*       */   {
/* 11082 */     end_local_directive_return retval = new end_local_directive_return();
/* 11083 */     retval.start = this.input.LT(1);
/*       */ 
/* 11085 */     CommonTree root_0 = null;
/*       */ 
/* 11087 */     Token END_LOCAL_DIRECTIVE219 = null;
/* 11088 */     Token REGISTER220 = null;
/*       */ 
/* 11090 */     CommonTree END_LOCAL_DIRECTIVE219_tree = null;
/* 11091 */     CommonTree REGISTER220_tree = null;
/* 11092 */     RewriteRuleTokenStream stream_END_LOCAL_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_LOCAL_DIRECTIVE");
/* 11093 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/* 11099 */       END_LOCAL_DIRECTIVE219 = (Token)match(this.input, 39, FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3580);
/* 11100 */       stream_END_LOCAL_DIRECTIVE.add(END_LOCAL_DIRECTIVE219);
/*       */ 
/* 11102 */       REGISTER220 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_end_local_directive3582);
/* 11103 */       stream_REGISTER.add(REGISTER220);
/*       */ 
/* 11112 */       retval.tree = root_0;
/* 11113 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11115 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11120 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 11121 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(118, retval.start, "I_END_LOCAL"), root_1);
/* 11122 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 11123 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 11129 */       retval.tree = root_0;
/*       */ 
/* 11133 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11135 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11136 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11140 */       reportError(re);
/* 11141 */       recover(this.input, re);
/* 11142 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11147 */     return retval;
/*       */   }
/*       */ 
/*       */   public final restart_local_directive_return restart_local_directive()
/*       */     throws RecognitionException
/*       */   {
/* 11162 */     restart_local_directive_return retval = new restart_local_directive_return();
/* 11163 */     retval.start = this.input.LT(1);
/*       */ 
/* 11165 */     CommonTree root_0 = null;
/*       */ 
/* 11167 */     Token RESTART_LOCAL_DIRECTIVE221 = null;
/* 11168 */     Token REGISTER222 = null;
/*       */ 
/* 11170 */     CommonTree RESTART_LOCAL_DIRECTIVE221_tree = null;
/* 11171 */     CommonTree REGISTER222_tree = null;
/* 11172 */     RewriteRuleTokenStream stream_RESTART_LOCAL_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token RESTART_LOCAL_DIRECTIVE");
/* 11173 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/* 11179 */       RESTART_LOCAL_DIRECTIVE221 = (Token)match(this.input, 203, FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3605);
/* 11180 */       stream_RESTART_LOCAL_DIRECTIVE.add(RESTART_LOCAL_DIRECTIVE221);
/*       */ 
/* 11182 */       REGISTER222 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_restart_local_directive3607);
/* 11183 */       stream_REGISTER.add(REGISTER222);
/*       */ 
/* 11192 */       retval.tree = root_0;
/* 11193 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11195 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11200 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 11201 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(143, retval.start, "I_RESTART_LOCAL"), root_1);
/* 11202 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 11203 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 11209 */       retval.tree = root_0;
/*       */ 
/* 11213 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11215 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11216 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11220 */       reportError(re);
/* 11221 */       recover(this.input, re);
/* 11222 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11227 */     return retval;
/*       */   }
/*       */ 
/*       */   public final prologue_directive_return prologue_directive()
/*       */     throws RecognitionException
/*       */   {
/* 11242 */     prologue_directive_return retval = new prologue_directive_return();
/* 11243 */     retval.start = this.input.LT(1);
/*       */ 
/* 11245 */     CommonTree root_0 = null;
/*       */ 
/* 11247 */     Token PROLOGUE_DIRECTIVE223 = null;
/*       */ 
/* 11249 */     CommonTree PROLOGUE_DIRECTIVE223_tree = null;
/* 11250 */     RewriteRuleTokenStream stream_PROLOGUE_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token PROLOGUE_DIRECTIVE");
/*       */     try
/*       */     {
/* 11256 */       PROLOGUE_DIRECTIVE223 = (Token)match(this.input, 200, FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3630);
/* 11257 */       stream_PROLOGUE_DIRECTIVE.add(PROLOGUE_DIRECTIVE223);
/*       */ 
/* 11266 */       retval.tree = root_0;
/* 11267 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11269 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11274 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 11275 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(139, retval.start, "I_PROLOGUE"), root_1);
/* 11276 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 11282 */       retval.tree = root_0;
/*       */ 
/* 11286 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11288 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11289 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11293 */       reportError(re);
/* 11294 */       recover(this.input, re);
/* 11295 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11300 */     return retval;
/*       */   }
/*       */ 
/*       */   public final epilogue_directive_return epilogue_directive()
/*       */     throws RecognitionException
/*       */   {
/* 11315 */     epilogue_directive_return retval = new epilogue_directive_return();
/* 11316 */     retval.start = this.input.LT(1);
/*       */ 
/* 11318 */     CommonTree root_0 = null;
/*       */ 
/* 11320 */     Token EPILOGUE_DIRECTIVE224 = null;
/*       */ 
/* 11322 */     CommonTree EPILOGUE_DIRECTIVE224_tree = null;
/* 11323 */     RewriteRuleTokenStream stream_EPILOGUE_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token EPILOGUE_DIRECTIVE");
/*       */     try
/*       */     {
/* 11329 */       EPILOGUE_DIRECTIVE224 = (Token)match(this.input, 46, FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3651);
/* 11330 */       stream_EPILOGUE_DIRECTIVE.add(EPILOGUE_DIRECTIVE224);
/*       */ 
/* 11339 */       retval.tree = root_0;
/* 11340 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11342 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11347 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 11348 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(119, retval.start, "I_EPILOGUE"), root_1);
/* 11349 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 11355 */       retval.tree = root_0;
/*       */ 
/* 11359 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11361 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11362 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11366 */       reportError(re);
/* 11367 */       recover(this.input, re);
/* 11368 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11373 */     return retval;
/*       */   }
/*       */ 
/*       */   public final source_directive_return source_directive()
/*       */     throws RecognitionException
/*       */   {
/* 11388 */     source_directive_return retval = new source_directive_return();
/* 11389 */     retval.start = this.input.LT(1);
/*       */ 
/* 11391 */     CommonTree root_0 = null;
/*       */ 
/* 11393 */     Token SOURCE_DIRECTIVE225 = null;
/* 11394 */     Token STRING_LITERAL226 = null;
/*       */ 
/* 11396 */     CommonTree SOURCE_DIRECTIVE225_tree = null;
/* 11397 */     CommonTree STRING_LITERAL226_tree = null;
/* 11398 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/* 11399 */     RewriteRuleTokenStream stream_SOURCE_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token SOURCE_DIRECTIVE");
/*       */     try
/*       */     {
/* 11405 */       SOURCE_DIRECTIVE225 = (Token)match(this.input, 206, FOLLOW_SOURCE_DIRECTIVE_in_source_directive3672);
/* 11406 */       stream_SOURCE_DIRECTIVE.add(SOURCE_DIRECTIVE225);
/*       */ 
/* 11409 */       int alt40 = 2;
/* 11410 */       int LA40_0 = this.input.LA(1);
/* 11411 */       if (LA40_0 == 208) {
/* 11412 */         alt40 = 1;
/*       */       }
/* 11414 */       switch (alt40)
/*       */       {
/*       */       case 1:
/* 11418 */         STRING_LITERAL226 = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_source_directive3674);
/* 11419 */         stream_STRING_LITERAL.add(STRING_LITERAL226);
/*       */       }
/*       */ 
/* 11433 */       retval.tree = root_0;
/* 11434 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11436 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11441 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 11442 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(144, retval.start, "I_SOURCE"), root_1);
/*       */ 
/* 11444 */       if (stream_STRING_LITERAL.hasNext()) {
/* 11445 */         this.adaptor.addChild(root_1, stream_STRING_LITERAL.nextNode());
/*       */       }
/* 11447 */       stream_STRING_LITERAL.reset();
/*       */ 
/* 11449 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 11455 */       retval.tree = root_0;
/*       */ 
/* 11459 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11461 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11462 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11466 */       reportError(re);
/* 11467 */       recover(this.input, re);
/* 11468 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11473 */     return retval;
/*       */   }
/*       */ 
/*       */   public final instruction_format12x_return instruction_format12x()
/*       */     throws RecognitionException
/*       */   {
/* 11488 */     instruction_format12x_return retval = new instruction_format12x_return();
/* 11489 */     retval.start = this.input.LT(1);
/*       */ 
/* 11491 */     CommonTree root_0 = null;
/*       */ 
/* 11493 */     Token INSTRUCTION_FORMAT12x227 = null;
/* 11494 */     Token INSTRUCTION_FORMAT12x_OR_ID228 = null;
/*       */ 
/* 11496 */     CommonTree INSTRUCTION_FORMAT12x227_tree = null;
/* 11497 */     CommonTree INSTRUCTION_FORMAT12x_OR_ID228_tree = null;
/* 11498 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT12x_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT12x_OR_ID");
/*       */     try
/*       */     {
/* 11502 */       int alt41 = 2;
/* 11503 */       int LA41_0 = this.input.LA(1);
/* 11504 */       if (LA41_0 == 63) {
/* 11505 */         alt41 = 1;
/*       */       }
/* 11507 */       else if (LA41_0 == 64) {
/* 11508 */         alt41 = 2;
/*       */       }
/*       */       else
/*       */       {
/* 11512 */         NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/* 11517 */       switch (alt41)
/*       */       {
/*       */       case 1:
/* 11521 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11524 */         INSTRUCTION_FORMAT12x227 = (Token)match(this.input, 63, FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x3699);
/* 11525 */         INSTRUCTION_FORMAT12x227_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT12x227);
/* 11526 */         this.adaptor.addChild(root_0, INSTRUCTION_FORMAT12x227_tree);
/*       */ 
/* 11529 */         break;
/*       */       case 2:
/* 11533 */         INSTRUCTION_FORMAT12x_OR_ID228 = (Token)match(this.input, 64, FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x3705);
/* 11534 */         stream_INSTRUCTION_FORMAT12x_OR_ID.add(INSTRUCTION_FORMAT12x_OR_ID228);
/*       */ 
/* 11543 */         retval.tree = root_0;
/* 11544 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11546 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11549 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(63, INSTRUCTION_FORMAT12x_OR_ID228));
/*       */ 
/* 11553 */         retval.tree = root_0;
/*       */       }
/*       */ 
/* 11559 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11561 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11562 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11566 */       reportError(re);
/* 11567 */       recover(this.input, re);
/* 11568 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11573 */     return retval;
/*       */   }
/*       */ 
/*       */   public final instruction_format22s_return instruction_format22s()
/*       */     throws RecognitionException
/*       */   {
/* 11588 */     instruction_format22s_return retval = new instruction_format22s_return();
/* 11589 */     retval.start = this.input.LT(1);
/*       */ 
/* 11591 */     CommonTree root_0 = null;
/*       */ 
/* 11593 */     Token INSTRUCTION_FORMAT22s229 = null;
/* 11594 */     Token INSTRUCTION_FORMAT22s_OR_ID230 = null;
/*       */ 
/* 11596 */     CommonTree INSTRUCTION_FORMAT22s229_tree = null;
/* 11597 */     CommonTree INSTRUCTION_FORMAT22s_OR_ID230_tree = null;
/* 11598 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22s_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22s_OR_ID");
/*       */     try
/*       */     {
/* 11602 */       int alt42 = 2;
/* 11603 */       int LA42_0 = this.input.LA(1);
/* 11604 */       if (LA42_0 == 80) {
/* 11605 */         alt42 = 1;
/*       */       }
/* 11607 */       else if (LA42_0 == 81) {
/* 11608 */         alt42 = 2;
/*       */       }
/*       */       else
/*       */       {
/* 11612 */         NoViableAltException nvae = new NoViableAltException("", 42, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/* 11617 */       switch (alt42)
/*       */       {
/*       */       case 1:
/* 11621 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11624 */         INSTRUCTION_FORMAT22s229 = (Token)match(this.input, 80, FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s3720);
/* 11625 */         INSTRUCTION_FORMAT22s229_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT22s229);
/* 11626 */         this.adaptor.addChild(root_0, INSTRUCTION_FORMAT22s229_tree);
/*       */ 
/* 11629 */         break;
/*       */       case 2:
/* 11633 */         INSTRUCTION_FORMAT22s_OR_ID230 = (Token)match(this.input, 81, FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s3726);
/* 11634 */         stream_INSTRUCTION_FORMAT22s_OR_ID.add(INSTRUCTION_FORMAT22s_OR_ID230);
/*       */ 
/* 11643 */         retval.tree = root_0;
/* 11644 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11646 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11649 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(80, INSTRUCTION_FORMAT22s_OR_ID230));
/*       */ 
/* 11653 */         retval.tree = root_0;
/*       */       }
/*       */ 
/* 11659 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11661 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11662 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11666 */       reportError(re);
/* 11667 */       recover(this.input, re);
/* 11668 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11673 */     return retval;
/*       */   }
/*       */ 
/*       */   public final instruction_format31i_return instruction_format31i()
/*       */     throws RecognitionException
/*       */   {
/* 11688 */     instruction_format31i_return retval = new instruction_format31i_return();
/* 11689 */     retval.start = this.input.LT(1);
/*       */ 
/* 11691 */     CommonTree root_0 = null;
/*       */ 
/* 11693 */     Token INSTRUCTION_FORMAT31i231 = null;
/* 11694 */     Token INSTRUCTION_FORMAT31i_OR_ID232 = null;
/*       */ 
/* 11696 */     CommonTree INSTRUCTION_FORMAT31i231_tree = null;
/* 11697 */     CommonTree INSTRUCTION_FORMAT31i_OR_ID232_tree = null;
/* 11698 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT31i_OR_ID = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31i_OR_ID");
/*       */     try
/*       */     {
/* 11702 */       int alt43 = 2;
/* 11703 */       int LA43_0 = this.input.LA(1);
/* 11704 */       if (LA43_0 == 87) {
/* 11705 */         alt43 = 1;
/*       */       }
/* 11707 */       else if (LA43_0 == 88) {
/* 11708 */         alt43 = 2;
/*       */       }
/*       */       else
/*       */       {
/* 11712 */         NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
/*       */         throw nvae;
/*       */       }
/*       */ 
/* 11717 */       switch (alt43)
/*       */       {
/*       */       case 1:
/* 11721 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11724 */         INSTRUCTION_FORMAT31i231 = (Token)match(this.input, 87, FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i3741);
/* 11725 */         INSTRUCTION_FORMAT31i231_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT31i231);
/* 11726 */         this.adaptor.addChild(root_0, INSTRUCTION_FORMAT31i231_tree);
/*       */ 
/* 11729 */         break;
/*       */       case 2:
/* 11733 */         INSTRUCTION_FORMAT31i_OR_ID232 = (Token)match(this.input, 88, FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i3747);
/* 11734 */         stream_INSTRUCTION_FORMAT31i_OR_ID.add(INSTRUCTION_FORMAT31i_OR_ID232);
/*       */ 
/* 11743 */         retval.tree = root_0;
/* 11744 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 11746 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 11749 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(87, INSTRUCTION_FORMAT31i_OR_ID232));
/*       */ 
/* 11753 */         retval.tree = root_0;
/*       */       }
/*       */ 
/* 11759 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 11761 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 11762 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 11766 */       reportError(re);
/* 11767 */       recover(this.input, re);
/* 11768 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 11773 */     return retval;
/*       */   }
/*       */ 
/*       */   public final instruction_return instruction()
/*       */     throws RecognitionException
/*       */   {
/* 11788 */     instruction_return retval = new instruction_return();
/* 11789 */     retval.start = this.input.LT(1);
/*       */ 
/* 11791 */     CommonTree root_0 = null;
/*       */ 
/* 11793 */     ParserRuleReturnScope insn_format10t233 = null;
/* 11794 */     ParserRuleReturnScope insn_format10x234 = null;
/* 11795 */     ParserRuleReturnScope insn_format10x_odex235 = null;
/* 11796 */     ParserRuleReturnScope insn_format11n236 = null;
/* 11797 */     ParserRuleReturnScope insn_format11x237 = null;
/* 11798 */     ParserRuleReturnScope insn_format12x238 = null;
/* 11799 */     ParserRuleReturnScope insn_format20bc239 = null;
/* 11800 */     ParserRuleReturnScope insn_format20t240 = null;
/* 11801 */     ParserRuleReturnScope insn_format21c_field241 = null;
/* 11802 */     ParserRuleReturnScope insn_format21c_field_odex242 = null;
/* 11803 */     ParserRuleReturnScope insn_format21c_string243 = null;
/* 11804 */     ParserRuleReturnScope insn_format21c_type244 = null;
/* 11805 */     ParserRuleReturnScope insn_format21ih245 = null;
/* 11806 */     ParserRuleReturnScope insn_format21lh246 = null;
/* 11807 */     ParserRuleReturnScope insn_format21s247 = null;
/* 11808 */     ParserRuleReturnScope insn_format21t248 = null;
/* 11809 */     ParserRuleReturnScope insn_format22b249 = null;
/* 11810 */     ParserRuleReturnScope insn_format22c_field250 = null;
/* 11811 */     ParserRuleReturnScope insn_format22c_field_odex251 = null;
/* 11812 */     ParserRuleReturnScope insn_format22c_type252 = null;
/* 11813 */     ParserRuleReturnScope insn_format22cs_field253 = null;
/* 11814 */     ParserRuleReturnScope insn_format22s254 = null;
/* 11815 */     ParserRuleReturnScope insn_format22t255 = null;
/* 11816 */     ParserRuleReturnScope insn_format22x256 = null;
/* 11817 */     ParserRuleReturnScope insn_format23x257 = null;
/* 11818 */     ParserRuleReturnScope insn_format30t258 = null;
/* 11819 */     ParserRuleReturnScope insn_format31c259 = null;
/* 11820 */     ParserRuleReturnScope insn_format31i260 = null;
/* 11821 */     ParserRuleReturnScope insn_format31t261 = null;
/* 11822 */     ParserRuleReturnScope insn_format32x262 = null;
/* 11823 */     ParserRuleReturnScope insn_format35c_method263 = null;
/* 11824 */     ParserRuleReturnScope insn_format35c_type264 = null;
/* 11825 */     ParserRuleReturnScope insn_format35c_method_odex265 = null;
/* 11826 */     ParserRuleReturnScope insn_format35mi_method266 = null;
/* 11827 */     ParserRuleReturnScope insn_format35ms_method267 = null;
/* 11828 */     ParserRuleReturnScope insn_format3rc_method268 = null;
/* 11829 */     ParserRuleReturnScope insn_format3rc_method_odex269 = null;
/* 11830 */     ParserRuleReturnScope insn_format3rc_type270 = null;
/* 11831 */     ParserRuleReturnScope insn_format3rmi_method271 = null;
/* 11832 */     ParserRuleReturnScope insn_format3rms_method272 = null;
/* 11833 */     ParserRuleReturnScope insn_format51l273 = null;
/* 11834 */     ParserRuleReturnScope insn_array_data_directive274 = null;
/* 11835 */     ParserRuleReturnScope insn_packed_switch_directive275 = null;
/* 11836 */     ParserRuleReturnScope insn_sparse_switch_directive276 = null;
/*       */     try
/*       */     {
/* 11841 */       int alt44 = 44;
/* 11842 */       switch (this.input.LA(1))
/*       */       {
/*       */       case 58:
/* 11845 */         alt44 = 1;
/*       */ 
/* 11847 */         break;
/*       */       case 59:
/* 11850 */         alt44 = 2;
/*       */ 
/* 11852 */         break;
/*       */       case 60:
/* 11855 */         alt44 = 3;
/*       */ 
/* 11857 */         break;
/*       */       case 61:
/* 11860 */         alt44 = 4;
/*       */ 
/* 11862 */         break;
/*       */       case 62:
/* 11865 */         alt44 = 5;
/*       */ 
/* 11867 */         break;
/*       */       case 63:
/*       */       case 64:
/* 11871 */         alt44 = 6;
/*       */ 
/* 11873 */         break;
/*       */       case 65:
/* 11876 */         alt44 = 7;
/*       */ 
/* 11878 */         break;
/*       */       case 66:
/* 11881 */         alt44 = 8;
/*       */ 
/* 11883 */         break;
/*       */       case 67:
/* 11886 */         alt44 = 9;
/*       */ 
/* 11888 */         break;
/*       */       case 68:
/* 11891 */         alt44 = 10;
/*       */ 
/* 11893 */         break;
/*       */       case 69:
/* 11896 */         alt44 = 11;
/*       */ 
/* 11898 */         break;
/*       */       case 70:
/* 11901 */         alt44 = 12;
/*       */ 
/* 11903 */         break;
/*       */       case 71:
/* 11906 */         alt44 = 13;
/*       */ 
/* 11908 */         break;
/*       */       case 72:
/* 11911 */         alt44 = 14;
/*       */ 
/* 11913 */         break;
/*       */       case 73:
/* 11916 */         alt44 = 15;
/*       */ 
/* 11918 */         break;
/*       */       case 74:
/* 11921 */         alt44 = 16;
/*       */ 
/* 11923 */         break;
/*       */       case 75:
/* 11926 */         alt44 = 17;
/*       */ 
/* 11928 */         break;
/*       */       case 76:
/* 11931 */         alt44 = 18;
/*       */ 
/* 11933 */         break;
/*       */       case 77:
/* 11936 */         alt44 = 19;
/*       */ 
/* 11938 */         break;
/*       */       case 78:
/* 11941 */         alt44 = 20;
/*       */ 
/* 11943 */         break;
/*       */       case 79:
/* 11946 */         alt44 = 21;
/*       */ 
/* 11948 */         break;
/*       */       case 80:
/*       */       case 81:
/* 11952 */         alt44 = 22;
/*       */ 
/* 11954 */         break;
/*       */       case 82:
/* 11957 */         alt44 = 23;
/*       */ 
/* 11959 */         break;
/*       */       case 83:
/* 11962 */         alt44 = 24;
/*       */ 
/* 11964 */         break;
/*       */       case 84:
/* 11967 */         alt44 = 25;
/*       */ 
/* 11969 */         break;
/*       */       case 85:
/* 11972 */         alt44 = 26;
/*       */ 
/* 11974 */         break;
/*       */       case 86:
/* 11977 */         alt44 = 27;
/*       */ 
/* 11979 */         break;
/*       */       case 87:
/*       */       case 88:
/* 11983 */         alt44 = 28;
/*       */ 
/* 11985 */         break;
/*       */       case 89:
/* 11988 */         alt44 = 29;
/*       */ 
/* 11990 */         break;
/*       */       case 90:
/* 11993 */         alt44 = 30;
/*       */ 
/* 11995 */         break;
/*       */       case 91:
/* 11998 */         alt44 = 31;
/*       */ 
/* 12000 */         break;
/*       */       case 93:
/* 12003 */         alt44 = 32;
/*       */ 
/* 12005 */         break;
/*       */       case 92:
/* 12008 */         alt44 = 33;
/*       */ 
/* 12010 */         break;
/*       */       case 94:
/* 12013 */         alt44 = 34;
/*       */ 
/* 12015 */         break;
/*       */       case 95:
/* 12018 */         alt44 = 35;
/*       */ 
/* 12020 */         break;
/*       */       case 96:
/* 12023 */         alt44 = 36;
/*       */ 
/* 12025 */         break;
/*       */       case 97:
/* 12028 */         alt44 = 37;
/*       */ 
/* 12030 */         break;
/*       */       case 98:
/* 12033 */         alt44 = 38;
/*       */ 
/* 12035 */         break;
/*       */       case 99:
/* 12038 */         alt44 = 39;
/*       */ 
/* 12040 */         break;
/*       */       case 100:
/* 12043 */         alt44 = 40;
/*       */ 
/* 12045 */         break;
/*       */       case 101:
/* 12048 */         alt44 = 41;
/*       */ 
/* 12050 */         break;
/*       */       case 7:
/* 12053 */         alt44 = 42;
/*       */ 
/* 12055 */         break;
/*       */       case 194:
/* 12058 */         alt44 = 43;
/*       */ 
/* 12060 */         break;
/*       */       case 207:
/* 12063 */         alt44 = 44;
/*       */ 
/* 12065 */         break;
/*       */       case 8:
/*       */       case 9:
/*       */       case 10:
/*       */       case 11:
/*       */       case 12:
/*       */       case 13:
/*       */       case 14:
/*       */       case 15:
/*       */       case 16:
/*       */       case 17:
/*       */       case 18:
/*       */       case 19:
/*       */       case 20:
/*       */       case 21:
/*       */       case 22:
/*       */       case 23:
/*       */       case 24:
/*       */       case 25:
/*       */       case 26:
/*       */       case 27:
/*       */       case 28:
/*       */       case 29:
/*       */       case 30:
/*       */       case 31:
/*       */       case 32:
/*       */       case 33:
/*       */       case 34:
/*       */       case 35:
/*       */       case 36:
/*       */       case 37:
/*       */       case 38:
/*       */       case 39:
/*       */       case 40:
/*       */       case 41:
/*       */       case 42:
/*       */       case 43:
/*       */       case 44:
/*       */       case 45:
/*       */       case 46:
/*       */       case 47:
/*       */       case 48:
/*       */       case 49:
/*       */       case 50:
/*       */       case 51:
/*       */       case 52:
/*       */       case 53:
/*       */       case 54:
/*       */       case 55:
/*       */       case 56:
/*       */       case 57:
/*       */       case 102:
/*       */       case 103:
/*       */       case 104:
/*       */       case 105:
/*       */       case 106:
/*       */       case 107:
/*       */       case 108:
/*       */       case 109:
/*       */       case 110:
/*       */       case 111:
/*       */       case 112:
/*       */       case 113:
/*       */       case 114:
/*       */       case 115:
/*       */       case 116:
/*       */       case 117:
/*       */       case 118:
/*       */       case 119:
/*       */       case 120:
/*       */       case 121:
/*       */       case 122:
/*       */       case 123:
/*       */       case 124:
/*       */       case 125:
/*       */       case 126:
/*       */       case 127:
/*       */       case 128:
/*       */       case 129:
/*       */       case 130:
/*       */       case 131:
/*       */       case 132:
/*       */       case 133:
/*       */       case 134:
/*       */       case 135:
/*       */       case 136:
/*       */       case 137:
/*       */       case 138:
/*       */       case 139:
/*       */       case 140:
/*       */       case 141:
/*       */       case 142:
/*       */       case 143:
/*       */       case 144:
/*       */       case 145:
/*       */       case 146:
/*       */       case 147:
/*       */       case 148:
/*       */       case 149:
/*       */       case 150:
/*       */       case 151:
/*       */       case 152:
/*       */       case 153:
/*       */       case 154:
/*       */       case 155:
/*       */       case 156:
/*       */       case 157:
/*       */       case 158:
/*       */       case 159:
/*       */       case 160:
/*       */       case 161:
/*       */       case 162:
/*       */       case 163:
/*       */       case 164:
/*       */       case 165:
/*       */       case 166:
/*       */       case 167:
/*       */       case 168:
/*       */       case 169:
/*       */       case 170:
/*       */       case 171:
/*       */       case 172:
/*       */       case 173:
/*       */       case 174:
/*       */       case 175:
/*       */       case 176:
/*       */       case 177:
/*       */       case 178:
/*       */       case 179:
/*       */       case 180:
/*       */       case 181:
/*       */       case 182:
/*       */       case 183:
/*       */       case 184:
/*       */       case 185:
/*       */       case 186:
/*       */       case 187:
/*       */       case 188:
/*       */       case 189:
/*       */       case 190:
/*       */       case 191:
/*       */       case 192:
/*       */       case 193:
/*       */       case 195:
/*       */       case 196:
/*       */       case 197:
/*       */       case 198:
/*       */       case 199:
/*       */       case 200:
/*       */       case 201:
/*       */       case 202:
/*       */       case 203:
/*       */       case 204:
/*       */       case 205:
/*       */       case 206:
/*       */       default:
/* 12067 */         NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
/*       */         throw nvae;
/*       */       }
/* 12071 */       switch (alt44)
/*       */       {
/*       */       case 1:
/* 12075 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12078 */         pushFollow(FOLLOW_insn_format10t_in_instruction3764);
/* 12079 */         insn_format10t233 = insn_format10t();
/* 12080 */         this.state._fsp -= 1;
/*       */ 
/* 12082 */         this.adaptor.addChild(root_0, insn_format10t233.getTree());
/*       */ 
/* 12085 */         break;
/*       */       case 2:
/* 12089 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12092 */         pushFollow(FOLLOW_insn_format10x_in_instruction3770);
/* 12093 */         insn_format10x234 = insn_format10x();
/* 12094 */         this.state._fsp -= 1;
/*       */ 
/* 12096 */         this.adaptor.addChild(root_0, insn_format10x234.getTree());
/*       */ 
/* 12099 */         break;
/*       */       case 3:
/* 12103 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12106 */         pushFollow(FOLLOW_insn_format10x_odex_in_instruction3776);
/* 12107 */         insn_format10x_odex235 = insn_format10x_odex();
/* 12108 */         this.state._fsp -= 1;
/*       */ 
/* 12110 */         this.adaptor.addChild(root_0, insn_format10x_odex235.getTree());
/*       */ 
/* 12113 */         break;
/*       */       case 4:
/* 12117 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12120 */         pushFollow(FOLLOW_insn_format11n_in_instruction3782);
/* 12121 */         insn_format11n236 = insn_format11n();
/* 12122 */         this.state._fsp -= 1;
/*       */ 
/* 12124 */         this.adaptor.addChild(root_0, insn_format11n236.getTree());
/*       */ 
/* 12127 */         break;
/*       */       case 5:
/* 12131 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12134 */         pushFollow(FOLLOW_insn_format11x_in_instruction3788);
/* 12135 */         insn_format11x237 = insn_format11x();
/* 12136 */         this.state._fsp -= 1;
/*       */ 
/* 12138 */         this.adaptor.addChild(root_0, insn_format11x237.getTree());
/*       */ 
/* 12141 */         break;
/*       */       case 6:
/* 12145 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12148 */         pushFollow(FOLLOW_insn_format12x_in_instruction3794);
/* 12149 */         insn_format12x238 = insn_format12x();
/* 12150 */         this.state._fsp -= 1;
/*       */ 
/* 12152 */         this.adaptor.addChild(root_0, insn_format12x238.getTree());
/*       */ 
/* 12155 */         break;
/*       */       case 7:
/* 12159 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12162 */         pushFollow(FOLLOW_insn_format20bc_in_instruction3800);
/* 12163 */         insn_format20bc239 = insn_format20bc();
/* 12164 */         this.state._fsp -= 1;
/*       */ 
/* 12166 */         this.adaptor.addChild(root_0, insn_format20bc239.getTree());
/*       */ 
/* 12169 */         break;
/*       */       case 8:
/* 12173 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12176 */         pushFollow(FOLLOW_insn_format20t_in_instruction3806);
/* 12177 */         insn_format20t240 = insn_format20t();
/* 12178 */         this.state._fsp -= 1;
/*       */ 
/* 12180 */         this.adaptor.addChild(root_0, insn_format20t240.getTree());
/*       */ 
/* 12183 */         break;
/*       */       case 9:
/* 12187 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12190 */         pushFollow(FOLLOW_insn_format21c_field_in_instruction3812);
/* 12191 */         insn_format21c_field241 = insn_format21c_field();
/* 12192 */         this.state._fsp -= 1;
/*       */ 
/* 12194 */         this.adaptor.addChild(root_0, insn_format21c_field241.getTree());
/*       */ 
/* 12197 */         break;
/*       */       case 10:
/* 12201 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12204 */         pushFollow(FOLLOW_insn_format21c_field_odex_in_instruction3818);
/* 12205 */         insn_format21c_field_odex242 = insn_format21c_field_odex();
/* 12206 */         this.state._fsp -= 1;
/*       */ 
/* 12208 */         this.adaptor.addChild(root_0, insn_format21c_field_odex242.getTree());
/*       */ 
/* 12211 */         break;
/*       */       case 11:
/* 12215 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12218 */         pushFollow(FOLLOW_insn_format21c_string_in_instruction3824);
/* 12219 */         insn_format21c_string243 = insn_format21c_string();
/* 12220 */         this.state._fsp -= 1;
/*       */ 
/* 12222 */         this.adaptor.addChild(root_0, insn_format21c_string243.getTree());
/*       */ 
/* 12225 */         break;
/*       */       case 12:
/* 12229 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12232 */         pushFollow(FOLLOW_insn_format21c_type_in_instruction3830);
/* 12233 */         insn_format21c_type244 = insn_format21c_type();
/* 12234 */         this.state._fsp -= 1;
/*       */ 
/* 12236 */         this.adaptor.addChild(root_0, insn_format21c_type244.getTree());
/*       */ 
/* 12239 */         break;
/*       */       case 13:
/* 12243 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12246 */         pushFollow(FOLLOW_insn_format21ih_in_instruction3836);
/* 12247 */         insn_format21ih245 = insn_format21ih();
/* 12248 */         this.state._fsp -= 1;
/*       */ 
/* 12250 */         this.adaptor.addChild(root_0, insn_format21ih245.getTree());
/*       */ 
/* 12253 */         break;
/*       */       case 14:
/* 12257 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12260 */         pushFollow(FOLLOW_insn_format21lh_in_instruction3842);
/* 12261 */         insn_format21lh246 = insn_format21lh();
/* 12262 */         this.state._fsp -= 1;
/*       */ 
/* 12264 */         this.adaptor.addChild(root_0, insn_format21lh246.getTree());
/*       */ 
/* 12267 */         break;
/*       */       case 15:
/* 12271 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12274 */         pushFollow(FOLLOW_insn_format21s_in_instruction3848);
/* 12275 */         insn_format21s247 = insn_format21s();
/* 12276 */         this.state._fsp -= 1;
/*       */ 
/* 12278 */         this.adaptor.addChild(root_0, insn_format21s247.getTree());
/*       */ 
/* 12281 */         break;
/*       */       case 16:
/* 12285 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12288 */         pushFollow(FOLLOW_insn_format21t_in_instruction3854);
/* 12289 */         insn_format21t248 = insn_format21t();
/* 12290 */         this.state._fsp -= 1;
/*       */ 
/* 12292 */         this.adaptor.addChild(root_0, insn_format21t248.getTree());
/*       */ 
/* 12295 */         break;
/*       */       case 17:
/* 12299 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12302 */         pushFollow(FOLLOW_insn_format22b_in_instruction3860);
/* 12303 */         insn_format22b249 = insn_format22b();
/* 12304 */         this.state._fsp -= 1;
/*       */ 
/* 12306 */         this.adaptor.addChild(root_0, insn_format22b249.getTree());
/*       */ 
/* 12309 */         break;
/*       */       case 18:
/* 12313 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12316 */         pushFollow(FOLLOW_insn_format22c_field_in_instruction3866);
/* 12317 */         insn_format22c_field250 = insn_format22c_field();
/* 12318 */         this.state._fsp -= 1;
/*       */ 
/* 12320 */         this.adaptor.addChild(root_0, insn_format22c_field250.getTree());
/*       */ 
/* 12323 */         break;
/*       */       case 19:
/* 12327 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12330 */         pushFollow(FOLLOW_insn_format22c_field_odex_in_instruction3872);
/* 12331 */         insn_format22c_field_odex251 = insn_format22c_field_odex();
/* 12332 */         this.state._fsp -= 1;
/*       */ 
/* 12334 */         this.adaptor.addChild(root_0, insn_format22c_field_odex251.getTree());
/*       */ 
/* 12337 */         break;
/*       */       case 20:
/* 12341 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12344 */         pushFollow(FOLLOW_insn_format22c_type_in_instruction3878);
/* 12345 */         insn_format22c_type252 = insn_format22c_type();
/* 12346 */         this.state._fsp -= 1;
/*       */ 
/* 12348 */         this.adaptor.addChild(root_0, insn_format22c_type252.getTree());
/*       */ 
/* 12351 */         break;
/*       */       case 21:
/* 12355 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12358 */         pushFollow(FOLLOW_insn_format22cs_field_in_instruction3884);
/* 12359 */         insn_format22cs_field253 = insn_format22cs_field();
/* 12360 */         this.state._fsp -= 1;
/*       */ 
/* 12362 */         this.adaptor.addChild(root_0, insn_format22cs_field253.getTree());
/*       */ 
/* 12365 */         break;
/*       */       case 22:
/* 12369 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12372 */         pushFollow(FOLLOW_insn_format22s_in_instruction3890);
/* 12373 */         insn_format22s254 = insn_format22s();
/* 12374 */         this.state._fsp -= 1;
/*       */ 
/* 12376 */         this.adaptor.addChild(root_0, insn_format22s254.getTree());
/*       */ 
/* 12379 */         break;
/*       */       case 23:
/* 12383 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12386 */         pushFollow(FOLLOW_insn_format22t_in_instruction3896);
/* 12387 */         insn_format22t255 = insn_format22t();
/* 12388 */         this.state._fsp -= 1;
/*       */ 
/* 12390 */         this.adaptor.addChild(root_0, insn_format22t255.getTree());
/*       */ 
/* 12393 */         break;
/*       */       case 24:
/* 12397 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12400 */         pushFollow(FOLLOW_insn_format22x_in_instruction3902);
/* 12401 */         insn_format22x256 = insn_format22x();
/* 12402 */         this.state._fsp -= 1;
/*       */ 
/* 12404 */         this.adaptor.addChild(root_0, insn_format22x256.getTree());
/*       */ 
/* 12407 */         break;
/*       */       case 25:
/* 12411 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12414 */         pushFollow(FOLLOW_insn_format23x_in_instruction3908);
/* 12415 */         insn_format23x257 = insn_format23x();
/* 12416 */         this.state._fsp -= 1;
/*       */ 
/* 12418 */         this.adaptor.addChild(root_0, insn_format23x257.getTree());
/*       */ 
/* 12421 */         break;
/*       */       case 26:
/* 12425 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12428 */         pushFollow(FOLLOW_insn_format30t_in_instruction3914);
/* 12429 */         insn_format30t258 = insn_format30t();
/* 12430 */         this.state._fsp -= 1;
/*       */ 
/* 12432 */         this.adaptor.addChild(root_0, insn_format30t258.getTree());
/*       */ 
/* 12435 */         break;
/*       */       case 27:
/* 12439 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12442 */         pushFollow(FOLLOW_insn_format31c_in_instruction3920);
/* 12443 */         insn_format31c259 = insn_format31c();
/* 12444 */         this.state._fsp -= 1;
/*       */ 
/* 12446 */         this.adaptor.addChild(root_0, insn_format31c259.getTree());
/*       */ 
/* 12449 */         break;
/*       */       case 28:
/* 12453 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12456 */         pushFollow(FOLLOW_insn_format31i_in_instruction3926);
/* 12457 */         insn_format31i260 = insn_format31i();
/* 12458 */         this.state._fsp -= 1;
/*       */ 
/* 12460 */         this.adaptor.addChild(root_0, insn_format31i260.getTree());
/*       */ 
/* 12463 */         break;
/*       */       case 29:
/* 12467 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12470 */         pushFollow(FOLLOW_insn_format31t_in_instruction3932);
/* 12471 */         insn_format31t261 = insn_format31t();
/* 12472 */         this.state._fsp -= 1;
/*       */ 
/* 12474 */         this.adaptor.addChild(root_0, insn_format31t261.getTree());
/*       */ 
/* 12477 */         break;
/*       */       case 30:
/* 12481 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12484 */         pushFollow(FOLLOW_insn_format32x_in_instruction3938);
/* 12485 */         insn_format32x262 = insn_format32x();
/* 12486 */         this.state._fsp -= 1;
/*       */ 
/* 12488 */         this.adaptor.addChild(root_0, insn_format32x262.getTree());
/*       */ 
/* 12491 */         break;
/*       */       case 31:
/* 12495 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12498 */         pushFollow(FOLLOW_insn_format35c_method_in_instruction3944);
/* 12499 */         insn_format35c_method263 = insn_format35c_method();
/* 12500 */         this.state._fsp -= 1;
/*       */ 
/* 12502 */         this.adaptor.addChild(root_0, insn_format35c_method263.getTree());
/*       */ 
/* 12505 */         break;
/*       */       case 32:
/* 12509 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12512 */         pushFollow(FOLLOW_insn_format35c_type_in_instruction3950);
/* 12513 */         insn_format35c_type264 = insn_format35c_type();
/* 12514 */         this.state._fsp -= 1;
/*       */ 
/* 12516 */         this.adaptor.addChild(root_0, insn_format35c_type264.getTree());
/*       */ 
/* 12519 */         break;
/*       */       case 33:
/* 12523 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12526 */         pushFollow(FOLLOW_insn_format35c_method_odex_in_instruction3956);
/* 12527 */         insn_format35c_method_odex265 = insn_format35c_method_odex();
/* 12528 */         this.state._fsp -= 1;
/*       */ 
/* 12530 */         this.adaptor.addChild(root_0, insn_format35c_method_odex265.getTree());
/*       */ 
/* 12533 */         break;
/*       */       case 34:
/* 12537 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12540 */         pushFollow(FOLLOW_insn_format35mi_method_in_instruction3962);
/* 12541 */         insn_format35mi_method266 = insn_format35mi_method();
/* 12542 */         this.state._fsp -= 1;
/*       */ 
/* 12544 */         this.adaptor.addChild(root_0, insn_format35mi_method266.getTree());
/*       */ 
/* 12547 */         break;
/*       */       case 35:
/* 12551 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12554 */         pushFollow(FOLLOW_insn_format35ms_method_in_instruction3968);
/* 12555 */         insn_format35ms_method267 = insn_format35ms_method();
/* 12556 */         this.state._fsp -= 1;
/*       */ 
/* 12558 */         this.adaptor.addChild(root_0, insn_format35ms_method267.getTree());
/*       */ 
/* 12561 */         break;
/*       */       case 36:
/* 12565 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12568 */         pushFollow(FOLLOW_insn_format3rc_method_in_instruction3974);
/* 12569 */         insn_format3rc_method268 = insn_format3rc_method();
/* 12570 */         this.state._fsp -= 1;
/*       */ 
/* 12572 */         this.adaptor.addChild(root_0, insn_format3rc_method268.getTree());
/*       */ 
/* 12575 */         break;
/*       */       case 37:
/* 12579 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12582 */         pushFollow(FOLLOW_insn_format3rc_method_odex_in_instruction3980);
/* 12583 */         insn_format3rc_method_odex269 = insn_format3rc_method_odex();
/* 12584 */         this.state._fsp -= 1;
/*       */ 
/* 12586 */         this.adaptor.addChild(root_0, insn_format3rc_method_odex269.getTree());
/*       */ 
/* 12589 */         break;
/*       */       case 38:
/* 12593 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12596 */         pushFollow(FOLLOW_insn_format3rc_type_in_instruction3986);
/* 12597 */         insn_format3rc_type270 = insn_format3rc_type();
/* 12598 */         this.state._fsp -= 1;
/*       */ 
/* 12600 */         this.adaptor.addChild(root_0, insn_format3rc_type270.getTree());
/*       */ 
/* 12603 */         break;
/*       */       case 39:
/* 12607 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12610 */         pushFollow(FOLLOW_insn_format3rmi_method_in_instruction3992);
/* 12611 */         insn_format3rmi_method271 = insn_format3rmi_method();
/* 12612 */         this.state._fsp -= 1;
/*       */ 
/* 12614 */         this.adaptor.addChild(root_0, insn_format3rmi_method271.getTree());
/*       */ 
/* 12617 */         break;
/*       */       case 40:
/* 12621 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12624 */         pushFollow(FOLLOW_insn_format3rms_method_in_instruction3998);
/* 12625 */         insn_format3rms_method272 = insn_format3rms_method();
/* 12626 */         this.state._fsp -= 1;
/*       */ 
/* 12628 */         this.adaptor.addChild(root_0, insn_format3rms_method272.getTree());
/*       */ 
/* 12631 */         break;
/*       */       case 41:
/* 12635 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12638 */         pushFollow(FOLLOW_insn_format51l_in_instruction4004);
/* 12639 */         insn_format51l273 = insn_format51l();
/* 12640 */         this.state._fsp -= 1;
/*       */ 
/* 12642 */         this.adaptor.addChild(root_0, insn_format51l273.getTree());
/*       */ 
/* 12645 */         break;
/*       */       case 42:
/* 12649 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12652 */         pushFollow(FOLLOW_insn_array_data_directive_in_instruction4010);
/* 12653 */         insn_array_data_directive274 = insn_array_data_directive();
/* 12654 */         this.state._fsp -= 1;
/*       */ 
/* 12656 */         this.adaptor.addChild(root_0, insn_array_data_directive274.getTree());
/*       */ 
/* 12659 */         break;
/*       */       case 43:
/* 12663 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12666 */         pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction4016);
/* 12667 */         insn_packed_switch_directive275 = insn_packed_switch_directive();
/* 12668 */         this.state._fsp -= 1;
/*       */ 
/* 12670 */         this.adaptor.addChild(root_0, insn_packed_switch_directive275.getTree());
/*       */ 
/* 12673 */         break;
/*       */       case 44:
/* 12677 */         root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12680 */         pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction4022);
/* 12681 */         insn_sparse_switch_directive276 = insn_sparse_switch_directive();
/* 12682 */         this.state._fsp -= 1;
/*       */ 
/* 12684 */         this.adaptor.addChild(root_0, insn_sparse_switch_directive276.getTree());
/*       */       }
/*       */ 
/* 12690 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 12692 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 12693 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 12697 */       reportError(re);
/* 12698 */       recover(this.input, re);
/* 12699 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 12704 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format10t_return insn_format10t()
/*       */     throws RecognitionException
/*       */   {
/* 12719 */     insn_format10t_return retval = new insn_format10t_return();
/* 12720 */     retval.start = this.input.LT(1);
/*       */ 
/* 12722 */     CommonTree root_0 = null;
/*       */ 
/* 12724 */     Token INSTRUCTION_FORMAT10t277 = null;
/* 12725 */     ParserRuleReturnScope label_ref278 = null;
/*       */ 
/* 12727 */     CommonTree INSTRUCTION_FORMAT10t277_tree = null;
/* 12728 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT10t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10t");
/* 12729 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 12735 */       INSTRUCTION_FORMAT10t277 = (Token)match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4042);
/* 12736 */       stream_INSTRUCTION_FORMAT10t.add(INSTRUCTION_FORMAT10t277);
/*       */ 
/* 12738 */       pushFollow(FOLLOW_label_ref_in_insn_format10t4044);
/* 12739 */       label_ref278 = label_ref();
/* 12740 */       this.state._fsp -= 1;
/*       */ 
/* 12742 */       stream_label_ref.add(label_ref278.getTree());
/*       */ 
/* 12750 */       retval.tree = root_0;
/* 12751 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 12753 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12758 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 12759 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(147, retval.start, "I_STATEMENT_FORMAT10t"), root_1);
/* 12760 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT10t.nextNode());
/* 12761 */       this.adaptor.addChild(root_1, stream_label_ref.nextTree());
/* 12762 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 12768 */       retval.tree = root_0;
/*       */ 
/* 12772 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 12774 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 12775 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 12779 */       reportError(re);
/* 12780 */       recover(this.input, re);
/* 12781 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 12786 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format10x_return insn_format10x()
/*       */     throws RecognitionException
/*       */   {
/* 12801 */     insn_format10x_return retval = new insn_format10x_return();
/* 12802 */     retval.start = this.input.LT(1);
/*       */ 
/* 12804 */     CommonTree root_0 = null;
/*       */ 
/* 12806 */     Token INSTRUCTION_FORMAT10x279 = null;
/*       */ 
/* 12808 */     CommonTree INSTRUCTION_FORMAT10x279_tree = null;
/* 12809 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT10x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT10x");
/*       */     try
/*       */     {
/* 12815 */       INSTRUCTION_FORMAT10x279 = (Token)match(this.input, 59, FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4074);
/* 12816 */       stream_INSTRUCTION_FORMAT10x.add(INSTRUCTION_FORMAT10x279);
/*       */ 
/* 12825 */       retval.tree = root_0;
/* 12826 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 12828 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12833 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 12834 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(148, retval.start, "I_STATEMENT_FORMAT10x"), root_1);
/* 12835 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT10x.nextNode());
/* 12836 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 12842 */       retval.tree = root_0;
/*       */ 
/* 12846 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 12848 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 12849 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 12853 */       reportError(re);
/* 12854 */       recover(this.input, re);
/* 12855 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 12860 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format10x_odex_return insn_format10x_odex()
/*       */     throws RecognitionException
/*       */   {
/* 12875 */     insn_format10x_odex_return retval = new insn_format10x_odex_return();
/* 12876 */     retval.start = this.input.LT(1);
/*       */ 
/* 12878 */     CommonTree root_0 = null;
/*       */ 
/* 12880 */     Token INSTRUCTION_FORMAT10x_ODEX280 = null;
/*       */ 
/* 12882 */     CommonTree INSTRUCTION_FORMAT10x_ODEX280_tree = null;
/*       */     try
/*       */     {
/* 12888 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12891 */       INSTRUCTION_FORMAT10x_ODEX280 = (Token)match(this.input, 60, FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4102);
/* 12892 */       INSTRUCTION_FORMAT10x_ODEX280_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT10x_ODEX280);
/* 12893 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT10x_ODEX280_tree);
/*       */ 
/* 12896 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT10x_ODEX280 != null ? INSTRUCTION_FORMAT10x_ODEX280.getText() : null);
/*       */ 
/* 12900 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 12902 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 12903 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 12907 */       reportError(re);
/* 12908 */       recover(this.input, re);
/* 12909 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 12914 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format11n_return insn_format11n()
/*       */     throws RecognitionException
/*       */   {
/* 12929 */     insn_format11n_return retval = new insn_format11n_return();
/* 12930 */     retval.start = this.input.LT(1);
/*       */ 
/* 12932 */     CommonTree root_0 = null;
/*       */ 
/* 12934 */     Token INSTRUCTION_FORMAT11n281 = null;
/* 12935 */     Token REGISTER282 = null;
/* 12936 */     Token COMMA283 = null;
/* 12937 */     ParserRuleReturnScope integral_literal284 = null;
/*       */ 
/* 12939 */     CommonTree INSTRUCTION_FORMAT11n281_tree = null;
/* 12940 */     CommonTree REGISTER282_tree = null;
/* 12941 */     CommonTree COMMA283_tree = null;
/* 12942 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 12943 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 12944 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT11n = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11n");
/* 12945 */     RewriteRuleSubtreeStream stream_integral_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
/*       */     try
/*       */     {
/* 12951 */       INSTRUCTION_FORMAT11n281 = (Token)match(this.input, 61, FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4123);
/* 12952 */       stream_INSTRUCTION_FORMAT11n.add(INSTRUCTION_FORMAT11n281);
/*       */ 
/* 12954 */       REGISTER282 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format11n4125);
/* 12955 */       stream_REGISTER.add(REGISTER282);
/*       */ 
/* 12957 */       COMMA283 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format11n4127);
/* 12958 */       stream_COMMA.add(COMMA283);
/*       */ 
/* 12960 */       pushFollow(FOLLOW_integral_literal_in_insn_format11n4129);
/* 12961 */       integral_literal284 = integral_literal();
/* 12962 */       this.state._fsp -= 1;
/*       */ 
/* 12964 */       stream_integral_literal.add(integral_literal284.getTree());
/*       */ 
/* 12972 */       retval.tree = root_0;
/* 12973 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 12975 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 12980 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 12981 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(149, retval.start, "I_STATEMENT_FORMAT11n"), root_1);
/* 12982 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT11n.nextNode());
/* 12983 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 12984 */       this.adaptor.addChild(root_1, stream_integral_literal.nextTree());
/* 12985 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 12991 */       retval.tree = root_0;
/*       */ 
/* 12995 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 12997 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 12998 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13002 */       reportError(re);
/* 13003 */       recover(this.input, re);
/* 13004 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13009 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format11x_return insn_format11x()
/*       */     throws RecognitionException
/*       */   {
/* 13024 */     insn_format11x_return retval = new insn_format11x_return();
/* 13025 */     retval.start = this.input.LT(1);
/*       */ 
/* 13027 */     CommonTree root_0 = null;
/*       */ 
/* 13029 */     Token INSTRUCTION_FORMAT11x285 = null;
/* 13030 */     Token REGISTER286 = null;
/*       */ 
/* 13032 */     CommonTree INSTRUCTION_FORMAT11x285_tree = null;
/* 13033 */     CommonTree REGISTER286_tree = null;
/* 13034 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT11x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT11x");
/* 13035 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/* 13041 */       INSTRUCTION_FORMAT11x285 = (Token)match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4161);
/* 13042 */       stream_INSTRUCTION_FORMAT11x.add(INSTRUCTION_FORMAT11x285);
/*       */ 
/* 13044 */       REGISTER286 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format11x4163);
/* 13045 */       stream_REGISTER.add(REGISTER286);
/*       */ 
/* 13054 */       retval.tree = root_0;
/* 13055 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13057 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13062 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13063 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(150, retval.start, "I_STATEMENT_FORMAT11x"), root_1);
/* 13064 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT11x.nextNode());
/* 13065 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13066 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13072 */       retval.tree = root_0;
/*       */ 
/* 13076 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13078 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13079 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13083 */       reportError(re);
/* 13084 */       recover(this.input, re);
/* 13085 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13090 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format12x_return insn_format12x()
/*       */     throws RecognitionException
/*       */   {
/* 13105 */     insn_format12x_return retval = new insn_format12x_return();
/* 13106 */     retval.start = this.input.LT(1);
/*       */ 
/* 13108 */     CommonTree root_0 = null;
/*       */ 
/* 13110 */     Token REGISTER288 = null;
/* 13111 */     Token COMMA289 = null;
/* 13112 */     Token REGISTER290 = null;
/* 13113 */     ParserRuleReturnScope instruction_format12x287 = null;
/*       */ 
/* 13115 */     CommonTree REGISTER288_tree = null;
/* 13116 */     CommonTree COMMA289_tree = null;
/* 13117 */     CommonTree REGISTER290_tree = null;
/* 13118 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13119 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 13120 */     RewriteRuleSubtreeStream stream_instruction_format12x = new RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format12x");
/*       */     try
/*       */     {
/* 13126 */       pushFollow(FOLLOW_instruction_format12x_in_insn_format12x4193);
/* 13127 */       instruction_format12x287 = instruction_format12x();
/* 13128 */       this.state._fsp -= 1;
/*       */ 
/* 13130 */       stream_instruction_format12x.add(instruction_format12x287.getTree());
/* 13131 */       REGISTER288 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format12x4195);
/* 13132 */       stream_REGISTER.add(REGISTER288);
/*       */ 
/* 13134 */       COMMA289 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format12x4197);
/* 13135 */       stream_COMMA.add(COMMA289);
/*       */ 
/* 13137 */       REGISTER290 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format12x4199);
/* 13138 */       stream_REGISTER.add(REGISTER290);
/*       */ 
/* 13147 */       retval.tree = root_0;
/* 13148 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13150 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13155 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13156 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(151, retval.start, "I_STATEMENT_FORMAT12x"), root_1);
/* 13157 */       this.adaptor.addChild(root_1, stream_instruction_format12x.nextTree());
/* 13158 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13159 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13160 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13166 */       retval.tree = root_0;
/*       */ 
/* 13170 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13172 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13173 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13177 */       reportError(re);
/* 13178 */       recover(this.input, re);
/* 13179 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13184 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format20bc_return insn_format20bc()
/*       */     throws RecognitionException
/*       */   {
/* 13199 */     insn_format20bc_return retval = new insn_format20bc_return();
/* 13200 */     retval.start = this.input.LT(1);
/*       */ 
/* 13202 */     CommonTree root_0 = null;
/*       */ 
/* 13204 */     Token INSTRUCTION_FORMAT20bc291 = null;
/* 13205 */     Token VERIFICATION_ERROR_TYPE292 = null;
/* 13206 */     Token COMMA293 = null;
/* 13207 */     ParserRuleReturnScope verification_error_reference294 = null;
/*       */ 
/* 13209 */     CommonTree INSTRUCTION_FORMAT20bc291_tree = null;
/* 13210 */     CommonTree VERIFICATION_ERROR_TYPE292_tree = null;
/* 13211 */     CommonTree COMMA293_tree = null;
/* 13212 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT20bc = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT20bc");
/* 13213 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13214 */     RewriteRuleTokenStream stream_VERIFICATION_ERROR_TYPE = new RewriteRuleTokenStream(this.adaptor, "token VERIFICATION_ERROR_TYPE");
/* 13215 */     RewriteRuleSubtreeStream stream_verification_error_reference = new RewriteRuleSubtreeStream(this.adaptor, "rule verification_error_reference");
/*       */     try
/*       */     {
/* 13221 */       INSTRUCTION_FORMAT20bc291 = (Token)match(this.input, 65, FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4231);
/* 13222 */       stream_INSTRUCTION_FORMAT20bc.add(INSTRUCTION_FORMAT20bc291);
/*       */ 
/* 13224 */       VERIFICATION_ERROR_TYPE292 = (Token)match(this.input, 211, FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4233);
/* 13225 */       stream_VERIFICATION_ERROR_TYPE.add(VERIFICATION_ERROR_TYPE292);
/*       */ 
/* 13227 */       COMMA293 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format20bc4235);
/* 13228 */       stream_COMMA.add(COMMA293);
/*       */ 
/* 13230 */       pushFollow(FOLLOW_verification_error_reference_in_insn_format20bc4237);
/* 13231 */       verification_error_reference294 = verification_error_reference();
/* 13232 */       this.state._fsp -= 1;
/*       */ 
/* 13234 */       stream_verification_error_reference.add(verification_error_reference294.getTree());
/*       */ 
/* 13236 */       if (this.allowOdex) { if ((this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT20bc291 != null ? INSTRUCTION_FORMAT20bc291.getText() : null) != null) && (this.apiLevel < 14)); } else throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT20bc291 != null ? INSTRUCTION_FORMAT20bc291.getText() : null);
/*       */ 
/* 13247 */       retval.tree = root_0;
/* 13248 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13250 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13255 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13256 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(152, "I_STATEMENT_FORMAT20bc"), root_1);
/* 13257 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT20bc.nextNode());
/* 13258 */       this.adaptor.addChild(root_1, stream_VERIFICATION_ERROR_TYPE.nextNode());
/* 13259 */       this.adaptor.addChild(root_1, stream_verification_error_reference.nextTree());
/* 13260 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13266 */       retval.tree = root_0;
/*       */ 
/* 13270 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13272 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13273 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13277 */       reportError(re);
/* 13278 */       recover(this.input, re);
/* 13279 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13284 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format20t_return insn_format20t()
/*       */     throws RecognitionException
/*       */   {
/* 13299 */     insn_format20t_return retval = new insn_format20t_return();
/* 13300 */     retval.start = this.input.LT(1);
/*       */ 
/* 13302 */     CommonTree root_0 = null;
/*       */ 
/* 13304 */     Token INSTRUCTION_FORMAT20t295 = null;
/* 13305 */     ParserRuleReturnScope label_ref296 = null;
/*       */ 
/* 13307 */     CommonTree INSTRUCTION_FORMAT20t295_tree = null;
/* 13308 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT20t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT20t");
/* 13309 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 13315 */       INSTRUCTION_FORMAT20t295 = (Token)match(this.input, 66, FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4274);
/* 13316 */       stream_INSTRUCTION_FORMAT20t.add(INSTRUCTION_FORMAT20t295);
/*       */ 
/* 13318 */       pushFollow(FOLLOW_label_ref_in_insn_format20t4276);
/* 13319 */       label_ref296 = label_ref();
/* 13320 */       this.state._fsp -= 1;
/*       */ 
/* 13322 */       stream_label_ref.add(label_ref296.getTree());
/*       */ 
/* 13330 */       retval.tree = root_0;
/* 13331 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13333 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13338 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13339 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(153, retval.start, "I_STATEMENT_FORMAT20t"), root_1);
/* 13340 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT20t.nextNode());
/* 13341 */       this.adaptor.addChild(root_1, stream_label_ref.nextTree());
/* 13342 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13348 */       retval.tree = root_0;
/*       */ 
/* 13352 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13354 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13355 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13359 */       reportError(re);
/* 13360 */       recover(this.input, re);
/* 13361 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13366 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21c_field_return insn_format21c_field()
/*       */     throws RecognitionException
/*       */   {
/* 13381 */     insn_format21c_field_return retval = new insn_format21c_field_return();
/* 13382 */     retval.start = this.input.LT(1);
/*       */ 
/* 13384 */     CommonTree root_0 = null;
/*       */ 
/* 13386 */     Token INSTRUCTION_FORMAT21c_FIELD297 = null;
/* 13387 */     Token REGISTER298 = null;
/* 13388 */     Token COMMA299 = null;
/* 13389 */     ParserRuleReturnScope fully_qualified_field300 = null;
/*       */ 
/* 13391 */     CommonTree INSTRUCTION_FORMAT21c_FIELD297_tree = null;
/* 13392 */     CommonTree REGISTER298_tree = null;
/* 13393 */     CommonTree COMMA299_tree = null;
/* 13394 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13395 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_FIELD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD");
/* 13396 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 13397 */     RewriteRuleSubtreeStream stream_fully_qualified_field = new RewriteRuleSubtreeStream(this.adaptor, "rule fully_qualified_field");
/*       */     try
/*       */     {
/* 13403 */       INSTRUCTION_FORMAT21c_FIELD297 = (Token)match(this.input, 67, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4306);
/* 13404 */       stream_INSTRUCTION_FORMAT21c_FIELD.add(INSTRUCTION_FORMAT21c_FIELD297);
/*       */ 
/* 13406 */       REGISTER298 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21c_field4308);
/* 13407 */       stream_REGISTER.add(REGISTER298);
/*       */ 
/* 13409 */       COMMA299 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21c_field4310);
/* 13410 */       stream_COMMA.add(COMMA299);
/*       */ 
/* 13412 */       pushFollow(FOLLOW_fully_qualified_field_in_insn_format21c_field4312);
/* 13413 */       fully_qualified_field300 = fully_qualified_field();
/* 13414 */       this.state._fsp -= 1;
/*       */ 
/* 13416 */       stream_fully_qualified_field.add(fully_qualified_field300.getTree());
/*       */ 
/* 13424 */       retval.tree = root_0;
/* 13425 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13427 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13432 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13433 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(154, retval.start, "I_STATEMENT_FORMAT21c_FIELD"), root_1);
/* 13434 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21c_FIELD.nextNode());
/* 13435 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13436 */       this.adaptor.addChild(root_1, stream_fully_qualified_field.nextTree());
/* 13437 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13443 */       retval.tree = root_0;
/*       */ 
/* 13447 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13449 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13450 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13454 */       reportError(re);
/* 13455 */       recover(this.input, re);
/* 13456 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13461 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21c_field_odex_return insn_format21c_field_odex()
/*       */     throws RecognitionException
/*       */   {
/* 13476 */     insn_format21c_field_odex_return retval = new insn_format21c_field_odex_return();
/* 13477 */     retval.start = this.input.LT(1);
/*       */ 
/* 13479 */     CommonTree root_0 = null;
/*       */ 
/* 13481 */     Token INSTRUCTION_FORMAT21c_FIELD_ODEX301 = null;
/* 13482 */     Token REGISTER302 = null;
/* 13483 */     Token COMMA303 = null;
/* 13484 */     ParserRuleReturnScope fully_qualified_field304 = null;
/*       */ 
/* 13486 */     CommonTree INSTRUCTION_FORMAT21c_FIELD_ODEX301_tree = null;
/* 13487 */     CommonTree REGISTER302_tree = null;
/* 13488 */     CommonTree COMMA303_tree = null;
/* 13489 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13490 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 13491 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_FIELD_ODEX = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_FIELD_ODEX");
/* 13492 */     RewriteRuleSubtreeStream stream_fully_qualified_field = new RewriteRuleSubtreeStream(this.adaptor, "rule fully_qualified_field");
/*       */     try
/*       */     {
/* 13498 */       INSTRUCTION_FORMAT21c_FIELD_ODEX301 = (Token)match(this.input, 68, FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4344);
/* 13499 */       stream_INSTRUCTION_FORMAT21c_FIELD_ODEX.add(INSTRUCTION_FORMAT21c_FIELD_ODEX301);
/*       */ 
/* 13501 */       REGISTER302 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21c_field_odex4346);
/* 13502 */       stream_REGISTER.add(REGISTER302);
/*       */ 
/* 13504 */       COMMA303 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21c_field_odex4348);
/* 13505 */       stream_COMMA.add(COMMA303);
/*       */ 
/* 13507 */       pushFollow(FOLLOW_fully_qualified_field_in_insn_format21c_field_odex4350);
/* 13508 */       fully_qualified_field304 = fully_qualified_field();
/* 13509 */       this.state._fsp -= 1;
/*       */ 
/* 13511 */       stream_fully_qualified_field.add(fully_qualified_field304.getTree());
/*       */ 
/* 13513 */       if (this.allowOdex) { if ((this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT21c_FIELD_ODEX301 != null ? INSTRUCTION_FORMAT21c_FIELD_ODEX301.getText() : null) != null) && (this.apiLevel < 14)); } else throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT21c_FIELD_ODEX301 != null ? INSTRUCTION_FORMAT21c_FIELD_ODEX301.getText() : null);
/*       */ 
/* 13524 */       retval.tree = root_0;
/* 13525 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13527 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13532 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13533 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(154, retval.start, "I_STATEMENT_FORMAT21c_FIELD"), root_1);
/* 13534 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21c_FIELD_ODEX.nextNode());
/* 13535 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13536 */       this.adaptor.addChild(root_1, stream_fully_qualified_field.nextTree());
/* 13537 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13543 */       retval.tree = root_0;
/*       */ 
/* 13547 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13549 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13550 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13554 */       reportError(re);
/* 13555 */       recover(this.input, re);
/* 13556 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13561 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21c_string_return insn_format21c_string()
/*       */     throws RecognitionException
/*       */   {
/* 13576 */     insn_format21c_string_return retval = new insn_format21c_string_return();
/* 13577 */     retval.start = this.input.LT(1);
/*       */ 
/* 13579 */     CommonTree root_0 = null;
/*       */ 
/* 13581 */     Token INSTRUCTION_FORMAT21c_STRING305 = null;
/* 13582 */     Token REGISTER306 = null;
/* 13583 */     Token COMMA307 = null;
/* 13584 */     Token STRING_LITERAL308 = null;
/*       */ 
/* 13586 */     CommonTree INSTRUCTION_FORMAT21c_STRING305_tree = null;
/* 13587 */     CommonTree REGISTER306_tree = null;
/* 13588 */     CommonTree COMMA307_tree = null;
/* 13589 */     CommonTree STRING_LITERAL308_tree = null;
/* 13590 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_STRING = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_STRING");
/* 13591 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/* 13592 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13593 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/* 13599 */       INSTRUCTION_FORMAT21c_STRING305 = (Token)match(this.input, 69, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4388);
/* 13600 */       stream_INSTRUCTION_FORMAT21c_STRING.add(INSTRUCTION_FORMAT21c_STRING305);
/*       */ 
/* 13602 */       REGISTER306 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21c_string4390);
/* 13603 */       stream_REGISTER.add(REGISTER306);
/*       */ 
/* 13605 */       COMMA307 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21c_string4392);
/* 13606 */       stream_COMMA.add(COMMA307);
/*       */ 
/* 13608 */       STRING_LITERAL308 = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_insn_format21c_string4394);
/* 13609 */       stream_STRING_LITERAL.add(STRING_LITERAL308);
/*       */ 
/* 13618 */       retval.tree = root_0;
/* 13619 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13621 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13626 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13627 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(155, retval.start, "I_STATEMENT_FORMAT21c_STRING"), root_1);
/* 13628 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21c_STRING.nextNode());
/* 13629 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13630 */       this.adaptor.addChild(root_1, stream_STRING_LITERAL.nextNode());
/* 13631 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13637 */       retval.tree = root_0;
/*       */ 
/* 13641 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13643 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13644 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13648 */       reportError(re);
/* 13649 */       recover(this.input, re);
/* 13650 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13655 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21c_type_return insn_format21c_type()
/*       */     throws RecognitionException
/*       */   {
/* 13670 */     insn_format21c_type_return retval = new insn_format21c_type_return();
/* 13671 */     retval.start = this.input.LT(1);
/*       */ 
/* 13673 */     CommonTree root_0 = null;
/*       */ 
/* 13675 */     Token INSTRUCTION_FORMAT21c_TYPE309 = null;
/* 13676 */     Token REGISTER310 = null;
/* 13677 */     Token COMMA311 = null;
/* 13678 */     ParserRuleReturnScope nonvoid_type_descriptor312 = null;
/*       */ 
/* 13680 */     CommonTree INSTRUCTION_FORMAT21c_TYPE309_tree = null;
/* 13681 */     CommonTree REGISTER310_tree = null;
/* 13682 */     CommonTree COMMA311_tree = null;
/* 13683 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13684 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21c_TYPE = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21c_TYPE");
/* 13685 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 13686 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/*       */     try
/*       */     {
/* 13692 */       INSTRUCTION_FORMAT21c_TYPE309 = (Token)match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4426);
/* 13693 */       stream_INSTRUCTION_FORMAT21c_TYPE.add(INSTRUCTION_FORMAT21c_TYPE309);
/*       */ 
/* 13695 */       REGISTER310 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21c_type4428);
/* 13696 */       stream_REGISTER.add(REGISTER310);
/*       */ 
/* 13698 */       COMMA311 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21c_type4430);
/* 13699 */       stream_COMMA.add(COMMA311);
/*       */ 
/* 13701 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4432);
/* 13702 */       nonvoid_type_descriptor312 = nonvoid_type_descriptor();
/* 13703 */       this.state._fsp -= 1;
/*       */ 
/* 13705 */       stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor312.getTree());
/*       */ 
/* 13713 */       retval.tree = root_0;
/* 13714 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13716 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13721 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13722 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(156, retval.start, "I_STATEMENT_FORMAT21c"), root_1);
/* 13723 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21c_TYPE.nextNode());
/* 13724 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13725 */       this.adaptor.addChild(root_1, stream_nonvoid_type_descriptor.nextTree());
/* 13726 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13732 */       retval.tree = root_0;
/*       */ 
/* 13736 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13738 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13739 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13743 */       reportError(re);
/* 13744 */       recover(this.input, re);
/* 13745 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13750 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21ih_return insn_format21ih()
/*       */     throws RecognitionException
/*       */   {
/* 13765 */     insn_format21ih_return retval = new insn_format21ih_return();
/* 13766 */     retval.start = this.input.LT(1);
/*       */ 
/* 13768 */     CommonTree root_0 = null;
/*       */ 
/* 13770 */     Token INSTRUCTION_FORMAT21ih313 = null;
/* 13771 */     Token REGISTER314 = null;
/* 13772 */     Token COMMA315 = null;
/* 13773 */     ParserRuleReturnScope fixed_32bit_literal316 = null;
/*       */ 
/* 13775 */     CommonTree INSTRUCTION_FORMAT21ih313_tree = null;
/* 13776 */     CommonTree REGISTER314_tree = null;
/* 13777 */     CommonTree COMMA315_tree = null;
/* 13778 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21ih = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21ih");
/* 13779 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13780 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 13781 */     RewriteRuleSubtreeStream stream_fixed_32bit_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
/*       */     try
/*       */     {
/* 13787 */       INSTRUCTION_FORMAT21ih313 = (Token)match(this.input, 71, FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4464);
/* 13788 */       stream_INSTRUCTION_FORMAT21ih.add(INSTRUCTION_FORMAT21ih313);
/*       */ 
/* 13790 */       REGISTER314 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21ih4466);
/* 13791 */       stream_REGISTER.add(REGISTER314);
/*       */ 
/* 13793 */       COMMA315 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21ih4468);
/* 13794 */       stream_COMMA.add(COMMA315);
/*       */ 
/* 13796 */       pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21ih4470);
/* 13797 */       fixed_32bit_literal316 = fixed_32bit_literal();
/* 13798 */       this.state._fsp -= 1;
/*       */ 
/* 13800 */       stream_fixed_32bit_literal.add(fixed_32bit_literal316.getTree());
/*       */ 
/* 13808 */       retval.tree = root_0;
/* 13809 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13811 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13816 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13817 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(157, retval.start, "I_STATEMENT_FORMAT21ih"), root_1);
/* 13818 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21ih.nextNode());
/* 13819 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13820 */       this.adaptor.addChild(root_1, stream_fixed_32bit_literal.nextTree());
/* 13821 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13827 */       retval.tree = root_0;
/*       */ 
/* 13831 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13833 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13834 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13838 */       reportError(re);
/* 13839 */       recover(this.input, re);
/* 13840 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13845 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21lh_return insn_format21lh()
/*       */     throws RecognitionException
/*       */   {
/* 13860 */     insn_format21lh_return retval = new insn_format21lh_return();
/* 13861 */     retval.start = this.input.LT(1);
/*       */ 
/* 13863 */     CommonTree root_0 = null;
/*       */ 
/* 13865 */     Token INSTRUCTION_FORMAT21lh317 = null;
/* 13866 */     Token REGISTER318 = null;
/* 13867 */     Token COMMA319 = null;
/* 13868 */     ParserRuleReturnScope fixed_32bit_literal320 = null;
/*       */ 
/* 13870 */     CommonTree INSTRUCTION_FORMAT21lh317_tree = null;
/* 13871 */     CommonTree REGISTER318_tree = null;
/* 13872 */     CommonTree COMMA319_tree = null;
/* 13873 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21lh = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21lh");
/* 13874 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13875 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 13876 */     RewriteRuleSubtreeStream stream_fixed_32bit_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
/*       */     try
/*       */     {
/* 13882 */       INSTRUCTION_FORMAT21lh317 = (Token)match(this.input, 72, FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4502);
/* 13883 */       stream_INSTRUCTION_FORMAT21lh.add(INSTRUCTION_FORMAT21lh317);
/*       */ 
/* 13885 */       REGISTER318 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21lh4504);
/* 13886 */       stream_REGISTER.add(REGISTER318);
/*       */ 
/* 13888 */       COMMA319 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21lh4506);
/* 13889 */       stream_COMMA.add(COMMA319);
/*       */ 
/* 13891 */       pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21lh4508);
/* 13892 */       fixed_32bit_literal320 = fixed_32bit_literal();
/* 13893 */       this.state._fsp -= 1;
/*       */ 
/* 13895 */       stream_fixed_32bit_literal.add(fixed_32bit_literal320.getTree());
/*       */ 
/* 13903 */       retval.tree = root_0;
/* 13904 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 13906 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 13911 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 13912 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(158, retval.start, "I_STATEMENT_FORMAT21lh"), root_1);
/* 13913 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21lh.nextNode());
/* 13914 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 13915 */       this.adaptor.addChild(root_1, stream_fixed_32bit_literal.nextTree());
/* 13916 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 13922 */       retval.tree = root_0;
/*       */ 
/* 13926 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 13928 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 13929 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 13933 */       reportError(re);
/* 13934 */       recover(this.input, re);
/* 13935 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 13940 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21s_return insn_format21s()
/*       */     throws RecognitionException
/*       */   {
/* 13955 */     insn_format21s_return retval = new insn_format21s_return();
/* 13956 */     retval.start = this.input.LT(1);
/*       */ 
/* 13958 */     CommonTree root_0 = null;
/*       */ 
/* 13960 */     Token INSTRUCTION_FORMAT21s321 = null;
/* 13961 */     Token REGISTER322 = null;
/* 13962 */     Token COMMA323 = null;
/* 13963 */     ParserRuleReturnScope integral_literal324 = null;
/*       */ 
/* 13965 */     CommonTree INSTRUCTION_FORMAT21s321_tree = null;
/* 13966 */     CommonTree REGISTER322_tree = null;
/* 13967 */     CommonTree COMMA323_tree = null;
/* 13968 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21s = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21s");
/* 13969 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 13970 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 13971 */     RewriteRuleSubtreeStream stream_integral_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
/*       */     try
/*       */     {
/* 13977 */       INSTRUCTION_FORMAT21s321 = (Token)match(this.input, 73, FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s4540);
/* 13978 */       stream_INSTRUCTION_FORMAT21s.add(INSTRUCTION_FORMAT21s321);
/*       */ 
/* 13980 */       REGISTER322 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21s4542);
/* 13981 */       stream_REGISTER.add(REGISTER322);
/*       */ 
/* 13983 */       COMMA323 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21s4544);
/* 13984 */       stream_COMMA.add(COMMA323);
/*       */ 
/* 13986 */       pushFollow(FOLLOW_integral_literal_in_insn_format21s4546);
/* 13987 */       integral_literal324 = integral_literal();
/* 13988 */       this.state._fsp -= 1;
/*       */ 
/* 13990 */       stream_integral_literal.add(integral_literal324.getTree());
/*       */ 
/* 13998 */       retval.tree = root_0;
/* 13999 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14001 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14006 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14007 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(159, retval.start, "I_STATEMENT_FORMAT21s"), root_1);
/* 14008 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21s.nextNode());
/* 14009 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14010 */       this.adaptor.addChild(root_1, stream_integral_literal.nextTree());
/* 14011 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14017 */       retval.tree = root_0;
/*       */ 
/* 14021 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14023 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14024 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14028 */       reportError(re);
/* 14029 */       recover(this.input, re);
/* 14030 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14035 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format21t_return insn_format21t()
/*       */     throws RecognitionException
/*       */   {
/* 14050 */     insn_format21t_return retval = new insn_format21t_return();
/* 14051 */     retval.start = this.input.LT(1);
/*       */ 
/* 14053 */     CommonTree root_0 = null;
/*       */ 
/* 14055 */     Token INSTRUCTION_FORMAT21t325 = null;
/* 14056 */     Token REGISTER326 = null;
/* 14057 */     Token COMMA327 = null;
/* 14058 */     ParserRuleReturnScope label_ref328 = null;
/*       */ 
/* 14060 */     CommonTree INSTRUCTION_FORMAT21t325_tree = null;
/* 14061 */     CommonTree REGISTER326_tree = null;
/* 14062 */     CommonTree COMMA327_tree = null;
/* 14063 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT21t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT21t");
/* 14064 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14065 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14066 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 14072 */       INSTRUCTION_FORMAT21t325 = (Token)match(this.input, 74, FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t4578);
/* 14073 */       stream_INSTRUCTION_FORMAT21t.add(INSTRUCTION_FORMAT21t325);
/*       */ 
/* 14075 */       REGISTER326 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21t4580);
/* 14076 */       stream_REGISTER.add(REGISTER326);
/*       */ 
/* 14078 */       COMMA327 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format21t4582);
/* 14079 */       stream_COMMA.add(COMMA327);
/*       */ 
/* 14081 */       pushFollow(FOLLOW_label_ref_in_insn_format21t4584);
/* 14082 */       label_ref328 = label_ref();
/* 14083 */       this.state._fsp -= 1;
/*       */ 
/* 14085 */       stream_label_ref.add(label_ref328.getTree());
/*       */ 
/* 14093 */       retval.tree = root_0;
/* 14094 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14096 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14101 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14102 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(160, retval.start, "I_STATEMENT_FORMAT21t"), root_1);
/* 14103 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT21t.nextNode());
/* 14104 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14105 */       this.adaptor.addChild(root_1, stream_label_ref.nextTree());
/* 14106 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14112 */       retval.tree = root_0;
/*       */ 
/* 14116 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14118 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14119 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14123 */       reportError(re);
/* 14124 */       recover(this.input, re);
/* 14125 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14130 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22b_return insn_format22b()
/*       */     throws RecognitionException
/*       */   {
/* 14145 */     insn_format22b_return retval = new insn_format22b_return();
/* 14146 */     retval.start = this.input.LT(1);
/*       */ 
/* 14148 */     CommonTree root_0 = null;
/*       */ 
/* 14150 */     Token INSTRUCTION_FORMAT22b329 = null;
/* 14151 */     Token REGISTER330 = null;
/* 14152 */     Token COMMA331 = null;
/* 14153 */     Token REGISTER332 = null;
/* 14154 */     Token COMMA333 = null;
/* 14155 */     ParserRuleReturnScope integral_literal334 = null;
/*       */ 
/* 14157 */     CommonTree INSTRUCTION_FORMAT22b329_tree = null;
/* 14158 */     CommonTree REGISTER330_tree = null;
/* 14159 */     CommonTree COMMA331_tree = null;
/* 14160 */     CommonTree REGISTER332_tree = null;
/* 14161 */     CommonTree COMMA333_tree = null;
/* 14162 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14163 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14164 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22b = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22b");
/* 14165 */     RewriteRuleSubtreeStream stream_integral_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
/*       */     try
/*       */     {
/* 14171 */       INSTRUCTION_FORMAT22b329 = (Token)match(this.input, 75, FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b4616);
/* 14172 */       stream_INSTRUCTION_FORMAT22b.add(INSTRUCTION_FORMAT22b329);
/*       */ 
/* 14174 */       REGISTER330 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22b4618);
/* 14175 */       stream_REGISTER.add(REGISTER330);
/*       */ 
/* 14177 */       COMMA331 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22b4620);
/* 14178 */       stream_COMMA.add(COMMA331);
/*       */ 
/* 14180 */       REGISTER332 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22b4622);
/* 14181 */       stream_REGISTER.add(REGISTER332);
/*       */ 
/* 14183 */       COMMA333 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22b4624);
/* 14184 */       stream_COMMA.add(COMMA333);
/*       */ 
/* 14186 */       pushFollow(FOLLOW_integral_literal_in_insn_format22b4626);
/* 14187 */       integral_literal334 = integral_literal();
/* 14188 */       this.state._fsp -= 1;
/*       */ 
/* 14190 */       stream_integral_literal.add(integral_literal334.getTree());
/*       */ 
/* 14198 */       retval.tree = root_0;
/* 14199 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14201 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14206 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14207 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(161, retval.start, "I_STATEMENT_FORMAT22b"), root_1);
/* 14208 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT22b.nextNode());
/* 14209 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14210 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14211 */       this.adaptor.addChild(root_1, stream_integral_literal.nextTree());
/* 14212 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14218 */       retval.tree = root_0;
/*       */ 
/* 14222 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14224 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14225 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14229 */       reportError(re);
/* 14230 */       recover(this.input, re);
/* 14231 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14236 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22c_field_return insn_format22c_field()
/*       */     throws RecognitionException
/*       */   {
/* 14251 */     insn_format22c_field_return retval = new insn_format22c_field_return();
/* 14252 */     retval.start = this.input.LT(1);
/*       */ 
/* 14254 */     CommonTree root_0 = null;
/*       */ 
/* 14256 */     Token INSTRUCTION_FORMAT22c_FIELD335 = null;
/* 14257 */     Token REGISTER336 = null;
/* 14258 */     Token COMMA337 = null;
/* 14259 */     Token REGISTER338 = null;
/* 14260 */     Token COMMA339 = null;
/* 14261 */     ParserRuleReturnScope fully_qualified_field340 = null;
/*       */ 
/* 14263 */     CommonTree INSTRUCTION_FORMAT22c_FIELD335_tree = null;
/* 14264 */     CommonTree REGISTER336_tree = null;
/* 14265 */     CommonTree COMMA337_tree = null;
/* 14266 */     CommonTree REGISTER338_tree = null;
/* 14267 */     CommonTree COMMA339_tree = null;
/* 14268 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14269 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14270 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22c_FIELD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD");
/* 14271 */     RewriteRuleSubtreeStream stream_fully_qualified_field = new RewriteRuleSubtreeStream(this.adaptor, "rule fully_qualified_field");
/*       */     try
/*       */     {
/* 14277 */       INSTRUCTION_FORMAT22c_FIELD335 = (Token)match(this.input, 76, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field4660);
/* 14278 */       stream_INSTRUCTION_FORMAT22c_FIELD.add(INSTRUCTION_FORMAT22c_FIELD335);
/*       */ 
/* 14280 */       REGISTER336 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_field4662);
/* 14281 */       stream_REGISTER.add(REGISTER336);
/*       */ 
/* 14283 */       COMMA337 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22c_field4664);
/* 14284 */       stream_COMMA.add(COMMA337);
/*       */ 
/* 14286 */       REGISTER338 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_field4666);
/* 14287 */       stream_REGISTER.add(REGISTER338);
/*       */ 
/* 14289 */       COMMA339 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22c_field4668);
/* 14290 */       stream_COMMA.add(COMMA339);
/*       */ 
/* 14292 */       pushFollow(FOLLOW_fully_qualified_field_in_insn_format22c_field4670);
/* 14293 */       fully_qualified_field340 = fully_qualified_field();
/* 14294 */       this.state._fsp -= 1;
/*       */ 
/* 14296 */       stream_fully_qualified_field.add(fully_qualified_field340.getTree());
/*       */ 
/* 14304 */       retval.tree = root_0;
/* 14305 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14307 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14312 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14313 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(162, retval.start, "I_STATEMENT_FORMAT22c_FIELD"), root_1);
/* 14314 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT22c_FIELD.nextNode());
/* 14315 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14316 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14317 */       this.adaptor.addChild(root_1, stream_fully_qualified_field.nextTree());
/* 14318 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14324 */       retval.tree = root_0;
/*       */ 
/* 14328 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14330 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14331 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14335 */       reportError(re);
/* 14336 */       recover(this.input, re);
/* 14337 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14342 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22c_field_odex_return insn_format22c_field_odex()
/*       */     throws RecognitionException
/*       */   {
/* 14357 */     insn_format22c_field_odex_return retval = new insn_format22c_field_odex_return();
/* 14358 */     retval.start = this.input.LT(1);
/*       */ 
/* 14360 */     CommonTree root_0 = null;
/*       */ 
/* 14362 */     Token INSTRUCTION_FORMAT22c_FIELD_ODEX341 = null;
/* 14363 */     Token REGISTER342 = null;
/* 14364 */     Token COMMA343 = null;
/* 14365 */     Token REGISTER344 = null;
/* 14366 */     Token COMMA345 = null;
/* 14367 */     ParserRuleReturnScope fully_qualified_field346 = null;
/*       */ 
/* 14369 */     CommonTree INSTRUCTION_FORMAT22c_FIELD_ODEX341_tree = null;
/* 14370 */     CommonTree REGISTER342_tree = null;
/* 14371 */     CommonTree COMMA343_tree = null;
/* 14372 */     CommonTree REGISTER344_tree = null;
/* 14373 */     CommonTree COMMA345_tree = null;
/* 14374 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22c_FIELD_ODEX = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_FIELD_ODEX");
/* 14375 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14376 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14377 */     RewriteRuleSubtreeStream stream_fully_qualified_field = new RewriteRuleSubtreeStream(this.adaptor, "rule fully_qualified_field");
/*       */     try
/*       */     {
/* 14383 */       INSTRUCTION_FORMAT22c_FIELD_ODEX341 = (Token)match(this.input, 77, FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex4704);
/* 14384 */       stream_INSTRUCTION_FORMAT22c_FIELD_ODEX.add(INSTRUCTION_FORMAT22c_FIELD_ODEX341);
/*       */ 
/* 14386 */       REGISTER342 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_field_odex4706);
/* 14387 */       stream_REGISTER.add(REGISTER342);
/*       */ 
/* 14389 */       COMMA343 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22c_field_odex4708);
/* 14390 */       stream_COMMA.add(COMMA343);
/*       */ 
/* 14392 */       REGISTER344 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_field_odex4710);
/* 14393 */       stream_REGISTER.add(REGISTER344);
/*       */ 
/* 14395 */       COMMA345 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22c_field_odex4712);
/* 14396 */       stream_COMMA.add(COMMA345);
/*       */ 
/* 14398 */       pushFollow(FOLLOW_fully_qualified_field_in_insn_format22c_field_odex4714);
/* 14399 */       fully_qualified_field346 = fully_qualified_field();
/* 14400 */       this.state._fsp -= 1;
/*       */ 
/* 14402 */       stream_fully_qualified_field.add(fully_qualified_field346.getTree());
/*       */ 
/* 14404 */       if (this.allowOdex) { if ((this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT22c_FIELD_ODEX341 != null ? INSTRUCTION_FORMAT22c_FIELD_ODEX341.getText() : null) != null) && (this.apiLevel < 14)); } else throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT22c_FIELD_ODEX341 != null ? INSTRUCTION_FORMAT22c_FIELD_ODEX341.getText() : null);
/*       */ 
/* 14415 */       retval.tree = root_0;
/* 14416 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14418 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14423 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14424 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(162, retval.start, "I_STATEMENT_FORMAT22c_FIELD"), root_1);
/* 14425 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT22c_FIELD_ODEX.nextNode());
/* 14426 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14427 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14428 */       this.adaptor.addChild(root_1, stream_fully_qualified_field.nextTree());
/* 14429 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14435 */       retval.tree = root_0;
/*       */ 
/* 14439 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14441 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14442 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14446 */       reportError(re);
/* 14447 */       recover(this.input, re);
/* 14448 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14453 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22c_type_return insn_format22c_type()
/*       */     throws RecognitionException
/*       */   {
/* 14468 */     insn_format22c_type_return retval = new insn_format22c_type_return();
/* 14469 */     retval.start = this.input.LT(1);
/*       */ 
/* 14471 */     CommonTree root_0 = null;
/*       */ 
/* 14473 */     Token INSTRUCTION_FORMAT22c_TYPE347 = null;
/* 14474 */     Token REGISTER348 = null;
/* 14475 */     Token COMMA349 = null;
/* 14476 */     Token REGISTER350 = null;
/* 14477 */     Token COMMA351 = null;
/* 14478 */     ParserRuleReturnScope nonvoid_type_descriptor352 = null;
/*       */ 
/* 14480 */     CommonTree INSTRUCTION_FORMAT22c_TYPE347_tree = null;
/* 14481 */     CommonTree REGISTER348_tree = null;
/* 14482 */     CommonTree COMMA349_tree = null;
/* 14483 */     CommonTree REGISTER350_tree = null;
/* 14484 */     CommonTree COMMA351_tree = null;
/* 14485 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22c_TYPE = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22c_TYPE");
/* 14486 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14487 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14488 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/*       */     try
/*       */     {
/* 14494 */       INSTRUCTION_FORMAT22c_TYPE347 = (Token)match(this.input, 78, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type4754);
/* 14495 */       stream_INSTRUCTION_FORMAT22c_TYPE.add(INSTRUCTION_FORMAT22c_TYPE347);
/*       */ 
/* 14497 */       REGISTER348 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_type4756);
/* 14498 */       stream_REGISTER.add(REGISTER348);
/*       */ 
/* 14500 */       COMMA349 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22c_type4758);
/* 14501 */       stream_COMMA.add(COMMA349);
/*       */ 
/* 14503 */       REGISTER350 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_type4760);
/* 14504 */       stream_REGISTER.add(REGISTER350);
/*       */ 
/* 14506 */       COMMA351 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22c_type4762);
/* 14507 */       stream_COMMA.add(COMMA351);
/*       */ 
/* 14509 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type4764);
/* 14510 */       nonvoid_type_descriptor352 = nonvoid_type_descriptor();
/* 14511 */       this.state._fsp -= 1;
/*       */ 
/* 14513 */       stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor352.getTree());
/*       */ 
/* 14521 */       retval.tree = root_0;
/* 14522 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14524 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14529 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14530 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(163, retval.start, "I_STATEMENT_FORMAT22c_TYPE"), root_1);
/* 14531 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT22c_TYPE.nextNode());
/* 14532 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14533 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14534 */       this.adaptor.addChild(root_1, stream_nonvoid_type_descriptor.nextTree());
/* 14535 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14541 */       retval.tree = root_0;
/*       */ 
/* 14545 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14547 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14548 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14552 */       reportError(re);
/* 14553 */       recover(this.input, re);
/* 14554 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14559 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22cs_field_return insn_format22cs_field()
/*       */     throws RecognitionException
/*       */   {
/* 14574 */     insn_format22cs_field_return retval = new insn_format22cs_field_return();
/* 14575 */     retval.start = this.input.LT(1);
/*       */ 
/* 14577 */     CommonTree root_0 = null;
/*       */ 
/* 14579 */     Token INSTRUCTION_FORMAT22cs_FIELD353 = null;
/* 14580 */     Token REGISTER354 = null;
/* 14581 */     Token COMMA355 = null;
/* 14582 */     Token REGISTER356 = null;
/* 14583 */     Token COMMA357 = null;
/* 14584 */     Token FIELD_OFFSET358 = null;
/*       */ 
/* 14586 */     CommonTree INSTRUCTION_FORMAT22cs_FIELD353_tree = null;
/* 14587 */     CommonTree REGISTER354_tree = null;
/* 14588 */     CommonTree COMMA355_tree = null;
/* 14589 */     CommonTree REGISTER356_tree = null;
/* 14590 */     CommonTree COMMA357_tree = null;
/* 14591 */     CommonTree FIELD_OFFSET358_tree = null;
/*       */     try
/*       */     {
/* 14597 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14600 */       INSTRUCTION_FORMAT22cs_FIELD353 = (Token)match(this.input, 79, FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field4798);
/* 14601 */       INSTRUCTION_FORMAT22cs_FIELD353_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT22cs_FIELD353);
/* 14602 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT22cs_FIELD353_tree);
/*       */ 
/* 14604 */       REGISTER354 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22cs_field4800);
/* 14605 */       REGISTER354_tree = (CommonTree)this.adaptor.create(REGISTER354);
/* 14606 */       this.adaptor.addChild(root_0, REGISTER354_tree);
/*       */ 
/* 14608 */       COMMA355 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22cs_field4802);
/* 14609 */       COMMA355_tree = (CommonTree)this.adaptor.create(COMMA355);
/* 14610 */       this.adaptor.addChild(root_0, COMMA355_tree);
/*       */ 
/* 14612 */       REGISTER356 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22cs_field4804);
/* 14613 */       REGISTER356_tree = (CommonTree)this.adaptor.create(REGISTER356);
/* 14614 */       this.adaptor.addChild(root_0, REGISTER356_tree);
/*       */ 
/* 14616 */       COMMA357 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22cs_field4806);
/* 14617 */       COMMA357_tree = (CommonTree)this.adaptor.create(COMMA357);
/* 14618 */       this.adaptor.addChild(root_0, COMMA357_tree);
/*       */ 
/* 14620 */       FIELD_OFFSET358 = (Token)match(this.input, 50, FOLLOW_FIELD_OFFSET_in_insn_format22cs_field4808);
/* 14621 */       FIELD_OFFSET358_tree = (CommonTree)this.adaptor.create(FIELD_OFFSET358);
/* 14622 */       this.adaptor.addChild(root_0, FIELD_OFFSET358_tree);
/*       */ 
/* 14625 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT22cs_FIELD353 != null ? INSTRUCTION_FORMAT22cs_FIELD353.getText() : null);
/*       */ 
/* 14629 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14631 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14632 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14636 */       reportError(re);
/* 14637 */       recover(this.input, re);
/* 14638 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14643 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22s_return insn_format22s()
/*       */     throws RecognitionException
/*       */   {
/* 14658 */     insn_format22s_return retval = new insn_format22s_return();
/* 14659 */     retval.start = this.input.LT(1);
/*       */ 
/* 14661 */     CommonTree root_0 = null;
/*       */ 
/* 14663 */     Token REGISTER360 = null;
/* 14664 */     Token COMMA361 = null;
/* 14665 */     Token REGISTER362 = null;
/* 14666 */     Token COMMA363 = null;
/* 14667 */     ParserRuleReturnScope instruction_format22s359 = null;
/* 14668 */     ParserRuleReturnScope integral_literal364 = null;
/*       */ 
/* 14670 */     CommonTree REGISTER360_tree = null;
/* 14671 */     CommonTree COMMA361_tree = null;
/* 14672 */     CommonTree REGISTER362_tree = null;
/* 14673 */     CommonTree COMMA363_tree = null;
/* 14674 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14675 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14676 */     RewriteRuleSubtreeStream stream_integral_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule integral_literal");
/* 14677 */     RewriteRuleSubtreeStream stream_instruction_format22s = new RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format22s");
/*       */     try
/*       */     {
/* 14683 */       pushFollow(FOLLOW_instruction_format22s_in_insn_format22s4829);
/* 14684 */       instruction_format22s359 = instruction_format22s();
/* 14685 */       this.state._fsp -= 1;
/*       */ 
/* 14687 */       stream_instruction_format22s.add(instruction_format22s359.getTree());
/* 14688 */       REGISTER360 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22s4831);
/* 14689 */       stream_REGISTER.add(REGISTER360);
/*       */ 
/* 14691 */       COMMA361 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22s4833);
/* 14692 */       stream_COMMA.add(COMMA361);
/*       */ 
/* 14694 */       REGISTER362 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22s4835);
/* 14695 */       stream_REGISTER.add(REGISTER362);
/*       */ 
/* 14697 */       COMMA363 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22s4837);
/* 14698 */       stream_COMMA.add(COMMA363);
/*       */ 
/* 14700 */       pushFollow(FOLLOW_integral_literal_in_insn_format22s4839);
/* 14701 */       integral_literal364 = integral_literal();
/* 14702 */       this.state._fsp -= 1;
/*       */ 
/* 14704 */       stream_integral_literal.add(integral_literal364.getTree());
/*       */ 
/* 14712 */       retval.tree = root_0;
/* 14713 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14715 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14720 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14721 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(164, retval.start, "I_STATEMENT_FORMAT22s"), root_1);
/* 14722 */       this.adaptor.addChild(root_1, stream_instruction_format22s.nextTree());
/* 14723 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14724 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14725 */       this.adaptor.addChild(root_1, stream_integral_literal.nextTree());
/* 14726 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14732 */       retval.tree = root_0;
/*       */ 
/* 14736 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14738 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14739 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14743 */       reportError(re);
/* 14744 */       recover(this.input, re);
/* 14745 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14750 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22t_return insn_format22t()
/*       */     throws RecognitionException
/*       */   {
/* 14765 */     insn_format22t_return retval = new insn_format22t_return();
/* 14766 */     retval.start = this.input.LT(1);
/*       */ 
/* 14768 */     CommonTree root_0 = null;
/*       */ 
/* 14770 */     Token INSTRUCTION_FORMAT22t365 = null;
/* 14771 */     Token REGISTER366 = null;
/* 14772 */     Token COMMA367 = null;
/* 14773 */     Token REGISTER368 = null;
/* 14774 */     Token COMMA369 = null;
/* 14775 */     ParserRuleReturnScope label_ref370 = null;
/*       */ 
/* 14777 */     CommonTree INSTRUCTION_FORMAT22t365_tree = null;
/* 14778 */     CommonTree REGISTER366_tree = null;
/* 14779 */     CommonTree COMMA367_tree = null;
/* 14780 */     CommonTree REGISTER368_tree = null;
/* 14781 */     CommonTree COMMA369_tree = null;
/* 14782 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14783 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14784 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22t");
/* 14785 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 14791 */       INSTRUCTION_FORMAT22t365 = (Token)match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t4873);
/* 14792 */       stream_INSTRUCTION_FORMAT22t.add(INSTRUCTION_FORMAT22t365);
/*       */ 
/* 14794 */       REGISTER366 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22t4875);
/* 14795 */       stream_REGISTER.add(REGISTER366);
/*       */ 
/* 14797 */       COMMA367 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22t4877);
/* 14798 */       stream_COMMA.add(COMMA367);
/*       */ 
/* 14800 */       REGISTER368 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22t4879);
/* 14801 */       stream_REGISTER.add(REGISTER368);
/*       */ 
/* 14803 */       COMMA369 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22t4881);
/* 14804 */       stream_COMMA.add(COMMA369);
/*       */ 
/* 14806 */       pushFollow(FOLLOW_label_ref_in_insn_format22t4883);
/* 14807 */       label_ref370 = label_ref();
/* 14808 */       this.state._fsp -= 1;
/*       */ 
/* 14810 */       stream_label_ref.add(label_ref370.getTree());
/*       */ 
/* 14818 */       retval.tree = root_0;
/* 14819 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14821 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14826 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14827 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(165, retval.start, "I_STATEMENT_FFORMAT22t"), root_1);
/* 14828 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT22t.nextNode());
/* 14829 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14830 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14831 */       this.adaptor.addChild(root_1, stream_label_ref.nextTree());
/* 14832 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14838 */       retval.tree = root_0;
/*       */ 
/* 14842 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14844 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14845 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14849 */       reportError(re);
/* 14850 */       recover(this.input, re);
/* 14851 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14856 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format22x_return insn_format22x()
/*       */     throws RecognitionException
/*       */   {
/* 14871 */     insn_format22x_return retval = new insn_format22x_return();
/* 14872 */     retval.start = this.input.LT(1);
/*       */ 
/* 14874 */     CommonTree root_0 = null;
/*       */ 
/* 14876 */     Token INSTRUCTION_FORMAT22x371 = null;
/* 14877 */     Token REGISTER372 = null;
/* 14878 */     Token COMMA373 = null;
/* 14879 */     Token REGISTER374 = null;
/*       */ 
/* 14881 */     CommonTree INSTRUCTION_FORMAT22x371_tree = null;
/* 14882 */     CommonTree REGISTER372_tree = null;
/* 14883 */     CommonTree COMMA373_tree = null;
/* 14884 */     CommonTree REGISTER374_tree = null;
/* 14885 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT22x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT22x");
/* 14886 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14887 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/* 14893 */       INSTRUCTION_FORMAT22x371 = (Token)match(this.input, 83, FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x4917);
/* 14894 */       stream_INSTRUCTION_FORMAT22x.add(INSTRUCTION_FORMAT22x371);
/*       */ 
/* 14896 */       REGISTER372 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22x4919);
/* 14897 */       stream_REGISTER.add(REGISTER372);
/*       */ 
/* 14899 */       COMMA373 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format22x4921);
/* 14900 */       stream_COMMA.add(COMMA373);
/*       */ 
/* 14902 */       REGISTER374 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22x4923);
/* 14903 */       stream_REGISTER.add(REGISTER374);
/*       */ 
/* 14912 */       retval.tree = root_0;
/* 14913 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 14915 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 14920 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 14921 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(166, retval.start, "I_STATEMENT_FORMAT22x"), root_1);
/* 14922 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT22x.nextNode());
/* 14923 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14924 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 14925 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 14931 */       retval.tree = root_0;
/*       */ 
/* 14935 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 14937 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 14938 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 14942 */       reportError(re);
/* 14943 */       recover(this.input, re);
/* 14944 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 14949 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format23x_return insn_format23x()
/*       */     throws RecognitionException
/*       */   {
/* 14964 */     insn_format23x_return retval = new insn_format23x_return();
/* 14965 */     retval.start = this.input.LT(1);
/*       */ 
/* 14967 */     CommonTree root_0 = null;
/*       */ 
/* 14969 */     Token INSTRUCTION_FORMAT23x375 = null;
/* 14970 */     Token REGISTER376 = null;
/* 14971 */     Token COMMA377 = null;
/* 14972 */     Token REGISTER378 = null;
/* 14973 */     Token COMMA379 = null;
/* 14974 */     Token REGISTER380 = null;
/*       */ 
/* 14976 */     CommonTree INSTRUCTION_FORMAT23x375_tree = null;
/* 14977 */     CommonTree REGISTER376_tree = null;
/* 14978 */     CommonTree COMMA377_tree = null;
/* 14979 */     CommonTree REGISTER378_tree = null;
/* 14980 */     CommonTree COMMA379_tree = null;
/* 14981 */     CommonTree REGISTER380_tree = null;
/* 14982 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 14983 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 14984 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT23x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT23x");
/*       */     try
/*       */     {
/* 14990 */       INSTRUCTION_FORMAT23x375 = (Token)match(this.input, 84, FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x4955);
/* 14991 */       stream_INSTRUCTION_FORMAT23x.add(INSTRUCTION_FORMAT23x375);
/*       */ 
/* 14993 */       REGISTER376 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format23x4957);
/* 14994 */       stream_REGISTER.add(REGISTER376);
/*       */ 
/* 14996 */       COMMA377 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format23x4959);
/* 14997 */       stream_COMMA.add(COMMA377);
/*       */ 
/* 14999 */       REGISTER378 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format23x4961);
/* 15000 */       stream_REGISTER.add(REGISTER378);
/*       */ 
/* 15002 */       COMMA379 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format23x4963);
/* 15003 */       stream_COMMA.add(COMMA379);
/*       */ 
/* 15005 */       REGISTER380 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format23x4965);
/* 15006 */       stream_REGISTER.add(REGISTER380);
/*       */ 
/* 15015 */       retval.tree = root_0;
/* 15016 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15018 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15023 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15024 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(167, retval.start, "I_STATEMENT_FORMAT23x"), root_1);
/* 15025 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT23x.nextNode());
/* 15026 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15027 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15028 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15029 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15035 */       retval.tree = root_0;
/*       */ 
/* 15039 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15041 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15042 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15046 */       reportError(re);
/* 15047 */       recover(this.input, re);
/* 15048 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15053 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format30t_return insn_format30t()
/*       */     throws RecognitionException
/*       */   {
/* 15068 */     insn_format30t_return retval = new insn_format30t_return();
/* 15069 */     retval.start = this.input.LT(1);
/*       */ 
/* 15071 */     CommonTree root_0 = null;
/*       */ 
/* 15073 */     Token INSTRUCTION_FORMAT30t381 = null;
/* 15074 */     ParserRuleReturnScope label_ref382 = null;
/*       */ 
/* 15076 */     CommonTree INSTRUCTION_FORMAT30t381_tree = null;
/* 15077 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT30t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT30t");
/* 15078 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 15084 */       INSTRUCTION_FORMAT30t381 = (Token)match(this.input, 85, FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t4999);
/* 15085 */       stream_INSTRUCTION_FORMAT30t.add(INSTRUCTION_FORMAT30t381);
/*       */ 
/* 15087 */       pushFollow(FOLLOW_label_ref_in_insn_format30t5001);
/* 15088 */       label_ref382 = label_ref();
/* 15089 */       this.state._fsp -= 1;
/*       */ 
/* 15091 */       stream_label_ref.add(label_ref382.getTree());
/*       */ 
/* 15099 */       retval.tree = root_0;
/* 15100 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15102 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15107 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15108 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(168, retval.start, "I_STATEMENT_FORMAT30t"), root_1);
/* 15109 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT30t.nextNode());
/* 15110 */       this.adaptor.addChild(root_1, stream_label_ref.nextTree());
/* 15111 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15117 */       retval.tree = root_0;
/*       */ 
/* 15121 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15123 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15124 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15128 */       reportError(re);
/* 15129 */       recover(this.input, re);
/* 15130 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15135 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format31c_return insn_format31c()
/*       */     throws RecognitionException
/*       */   {
/* 15150 */     insn_format31c_return retval = new insn_format31c_return();
/* 15151 */     retval.start = this.input.LT(1);
/*       */ 
/* 15153 */     CommonTree root_0 = null;
/*       */ 
/* 15155 */     Token INSTRUCTION_FORMAT31c383 = null;
/* 15156 */     Token REGISTER384 = null;
/* 15157 */     Token COMMA385 = null;
/* 15158 */     Token STRING_LITERAL386 = null;
/*       */ 
/* 15160 */     CommonTree INSTRUCTION_FORMAT31c383_tree = null;
/* 15161 */     CommonTree REGISTER384_tree = null;
/* 15162 */     CommonTree COMMA385_tree = null;
/* 15163 */     CommonTree STRING_LITERAL386_tree = null;
/* 15164 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT31c = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31c");
/* 15165 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/* 15166 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 15167 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/*       */     try
/*       */     {
/* 15173 */       INSTRUCTION_FORMAT31c383 = (Token)match(this.input, 86, FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5031);
/* 15174 */       stream_INSTRUCTION_FORMAT31c.add(INSTRUCTION_FORMAT31c383);
/*       */ 
/* 15176 */       REGISTER384 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format31c5033);
/* 15177 */       stream_REGISTER.add(REGISTER384);
/*       */ 
/* 15179 */       COMMA385 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format31c5035);
/* 15180 */       stream_COMMA.add(COMMA385);
/*       */ 
/* 15182 */       STRING_LITERAL386 = (Token)match(this.input, 208, FOLLOW_STRING_LITERAL_in_insn_format31c5037);
/* 15183 */       stream_STRING_LITERAL.add(STRING_LITERAL386);
/*       */ 
/* 15192 */       retval.tree = root_0;
/* 15193 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15195 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15200 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15201 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(169, retval.start, "I_STATEMENT_FORMAT31c"), root_1);
/* 15202 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT31c.nextNode());
/* 15203 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15204 */       this.adaptor.addChild(root_1, stream_STRING_LITERAL.nextNode());
/* 15205 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15211 */       retval.tree = root_0;
/*       */ 
/* 15215 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15217 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15218 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15222 */       reportError(re);
/* 15223 */       recover(this.input, re);
/* 15224 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15229 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format31i_return insn_format31i()
/*       */     throws RecognitionException
/*       */   {
/* 15244 */     insn_format31i_return retval = new insn_format31i_return();
/* 15245 */     retval.start = this.input.LT(1);
/*       */ 
/* 15247 */     CommonTree root_0 = null;
/*       */ 
/* 15249 */     Token REGISTER388 = null;
/* 15250 */     Token COMMA389 = null;
/* 15251 */     ParserRuleReturnScope instruction_format31i387 = null;
/* 15252 */     ParserRuleReturnScope fixed_32bit_literal390 = null;
/*       */ 
/* 15254 */     CommonTree REGISTER388_tree = null;
/* 15255 */     CommonTree COMMA389_tree = null;
/* 15256 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 15257 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 15258 */     RewriteRuleSubtreeStream stream_instruction_format31i = new RewriteRuleSubtreeStream(this.adaptor, "rule instruction_format31i");
/* 15259 */     RewriteRuleSubtreeStream stream_fixed_32bit_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
/*       */     try
/*       */     {
/* 15265 */       pushFollow(FOLLOW_instruction_format31i_in_insn_format31i5068);
/* 15266 */       instruction_format31i387 = instruction_format31i();
/* 15267 */       this.state._fsp -= 1;
/*       */ 
/* 15269 */       stream_instruction_format31i.add(instruction_format31i387.getTree());
/* 15270 */       REGISTER388 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format31i5070);
/* 15271 */       stream_REGISTER.add(REGISTER388);
/*       */ 
/* 15273 */       COMMA389 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format31i5072);
/* 15274 */       stream_COMMA.add(COMMA389);
/*       */ 
/* 15276 */       pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format31i5074);
/* 15277 */       fixed_32bit_literal390 = fixed_32bit_literal();
/* 15278 */       this.state._fsp -= 1;
/*       */ 
/* 15280 */       stream_fixed_32bit_literal.add(fixed_32bit_literal390.getTree());
/*       */ 
/* 15288 */       retval.tree = root_0;
/* 15289 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15291 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15296 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15297 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(170, retval.start, "I_STATEMENT_FORMAT31i"), root_1);
/* 15298 */       this.adaptor.addChild(root_1, stream_instruction_format31i.nextTree());
/* 15299 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15300 */       this.adaptor.addChild(root_1, stream_fixed_32bit_literal.nextTree());
/* 15301 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15307 */       retval.tree = root_0;
/*       */ 
/* 15311 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15313 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15314 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15318 */       reportError(re);
/* 15319 */       recover(this.input, re);
/* 15320 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15325 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format31t_return insn_format31t()
/*       */     throws RecognitionException
/*       */   {
/* 15340 */     insn_format31t_return retval = new insn_format31t_return();
/* 15341 */     retval.start = this.input.LT(1);
/*       */ 
/* 15343 */     CommonTree root_0 = null;
/*       */ 
/* 15345 */     Token INSTRUCTION_FORMAT31t391 = null;
/* 15346 */     Token REGISTER392 = null;
/* 15347 */     Token COMMA393 = null;
/* 15348 */     ParserRuleReturnScope label_ref394 = null;
/*       */ 
/* 15350 */     CommonTree INSTRUCTION_FORMAT31t391_tree = null;
/* 15351 */     CommonTree REGISTER392_tree = null;
/* 15352 */     CommonTree COMMA393_tree = null;
/* 15353 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT31t = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT31t");
/* 15354 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 15355 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 15356 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 15362 */       INSTRUCTION_FORMAT31t391 = (Token)match(this.input, 89, FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5106);
/* 15363 */       stream_INSTRUCTION_FORMAT31t.add(INSTRUCTION_FORMAT31t391);
/*       */ 
/* 15365 */       REGISTER392 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format31t5108);
/* 15366 */       stream_REGISTER.add(REGISTER392);
/*       */ 
/* 15368 */       COMMA393 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format31t5110);
/* 15369 */       stream_COMMA.add(COMMA393);
/*       */ 
/* 15371 */       pushFollow(FOLLOW_label_ref_in_insn_format31t5112);
/* 15372 */       label_ref394 = label_ref();
/* 15373 */       this.state._fsp -= 1;
/*       */ 
/* 15375 */       stream_label_ref.add(label_ref394.getTree());
/*       */ 
/* 15383 */       retval.tree = root_0;
/* 15384 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15386 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15391 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15392 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(171, retval.start, "I_STATEMENT_FORMAT31t"), root_1);
/* 15393 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT31t.nextNode());
/* 15394 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15395 */       this.adaptor.addChild(root_1, stream_label_ref.nextTree());
/* 15396 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15402 */       retval.tree = root_0;
/*       */ 
/* 15406 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15408 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15409 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15413 */       reportError(re);
/* 15414 */       recover(this.input, re);
/* 15415 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15420 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format32x_return insn_format32x()
/*       */     throws RecognitionException
/*       */   {
/* 15435 */     insn_format32x_return retval = new insn_format32x_return();
/* 15436 */     retval.start = this.input.LT(1);
/*       */ 
/* 15438 */     CommonTree root_0 = null;
/*       */ 
/* 15440 */     Token INSTRUCTION_FORMAT32x395 = null;
/* 15441 */     Token REGISTER396 = null;
/* 15442 */     Token COMMA397 = null;
/* 15443 */     Token REGISTER398 = null;
/*       */ 
/* 15445 */     CommonTree INSTRUCTION_FORMAT32x395_tree = null;
/* 15446 */     CommonTree REGISTER396_tree = null;
/* 15447 */     CommonTree COMMA397_tree = null;
/* 15448 */     CommonTree REGISTER398_tree = null;
/* 15449 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 15450 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 15451 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT32x = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT32x");
/*       */     try
/*       */     {
/* 15457 */       INSTRUCTION_FORMAT32x395 = (Token)match(this.input, 90, FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5144);
/* 15458 */       stream_INSTRUCTION_FORMAT32x.add(INSTRUCTION_FORMAT32x395);
/*       */ 
/* 15460 */       REGISTER396 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format32x5146);
/* 15461 */       stream_REGISTER.add(REGISTER396);
/*       */ 
/* 15463 */       COMMA397 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format32x5148);
/* 15464 */       stream_COMMA.add(COMMA397);
/*       */ 
/* 15466 */       REGISTER398 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format32x5150);
/* 15467 */       stream_REGISTER.add(REGISTER398);
/*       */ 
/* 15476 */       retval.tree = root_0;
/* 15477 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15479 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15484 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15485 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(172, retval.start, "I_STATEMENT_FORMAT32x"), root_1);
/* 15486 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT32x.nextNode());
/* 15487 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15488 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 15489 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15495 */       retval.tree = root_0;
/*       */ 
/* 15499 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15501 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15502 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15506 */       reportError(re);
/* 15507 */       recover(this.input, re);
/* 15508 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15513 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format35c_method_return insn_format35c_method()
/*       */     throws RecognitionException
/*       */   {
/* 15528 */     insn_format35c_method_return retval = new insn_format35c_method_return();
/* 15529 */     retval.start = this.input.LT(1);
/*       */ 
/* 15531 */     CommonTree root_0 = null;
/*       */ 
/* 15533 */     Token INSTRUCTION_FORMAT35c_METHOD399 = null;
/* 15534 */     Token OPEN_BRACE400 = null;
/* 15535 */     Token CLOSE_BRACE402 = null;
/* 15536 */     Token COMMA403 = null;
/* 15537 */     ParserRuleReturnScope register_list401 = null;
/* 15538 */     ParserRuleReturnScope fully_qualified_method404 = null;
/*       */ 
/* 15540 */     CommonTree INSTRUCTION_FORMAT35c_METHOD399_tree = null;
/* 15541 */     CommonTree OPEN_BRACE400_tree = null;
/* 15542 */     CommonTree CLOSE_BRACE402_tree = null;
/* 15543 */     CommonTree COMMA403_tree = null;
/* 15544 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT35c_METHOD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_METHOD");
/* 15545 */     RewriteRuleTokenStream stream_CLOSE_BRACE = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
/* 15546 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 15547 */     RewriteRuleTokenStream stream_OPEN_BRACE = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
/* 15548 */     RewriteRuleSubtreeStream stream_register_list = new RewriteRuleSubtreeStream(this.adaptor, "rule register_list");
/* 15549 */     RewriteRuleSubtreeStream stream_fully_qualified_method = new RewriteRuleSubtreeStream(this.adaptor, "rule fully_qualified_method");
/*       */     try
/*       */     {
/* 15555 */       INSTRUCTION_FORMAT35c_METHOD399 = (Token)match(this.input, 91, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method5182);
/* 15556 */       stream_INSTRUCTION_FORMAT35c_METHOD.add(INSTRUCTION_FORMAT35c_METHOD399);
/*       */ 
/* 15558 */       OPEN_BRACE400 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format35c_method5184);
/* 15559 */       stream_OPEN_BRACE.add(OPEN_BRACE400);
/*       */ 
/* 15561 */       pushFollow(FOLLOW_register_list_in_insn_format35c_method5186);
/* 15562 */       register_list401 = register_list();
/* 15563 */       this.state._fsp -= 1;
/*       */ 
/* 15565 */       stream_register_list.add(register_list401.getTree());
/* 15566 */       CLOSE_BRACE402 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format35c_method5188);
/* 15567 */       stream_CLOSE_BRACE.add(CLOSE_BRACE402);
/*       */ 
/* 15569 */       COMMA403 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format35c_method5190);
/* 15570 */       stream_COMMA.add(COMMA403);
/*       */ 
/* 15572 */       pushFollow(FOLLOW_fully_qualified_method_in_insn_format35c_method5192);
/* 15573 */       fully_qualified_method404 = fully_qualified_method();
/* 15574 */       this.state._fsp -= 1;
/*       */ 
/* 15576 */       stream_fully_qualified_method.add(fully_qualified_method404.getTree());
/*       */ 
/* 15584 */       retval.tree = root_0;
/* 15585 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15587 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15592 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15593 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(173, retval.start, "I_STATEMENT_FORMAT35c_METHOD"), root_1);
/* 15594 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT35c_METHOD.nextNode());
/* 15595 */       this.adaptor.addChild(root_1, stream_register_list.nextTree());
/* 15596 */       this.adaptor.addChild(root_1, stream_fully_qualified_method.nextTree());
/* 15597 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15603 */       retval.tree = root_0;
/*       */ 
/* 15607 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15609 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15610 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15614 */       reportError(re);
/* 15615 */       recover(this.input, re);
/* 15616 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15621 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format35c_type_return insn_format35c_type()
/*       */     throws RecognitionException
/*       */   {
/* 15636 */     insn_format35c_type_return retval = new insn_format35c_type_return();
/* 15637 */     retval.start = this.input.LT(1);
/*       */ 
/* 15639 */     CommonTree root_0 = null;
/*       */ 
/* 15641 */     Token INSTRUCTION_FORMAT35c_TYPE405 = null;
/* 15642 */     Token OPEN_BRACE406 = null;
/* 15643 */     Token CLOSE_BRACE408 = null;
/* 15644 */     Token COMMA409 = null;
/* 15645 */     ParserRuleReturnScope register_list407 = null;
/* 15646 */     ParserRuleReturnScope nonvoid_type_descriptor410 = null;
/*       */ 
/* 15648 */     CommonTree INSTRUCTION_FORMAT35c_TYPE405_tree = null;
/* 15649 */     CommonTree OPEN_BRACE406_tree = null;
/* 15650 */     CommonTree CLOSE_BRACE408_tree = null;
/* 15651 */     CommonTree COMMA409_tree = null;
/* 15652 */     RewriteRuleTokenStream stream_CLOSE_BRACE = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
/* 15653 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 15654 */     RewriteRuleTokenStream stream_OPEN_BRACE = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
/* 15655 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT35c_TYPE = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT35c_TYPE");
/* 15656 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/* 15657 */     RewriteRuleSubtreeStream stream_register_list = new RewriteRuleSubtreeStream(this.adaptor, "rule register_list");
/*       */     try
/*       */     {
/* 15663 */       INSTRUCTION_FORMAT35c_TYPE405 = (Token)match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5224);
/* 15664 */       stream_INSTRUCTION_FORMAT35c_TYPE.add(INSTRUCTION_FORMAT35c_TYPE405);
/*       */ 
/* 15666 */       OPEN_BRACE406 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format35c_type5226);
/* 15667 */       stream_OPEN_BRACE.add(OPEN_BRACE406);
/*       */ 
/* 15669 */       pushFollow(FOLLOW_register_list_in_insn_format35c_type5228);
/* 15670 */       register_list407 = register_list();
/* 15671 */       this.state._fsp -= 1;
/*       */ 
/* 15673 */       stream_register_list.add(register_list407.getTree());
/* 15674 */       CLOSE_BRACE408 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format35c_type5230);
/* 15675 */       stream_CLOSE_BRACE.add(CLOSE_BRACE408);
/*       */ 
/* 15677 */       COMMA409 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format35c_type5232);
/* 15678 */       stream_COMMA.add(COMMA409);
/*       */ 
/* 15680 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5234);
/* 15681 */       nonvoid_type_descriptor410 = nonvoid_type_descriptor();
/* 15682 */       this.state._fsp -= 1;
/*       */ 
/* 15684 */       stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor410.getTree());
/*       */ 
/* 15692 */       retval.tree = root_0;
/* 15693 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 15695 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15700 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 15701 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(174, retval.start, "I_STATEMENT_FORMAT35c_TYPE"), root_1);
/* 15702 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT35c_TYPE.nextNode());
/* 15703 */       this.adaptor.addChild(root_1, stream_register_list.nextTree());
/* 15704 */       this.adaptor.addChild(root_1, stream_nonvoid_type_descriptor.nextTree());
/* 15705 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 15711 */       retval.tree = root_0;
/*       */ 
/* 15715 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15717 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15718 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15722 */       reportError(re);
/* 15723 */       recover(this.input, re);
/* 15724 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15729 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format35c_method_odex_return insn_format35c_method_odex()
/*       */     throws RecognitionException
/*       */   {
/* 15744 */     insn_format35c_method_odex_return retval = new insn_format35c_method_odex_return();
/* 15745 */     retval.start = this.input.LT(1);
/*       */ 
/* 15747 */     CommonTree root_0 = null;
/*       */ 
/* 15749 */     Token INSTRUCTION_FORMAT35c_METHOD_ODEX411 = null;
/* 15750 */     Token OPEN_BRACE412 = null;
/* 15751 */     Token CLOSE_BRACE414 = null;
/* 15752 */     Token COMMA415 = null;
/* 15753 */     ParserRuleReturnScope register_list413 = null;
/* 15754 */     ParserRuleReturnScope fully_qualified_method416 = null;
/*       */ 
/* 15756 */     CommonTree INSTRUCTION_FORMAT35c_METHOD_ODEX411_tree = null;
/* 15757 */     CommonTree OPEN_BRACE412_tree = null;
/* 15758 */     CommonTree CLOSE_BRACE414_tree = null;
/* 15759 */     CommonTree COMMA415_tree = null;
/*       */     try
/*       */     {
/* 15765 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15768 */       INSTRUCTION_FORMAT35c_METHOD_ODEX411 = (Token)match(this.input, 92, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5266);
/* 15769 */       INSTRUCTION_FORMAT35c_METHOD_ODEX411_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT35c_METHOD_ODEX411);
/* 15770 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT35c_METHOD_ODEX411_tree);
/*       */ 
/* 15772 */       OPEN_BRACE412 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5268);
/* 15773 */       OPEN_BRACE412_tree = (CommonTree)this.adaptor.create(OPEN_BRACE412);
/* 15774 */       this.adaptor.addChild(root_0, OPEN_BRACE412_tree);
/*       */ 
/* 15776 */       pushFollow(FOLLOW_register_list_in_insn_format35c_method_odex5270);
/* 15777 */       register_list413 = register_list();
/* 15778 */       this.state._fsp -= 1;
/*       */ 
/* 15780 */       this.adaptor.addChild(root_0, register_list413.getTree());
/*       */ 
/* 15782 */       CLOSE_BRACE414 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5272);
/* 15783 */       CLOSE_BRACE414_tree = (CommonTree)this.adaptor.create(CLOSE_BRACE414);
/* 15784 */       this.adaptor.addChild(root_0, CLOSE_BRACE414_tree);
/*       */ 
/* 15786 */       COMMA415 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format35c_method_odex5274);
/* 15787 */       COMMA415_tree = (CommonTree)this.adaptor.create(COMMA415);
/* 15788 */       this.adaptor.addChild(root_0, COMMA415_tree);
/*       */ 
/* 15790 */       pushFollow(FOLLOW_fully_qualified_method_in_insn_format35c_method_odex5276);
/* 15791 */       fully_qualified_method416 = fully_qualified_method();
/* 15792 */       this.state._fsp -= 1;
/*       */ 
/* 15794 */       this.adaptor.addChild(root_0, fully_qualified_method416.getTree());
/*       */ 
/* 15797 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT35c_METHOD_ODEX411 != null ? INSTRUCTION_FORMAT35c_METHOD_ODEX411.getText() : null);
/*       */ 
/* 15801 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15803 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15804 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15808 */       reportError(re);
/* 15809 */       recover(this.input, re);
/* 15810 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15815 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format35mi_method_return insn_format35mi_method()
/*       */     throws RecognitionException
/*       */   {
/* 15830 */     insn_format35mi_method_return retval = new insn_format35mi_method_return();
/* 15831 */     retval.start = this.input.LT(1);
/*       */ 
/* 15833 */     CommonTree root_0 = null;
/*       */ 
/* 15835 */     Token INSTRUCTION_FORMAT35mi_METHOD417 = null;
/* 15836 */     Token OPEN_BRACE418 = null;
/* 15837 */     Token CLOSE_BRACE420 = null;
/* 15838 */     Token COMMA421 = null;
/* 15839 */     Token INLINE_INDEX422 = null;
/* 15840 */     ParserRuleReturnScope register_list419 = null;
/*       */ 
/* 15842 */     CommonTree INSTRUCTION_FORMAT35mi_METHOD417_tree = null;
/* 15843 */     CommonTree OPEN_BRACE418_tree = null;
/* 15844 */     CommonTree CLOSE_BRACE420_tree = null;
/* 15845 */     CommonTree COMMA421_tree = null;
/* 15846 */     CommonTree INLINE_INDEX422_tree = null;
/*       */     try
/*       */     {
/* 15852 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15855 */       INSTRUCTION_FORMAT35mi_METHOD417 = (Token)match(this.input, 94, FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5297);
/* 15856 */       INSTRUCTION_FORMAT35mi_METHOD417_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT35mi_METHOD417);
/* 15857 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT35mi_METHOD417_tree);
/*       */ 
/* 15859 */       OPEN_BRACE418 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format35mi_method5299);
/* 15860 */       OPEN_BRACE418_tree = (CommonTree)this.adaptor.create(OPEN_BRACE418);
/* 15861 */       this.adaptor.addChild(root_0, OPEN_BRACE418_tree);
/*       */ 
/* 15863 */       pushFollow(FOLLOW_register_list_in_insn_format35mi_method5301);
/* 15864 */       register_list419 = register_list();
/* 15865 */       this.state._fsp -= 1;
/*       */ 
/* 15867 */       this.adaptor.addChild(root_0, register_list419.getTree());
/*       */ 
/* 15869 */       CLOSE_BRACE420 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5303);
/* 15870 */       CLOSE_BRACE420_tree = (CommonTree)this.adaptor.create(CLOSE_BRACE420);
/* 15871 */       this.adaptor.addChild(root_0, CLOSE_BRACE420_tree);
/*       */ 
/* 15873 */       COMMA421 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format35mi_method5305);
/* 15874 */       COMMA421_tree = (CommonTree)this.adaptor.create(COMMA421);
/* 15875 */       this.adaptor.addChild(root_0, COMMA421_tree);
/*       */ 
/* 15877 */       INLINE_INDEX422 = (Token)match(this.input, 57, FOLLOW_INLINE_INDEX_in_insn_format35mi_method5307);
/* 15878 */       INLINE_INDEX422_tree = (CommonTree)this.adaptor.create(INLINE_INDEX422);
/* 15879 */       this.adaptor.addChild(root_0, INLINE_INDEX422_tree);
/*       */ 
/* 15882 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT35mi_METHOD417 != null ? INSTRUCTION_FORMAT35mi_METHOD417.getText() : null);
/*       */ 
/* 15886 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15888 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15889 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15893 */       reportError(re);
/* 15894 */       recover(this.input, re);
/* 15895 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15900 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format35ms_method_return insn_format35ms_method()
/*       */     throws RecognitionException
/*       */   {
/* 15915 */     insn_format35ms_method_return retval = new insn_format35ms_method_return();
/* 15916 */     retval.start = this.input.LT(1);
/*       */ 
/* 15918 */     CommonTree root_0 = null;
/*       */ 
/* 15920 */     Token INSTRUCTION_FORMAT35ms_METHOD423 = null;
/* 15921 */     Token OPEN_BRACE424 = null;
/* 15922 */     Token CLOSE_BRACE426 = null;
/* 15923 */     Token COMMA427 = null;
/* 15924 */     Token VTABLE_INDEX428 = null;
/* 15925 */     ParserRuleReturnScope register_list425 = null;
/*       */ 
/* 15927 */     CommonTree INSTRUCTION_FORMAT35ms_METHOD423_tree = null;
/* 15928 */     CommonTree OPEN_BRACE424_tree = null;
/* 15929 */     CommonTree CLOSE_BRACE426_tree = null;
/* 15930 */     CommonTree COMMA427_tree = null;
/* 15931 */     CommonTree VTABLE_INDEX428_tree = null;
/*       */     try
/*       */     {
/* 15937 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 15940 */       INSTRUCTION_FORMAT35ms_METHOD423 = (Token)match(this.input, 95, FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5328);
/* 15941 */       INSTRUCTION_FORMAT35ms_METHOD423_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT35ms_METHOD423);
/* 15942 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT35ms_METHOD423_tree);
/*       */ 
/* 15944 */       OPEN_BRACE424 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format35ms_method5330);
/* 15945 */       OPEN_BRACE424_tree = (CommonTree)this.adaptor.create(OPEN_BRACE424);
/* 15946 */       this.adaptor.addChild(root_0, OPEN_BRACE424_tree);
/*       */ 
/* 15948 */       pushFollow(FOLLOW_register_list_in_insn_format35ms_method5332);
/* 15949 */       register_list425 = register_list();
/* 15950 */       this.state._fsp -= 1;
/*       */ 
/* 15952 */       this.adaptor.addChild(root_0, register_list425.getTree());
/*       */ 
/* 15954 */       CLOSE_BRACE426 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5334);
/* 15955 */       CLOSE_BRACE426_tree = (CommonTree)this.adaptor.create(CLOSE_BRACE426);
/* 15956 */       this.adaptor.addChild(root_0, CLOSE_BRACE426_tree);
/*       */ 
/* 15958 */       COMMA427 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format35ms_method5336);
/* 15959 */       COMMA427_tree = (CommonTree)this.adaptor.create(COMMA427);
/* 15960 */       this.adaptor.addChild(root_0, COMMA427_tree);
/*       */ 
/* 15962 */       VTABLE_INDEX428 = (Token)match(this.input, 213, FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5338);
/* 15963 */       VTABLE_INDEX428_tree = (CommonTree)this.adaptor.create(VTABLE_INDEX428);
/* 15964 */       this.adaptor.addChild(root_0, VTABLE_INDEX428_tree);
/*       */ 
/* 15967 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT35ms_METHOD423 != null ? INSTRUCTION_FORMAT35ms_METHOD423.getText() : null);
/*       */ 
/* 15971 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 15973 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 15974 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 15978 */       reportError(re);
/* 15979 */       recover(this.input, re);
/* 15980 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 15985 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format3rc_method_return insn_format3rc_method()
/*       */     throws RecognitionException
/*       */   {
/* 16000 */     insn_format3rc_method_return retval = new insn_format3rc_method_return();
/* 16001 */     retval.start = this.input.LT(1);
/*       */ 
/* 16003 */     CommonTree root_0 = null;
/*       */ 
/* 16005 */     Token INSTRUCTION_FORMAT3rc_METHOD429 = null;
/* 16006 */     Token OPEN_BRACE430 = null;
/* 16007 */     Token CLOSE_BRACE432 = null;
/* 16008 */     Token COMMA433 = null;
/* 16009 */     ParserRuleReturnScope register_range431 = null;
/* 16010 */     ParserRuleReturnScope fully_qualified_method434 = null;
/*       */ 
/* 16012 */     CommonTree INSTRUCTION_FORMAT3rc_METHOD429_tree = null;
/* 16013 */     CommonTree OPEN_BRACE430_tree = null;
/* 16014 */     CommonTree CLOSE_BRACE432_tree = null;
/* 16015 */     CommonTree COMMA433_tree = null;
/* 16016 */     RewriteRuleTokenStream stream_CLOSE_BRACE = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
/* 16017 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT3rc_METHOD = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_METHOD");
/* 16018 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 16019 */     RewriteRuleTokenStream stream_OPEN_BRACE = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
/* 16020 */     RewriteRuleSubtreeStream stream_fully_qualified_method = new RewriteRuleSubtreeStream(this.adaptor, "rule fully_qualified_method");
/* 16021 */     RewriteRuleSubtreeStream stream_register_range = new RewriteRuleSubtreeStream(this.adaptor, "rule register_range");
/*       */     try
/*       */     {
/* 16027 */       INSTRUCTION_FORMAT3rc_METHOD429 = (Token)match(this.input, 96, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5359);
/* 16028 */       stream_INSTRUCTION_FORMAT3rc_METHOD.add(INSTRUCTION_FORMAT3rc_METHOD429);
/*       */ 
/* 16030 */       OPEN_BRACE430 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format3rc_method5361);
/* 16031 */       stream_OPEN_BRACE.add(OPEN_BRACE430);
/*       */ 
/* 16033 */       pushFollow(FOLLOW_register_range_in_insn_format3rc_method5363);
/* 16034 */       register_range431 = register_range();
/* 16035 */       this.state._fsp -= 1;
/*       */ 
/* 16037 */       stream_register_range.add(register_range431.getTree());
/* 16038 */       CLOSE_BRACE432 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5365);
/* 16039 */       stream_CLOSE_BRACE.add(CLOSE_BRACE432);
/*       */ 
/* 16041 */       COMMA433 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format3rc_method5367);
/* 16042 */       stream_COMMA.add(COMMA433);
/*       */ 
/* 16044 */       pushFollow(FOLLOW_fully_qualified_method_in_insn_format3rc_method5369);
/* 16045 */       fully_qualified_method434 = fully_qualified_method();
/* 16046 */       this.state._fsp -= 1;
/*       */ 
/* 16048 */       stream_fully_qualified_method.add(fully_qualified_method434.getTree());
/*       */ 
/* 16056 */       retval.tree = root_0;
/* 16057 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 16059 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16064 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 16065 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(175, retval.start, "I_STATEMENT_FORMAT3rc_METHOD"), root_1);
/* 16066 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT3rc_METHOD.nextNode());
/* 16067 */       this.adaptor.addChild(root_1, stream_register_range.nextTree());
/* 16068 */       this.adaptor.addChild(root_1, stream_fully_qualified_method.nextTree());
/* 16069 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 16075 */       retval.tree = root_0;
/*       */ 
/* 16079 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16081 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16082 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16086 */       reportError(re);
/* 16087 */       recover(this.input, re);
/* 16088 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16093 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format3rc_method_odex_return insn_format3rc_method_odex()
/*       */     throws RecognitionException
/*       */   {
/* 16108 */     insn_format3rc_method_odex_return retval = new insn_format3rc_method_odex_return();
/* 16109 */     retval.start = this.input.LT(1);
/*       */ 
/* 16111 */     CommonTree root_0 = null;
/*       */ 
/* 16113 */     Token INSTRUCTION_FORMAT3rc_METHOD_ODEX435 = null;
/* 16114 */     Token OPEN_BRACE436 = null;
/* 16115 */     Token CLOSE_BRACE438 = null;
/* 16116 */     Token COMMA439 = null;
/* 16117 */     ParserRuleReturnScope register_list437 = null;
/* 16118 */     ParserRuleReturnScope fully_qualified_method440 = null;
/*       */ 
/* 16120 */     CommonTree INSTRUCTION_FORMAT3rc_METHOD_ODEX435_tree = null;
/* 16121 */     CommonTree OPEN_BRACE436_tree = null;
/* 16122 */     CommonTree CLOSE_BRACE438_tree = null;
/* 16123 */     CommonTree COMMA439_tree = null;
/*       */     try
/*       */     {
/* 16129 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16132 */       INSTRUCTION_FORMAT3rc_METHOD_ODEX435 = (Token)match(this.input, 97, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5401);
/* 16133 */       INSTRUCTION_FORMAT3rc_METHOD_ODEX435_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT3rc_METHOD_ODEX435);
/* 16134 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT3rc_METHOD_ODEX435_tree);
/*       */ 
/* 16136 */       OPEN_BRACE436 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5403);
/* 16137 */       OPEN_BRACE436_tree = (CommonTree)this.adaptor.create(OPEN_BRACE436);
/* 16138 */       this.adaptor.addChild(root_0, OPEN_BRACE436_tree);
/*       */ 
/* 16140 */       pushFollow(FOLLOW_register_list_in_insn_format3rc_method_odex5405);
/* 16141 */       register_list437 = register_list();
/* 16142 */       this.state._fsp -= 1;
/*       */ 
/* 16144 */       this.adaptor.addChild(root_0, register_list437.getTree());
/*       */ 
/* 16146 */       CLOSE_BRACE438 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5407);
/* 16147 */       CLOSE_BRACE438_tree = (CommonTree)this.adaptor.create(CLOSE_BRACE438);
/* 16148 */       this.adaptor.addChild(root_0, CLOSE_BRACE438_tree);
/*       */ 
/* 16150 */       COMMA439 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format3rc_method_odex5409);
/* 16151 */       COMMA439_tree = (CommonTree)this.adaptor.create(COMMA439);
/* 16152 */       this.adaptor.addChild(root_0, COMMA439_tree);
/*       */ 
/* 16154 */       pushFollow(FOLLOW_fully_qualified_method_in_insn_format3rc_method_odex5411);
/* 16155 */       fully_qualified_method440 = fully_qualified_method();
/* 16156 */       this.state._fsp -= 1;
/*       */ 
/* 16158 */       this.adaptor.addChild(root_0, fully_qualified_method440.getTree());
/*       */ 
/* 16161 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT3rc_METHOD_ODEX435 != null ? INSTRUCTION_FORMAT3rc_METHOD_ODEX435.getText() : null);
/*       */ 
/* 16165 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16167 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16168 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16172 */       reportError(re);
/* 16173 */       recover(this.input, re);
/* 16174 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16179 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format3rc_type_return insn_format3rc_type()
/*       */     throws RecognitionException
/*       */   {
/* 16194 */     insn_format3rc_type_return retval = new insn_format3rc_type_return();
/* 16195 */     retval.start = this.input.LT(1);
/*       */ 
/* 16197 */     CommonTree root_0 = null;
/*       */ 
/* 16199 */     Token INSTRUCTION_FORMAT3rc_TYPE441 = null;
/* 16200 */     Token OPEN_BRACE442 = null;
/* 16201 */     Token CLOSE_BRACE444 = null;
/* 16202 */     Token COMMA445 = null;
/* 16203 */     ParserRuleReturnScope register_range443 = null;
/* 16204 */     ParserRuleReturnScope nonvoid_type_descriptor446 = null;
/*       */ 
/* 16206 */     CommonTree INSTRUCTION_FORMAT3rc_TYPE441_tree = null;
/* 16207 */     CommonTree OPEN_BRACE442_tree = null;
/* 16208 */     CommonTree CLOSE_BRACE444_tree = null;
/* 16209 */     CommonTree COMMA445_tree = null;
/* 16210 */     RewriteRuleTokenStream stream_CLOSE_BRACE = new RewriteRuleTokenStream(this.adaptor, "token CLOSE_BRACE");
/* 16211 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT3rc_TYPE = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT3rc_TYPE");
/* 16212 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 16213 */     RewriteRuleTokenStream stream_OPEN_BRACE = new RewriteRuleTokenStream(this.adaptor, "token OPEN_BRACE");
/* 16214 */     RewriteRuleSubtreeStream stream_nonvoid_type_descriptor = new RewriteRuleSubtreeStream(this.adaptor, "rule nonvoid_type_descriptor");
/* 16215 */     RewriteRuleSubtreeStream stream_register_range = new RewriteRuleSubtreeStream(this.adaptor, "rule register_range");
/*       */     try
/*       */     {
/* 16221 */       INSTRUCTION_FORMAT3rc_TYPE441 = (Token)match(this.input, 98, FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5432);
/* 16222 */       stream_INSTRUCTION_FORMAT3rc_TYPE.add(INSTRUCTION_FORMAT3rc_TYPE441);
/*       */ 
/* 16224 */       OPEN_BRACE442 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format3rc_type5434);
/* 16225 */       stream_OPEN_BRACE.add(OPEN_BRACE442);
/*       */ 
/* 16227 */       pushFollow(FOLLOW_register_range_in_insn_format3rc_type5436);
/* 16228 */       register_range443 = register_range();
/* 16229 */       this.state._fsp -= 1;
/*       */ 
/* 16231 */       stream_register_range.add(register_range443.getTree());
/* 16232 */       CLOSE_BRACE444 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5438);
/* 16233 */       stream_CLOSE_BRACE.add(CLOSE_BRACE444);
/*       */ 
/* 16235 */       COMMA445 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format3rc_type5440);
/* 16236 */       stream_COMMA.add(COMMA445);
/*       */ 
/* 16238 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5442);
/* 16239 */       nonvoid_type_descriptor446 = nonvoid_type_descriptor();
/* 16240 */       this.state._fsp -= 1;
/*       */ 
/* 16242 */       stream_nonvoid_type_descriptor.add(nonvoid_type_descriptor446.getTree());
/*       */ 
/* 16250 */       retval.tree = root_0;
/* 16251 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 16253 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16258 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 16259 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(176, retval.start, "I_STATEMENT_FORMAT3rc_TYPE"), root_1);
/* 16260 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT3rc_TYPE.nextNode());
/* 16261 */       this.adaptor.addChild(root_1, stream_register_range.nextTree());
/* 16262 */       this.adaptor.addChild(root_1, stream_nonvoid_type_descriptor.nextTree());
/* 16263 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 16269 */       retval.tree = root_0;
/*       */ 
/* 16273 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16275 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16276 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16280 */       reportError(re);
/* 16281 */       recover(this.input, re);
/* 16282 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16287 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format3rmi_method_return insn_format3rmi_method()
/*       */     throws RecognitionException
/*       */   {
/* 16302 */     insn_format3rmi_method_return retval = new insn_format3rmi_method_return();
/* 16303 */     retval.start = this.input.LT(1);
/*       */ 
/* 16305 */     CommonTree root_0 = null;
/*       */ 
/* 16307 */     Token INSTRUCTION_FORMAT3rmi_METHOD447 = null;
/* 16308 */     Token OPEN_BRACE448 = null;
/* 16309 */     Token CLOSE_BRACE450 = null;
/* 16310 */     Token COMMA451 = null;
/* 16311 */     Token INLINE_INDEX452 = null;
/* 16312 */     ParserRuleReturnScope register_range449 = null;
/*       */ 
/* 16314 */     CommonTree INSTRUCTION_FORMAT3rmi_METHOD447_tree = null;
/* 16315 */     CommonTree OPEN_BRACE448_tree = null;
/* 16316 */     CommonTree CLOSE_BRACE450_tree = null;
/* 16317 */     CommonTree COMMA451_tree = null;
/* 16318 */     CommonTree INLINE_INDEX452_tree = null;
/*       */     try
/*       */     {
/* 16324 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16327 */       INSTRUCTION_FORMAT3rmi_METHOD447 = (Token)match(this.input, 99, FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method5474);
/* 16328 */       INSTRUCTION_FORMAT3rmi_METHOD447_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT3rmi_METHOD447);
/* 16329 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT3rmi_METHOD447_tree);
/*       */ 
/* 16331 */       OPEN_BRACE448 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format3rmi_method5476);
/* 16332 */       OPEN_BRACE448_tree = (CommonTree)this.adaptor.create(OPEN_BRACE448);
/* 16333 */       this.adaptor.addChild(root_0, OPEN_BRACE448_tree);
/*       */ 
/* 16335 */       pushFollow(FOLLOW_register_range_in_insn_format3rmi_method5478);
/* 16336 */       register_range449 = register_range();
/* 16337 */       this.state._fsp -= 1;
/*       */ 
/* 16339 */       this.adaptor.addChild(root_0, register_range449.getTree());
/*       */ 
/* 16341 */       CLOSE_BRACE450 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method5480);
/* 16342 */       CLOSE_BRACE450_tree = (CommonTree)this.adaptor.create(CLOSE_BRACE450);
/* 16343 */       this.adaptor.addChild(root_0, CLOSE_BRACE450_tree);
/*       */ 
/* 16345 */       COMMA451 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format3rmi_method5482);
/* 16346 */       COMMA451_tree = (CommonTree)this.adaptor.create(COMMA451);
/* 16347 */       this.adaptor.addChild(root_0, COMMA451_tree);
/*       */ 
/* 16349 */       INLINE_INDEX452 = (Token)match(this.input, 57, FOLLOW_INLINE_INDEX_in_insn_format3rmi_method5484);
/* 16350 */       INLINE_INDEX452_tree = (CommonTree)this.adaptor.create(INLINE_INDEX452);
/* 16351 */       this.adaptor.addChild(root_0, INLINE_INDEX452_tree);
/*       */ 
/* 16354 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT3rmi_METHOD447 != null ? INSTRUCTION_FORMAT3rmi_METHOD447.getText() : null);
/*       */ 
/* 16358 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16360 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16361 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16365 */       reportError(re);
/* 16366 */       recover(this.input, re);
/* 16367 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16372 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format3rms_method_return insn_format3rms_method()
/*       */     throws RecognitionException
/*       */   {
/* 16387 */     insn_format3rms_method_return retval = new insn_format3rms_method_return();
/* 16388 */     retval.start = this.input.LT(1);
/*       */ 
/* 16390 */     CommonTree root_0 = null;
/*       */ 
/* 16392 */     Token INSTRUCTION_FORMAT3rms_METHOD453 = null;
/* 16393 */     Token OPEN_BRACE454 = null;
/* 16394 */     Token CLOSE_BRACE456 = null;
/* 16395 */     Token COMMA457 = null;
/* 16396 */     Token VTABLE_INDEX458 = null;
/* 16397 */     ParserRuleReturnScope register_range455 = null;
/*       */ 
/* 16399 */     CommonTree INSTRUCTION_FORMAT3rms_METHOD453_tree = null;
/* 16400 */     CommonTree OPEN_BRACE454_tree = null;
/* 16401 */     CommonTree CLOSE_BRACE456_tree = null;
/* 16402 */     CommonTree COMMA457_tree = null;
/* 16403 */     CommonTree VTABLE_INDEX458_tree = null;
/*       */     try
/*       */     {
/* 16409 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16412 */       INSTRUCTION_FORMAT3rms_METHOD453 = (Token)match(this.input, 100, FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method5505);
/* 16413 */       INSTRUCTION_FORMAT3rms_METHOD453_tree = (CommonTree)this.adaptor.create(INSTRUCTION_FORMAT3rms_METHOD453);
/* 16414 */       this.adaptor.addChild(root_0, INSTRUCTION_FORMAT3rms_METHOD453_tree);
/*       */ 
/* 16416 */       OPEN_BRACE454 = (Token)match(this.input, 192, FOLLOW_OPEN_BRACE_in_insn_format3rms_method5507);
/* 16417 */       OPEN_BRACE454_tree = (CommonTree)this.adaptor.create(OPEN_BRACE454);
/* 16418 */       this.adaptor.addChild(root_0, OPEN_BRACE454_tree);
/*       */ 
/* 16420 */       pushFollow(FOLLOW_register_range_in_insn_format3rms_method5509);
/* 16421 */       register_range455 = register_range();
/* 16422 */       this.state._fsp -= 1;
/*       */ 
/* 16424 */       this.adaptor.addChild(root_0, register_range455.getTree());
/*       */ 
/* 16426 */       CLOSE_BRACE456 = (Token)match(this.input, 28, FOLLOW_CLOSE_BRACE_in_insn_format3rms_method5511);
/* 16427 */       CLOSE_BRACE456_tree = (CommonTree)this.adaptor.create(CLOSE_BRACE456);
/* 16428 */       this.adaptor.addChild(root_0, CLOSE_BRACE456_tree);
/*       */ 
/* 16430 */       COMMA457 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format3rms_method5513);
/* 16431 */       COMMA457_tree = (CommonTree)this.adaptor.create(COMMA457);
/* 16432 */       this.adaptor.addChild(root_0, COMMA457_tree);
/*       */ 
/* 16434 */       VTABLE_INDEX458 = (Token)match(this.input, 213, FOLLOW_VTABLE_INDEX_in_insn_format3rms_method5515);
/* 16435 */       VTABLE_INDEX458_tree = (CommonTree)this.adaptor.create(VTABLE_INDEX458);
/* 16436 */       this.adaptor.addChild(root_0, VTABLE_INDEX458_tree);
/*       */ 
/* 16439 */       throwOdexedInstructionException(this.input, INSTRUCTION_FORMAT3rms_METHOD453 != null ? INSTRUCTION_FORMAT3rms_METHOD453.getText() : null);
/*       */ 
/* 16443 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16445 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16446 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16450 */       reportError(re);
/* 16451 */       recover(this.input, re);
/* 16452 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16457 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_format51l_return insn_format51l()
/*       */     throws RecognitionException
/*       */   {
/* 16472 */     insn_format51l_return retval = new insn_format51l_return();
/* 16473 */     retval.start = this.input.LT(1);
/*       */ 
/* 16475 */     CommonTree root_0 = null;
/*       */ 
/* 16477 */     Token INSTRUCTION_FORMAT51l459 = null;
/* 16478 */     Token REGISTER460 = null;
/* 16479 */     Token COMMA461 = null;
/* 16480 */     ParserRuleReturnScope fixed_literal462 = null;
/*       */ 
/* 16482 */     CommonTree INSTRUCTION_FORMAT51l459_tree = null;
/* 16483 */     CommonTree REGISTER460_tree = null;
/* 16484 */     CommonTree COMMA461_tree = null;
/* 16485 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 16486 */     RewriteRuleTokenStream stream_REGISTER = new RewriteRuleTokenStream(this.adaptor, "token REGISTER");
/* 16487 */     RewriteRuleTokenStream stream_INSTRUCTION_FORMAT51l = new RewriteRuleTokenStream(this.adaptor, "token INSTRUCTION_FORMAT51l");
/* 16488 */     RewriteRuleSubtreeStream stream_fixed_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_literal");
/*       */     try
/*       */     {
/* 16494 */       INSTRUCTION_FORMAT51l459 = (Token)match(this.input, 101, FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l5536);
/* 16495 */       stream_INSTRUCTION_FORMAT51l.add(INSTRUCTION_FORMAT51l459);
/*       */ 
/* 16497 */       REGISTER460 = (Token)match(this.input, 201, FOLLOW_REGISTER_in_insn_format51l5538);
/* 16498 */       stream_REGISTER.add(REGISTER460);
/*       */ 
/* 16500 */       COMMA461 = (Token)match(this.input, 31, FOLLOW_COMMA_in_insn_format51l5540);
/* 16501 */       stream_COMMA.add(COMMA461);
/*       */ 
/* 16503 */       pushFollow(FOLLOW_fixed_literal_in_insn_format51l5542);
/* 16504 */       fixed_literal462 = fixed_literal();
/* 16505 */       this.state._fsp -= 1;
/*       */ 
/* 16507 */       stream_fixed_literal.add(fixed_literal462.getTree());
/*       */ 
/* 16515 */       retval.tree = root_0;
/* 16516 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 16518 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16523 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 16524 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(177, retval.start, "I_STATEMENT_FORMAT51l"), root_1);
/* 16525 */       this.adaptor.addChild(root_1, stream_INSTRUCTION_FORMAT51l.nextNode());
/* 16526 */       this.adaptor.addChild(root_1, stream_REGISTER.nextNode());
/* 16527 */       this.adaptor.addChild(root_1, stream_fixed_literal.nextTree());
/* 16528 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 16534 */       retval.tree = root_0;
/*       */ 
/* 16538 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16540 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16541 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16545 */       reportError(re);
/* 16546 */       recover(this.input, re);
/* 16547 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16552 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_array_data_directive_return insn_array_data_directive()
/*       */     throws RecognitionException
/*       */   {
/* 16567 */     insn_array_data_directive_return retval = new insn_array_data_directive_return();
/* 16568 */     retval.start = this.input.LT(1);
/*       */ 
/* 16570 */     CommonTree root_0 = null;
/*       */ 
/* 16572 */     Token ARRAY_DATA_DIRECTIVE463 = null;
/* 16573 */     Token END_ARRAY_DATA_DIRECTIVE466 = null;
/* 16574 */     ParserRuleReturnScope parsed_integer_literal464 = null;
/* 16575 */     ParserRuleReturnScope fixed_literal465 = null;
/*       */ 
/* 16577 */     CommonTree ARRAY_DATA_DIRECTIVE463_tree = null;
/* 16578 */     CommonTree END_ARRAY_DATA_DIRECTIVE466_tree = null;
/* 16579 */     RewriteRuleTokenStream stream_ARRAY_DATA_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token ARRAY_DATA_DIRECTIVE");
/* 16580 */     RewriteRuleTokenStream stream_END_ARRAY_DATA_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_ARRAY_DATA_DIRECTIVE");
/* 16581 */     RewriteRuleSubtreeStream stream_fixed_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_literal");
/* 16582 */     RewriteRuleSubtreeStream stream_parsed_integer_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule parsed_integer_literal");
/*       */     try
/*       */     {
/* 16588 */       ARRAY_DATA_DIRECTIVE463 = (Token)match(this.input, 7, FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive5569);
/* 16589 */       stream_ARRAY_DATA_DIRECTIVE.add(ARRAY_DATA_DIRECTIVE463);
/*       */ 
/* 16591 */       pushFollow(FOLLOW_parsed_integer_literal_in_insn_array_data_directive5575);
/* 16592 */       parsed_integer_literal464 = parsed_integer_literal();
/* 16593 */       this.state._fsp -= 1;
/*       */ 
/* 16595 */       stream_parsed_integer_literal.add(parsed_integer_literal464.getTree());
/*       */ 
/* 16597 */       int elementWidth = parsed_integer_literal464 != null ? ((parsed_integer_literal_return)parsed_integer_literal464).value : 0;
/* 16598 */       if ((elementWidth != 4) && (elementWidth != 8) && (elementWidth != 1) && (elementWidth != 2)) {
/*       */         throw new SemanticException(this.input, retval.start, "Invalid element width: %d. Must be 1, 2, 4 or 8", new Object[] { Integer.valueOf(elementWidth) });
/*       */       }
/*       */ 
/*       */       while (true)
/*       */       {
/* 16605 */         int alt45 = 2;
/* 16606 */         int LA45_0 = this.input.LA(1);
/* 16607 */         if (((LA45_0 >= 21) && (LA45_0 <= 22)) || (LA45_0 == 25) || ((LA45_0 >= 34) && (LA45_0 <= 35)) || ((LA45_0 >= 51) && (LA45_0 <= 52)) || (LA45_0 == 187) || (LA45_0 == 190) || (LA45_0 == 198) || (LA45_0 == 204)) {
/* 16608 */           alt45 = 1;
/*       */         }
/*       */ 
/* 16611 */         switch (alt45)
/*       */         {
/*       */         case 1:
/* 16615 */           pushFollow(FOLLOW_fixed_literal_in_insn_array_data_directive5587);
/* 16616 */           fixed_literal465 = fixed_literal();
/* 16617 */           this.state._fsp -= 1;
/*       */ 
/* 16619 */           stream_fixed_literal.add(fixed_literal465.getTree());
/*       */ 
/* 16621 */           break;
/*       */         default:
/* 16624 */           break label401;
/*       */         }
/*       */       }
/*       */ 
/* 16628 */       label401: END_ARRAY_DATA_DIRECTIVE466 = (Token)match(this.input, 37, FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive5590);
/* 16629 */       stream_END_ARRAY_DATA_DIRECTIVE.add(END_ARRAY_DATA_DIRECTIVE466);
/*       */ 
/* 16638 */       retval.tree = root_0;
/* 16639 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 16641 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16646 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 16647 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(146, retval.start, "I_STATEMENT_ARRAY_DATA"), root_1);
/*       */ 
/* 16650 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 16651 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(109, "I_ARRAY_ELEMENT_SIZE"), root_2);
/* 16652 */       this.adaptor.addChild(root_2, stream_parsed_integer_literal.nextTree());
/* 16653 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/* 16658 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 16659 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(108, "I_ARRAY_ELEMENTS"), root_2);
/*       */ 
/* 16661 */       while (stream_fixed_literal.hasNext()) {
/* 16662 */         this.adaptor.addChild(root_2, stream_fixed_literal.nextTree());
/*       */       }
/* 16664 */       stream_fixed_literal.reset();
/*       */ 
/* 16666 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/* 16669 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 16675 */       retval.tree = root_0;
/*       */ 
/* 16679 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16681 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16682 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16686 */       reportError(re);
/* 16687 */       recover(this.input, re);
/* 16688 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16693 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_packed_switch_directive_return insn_packed_switch_directive()
/*       */     throws RecognitionException
/*       */   {
/* 16708 */     insn_packed_switch_directive_return retval = new insn_packed_switch_directive_return();
/* 16709 */     retval.start = this.input.LT(1);
/*       */ 
/* 16711 */     CommonTree root_0 = null;
/*       */ 
/* 16713 */     Token PACKED_SWITCH_DIRECTIVE467 = null;
/* 16714 */     Token END_PACKED_SWITCH_DIRECTIVE470 = null;
/* 16715 */     ParserRuleReturnScope fixed_32bit_literal468 = null;
/* 16716 */     ParserRuleReturnScope label_ref469 = null;
/*       */ 
/* 16718 */     CommonTree PACKED_SWITCH_DIRECTIVE467_tree = null;
/* 16719 */     CommonTree END_PACKED_SWITCH_DIRECTIVE470_tree = null;
/* 16720 */     RewriteRuleTokenStream stream_PACKED_SWITCH_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token PACKED_SWITCH_DIRECTIVE");
/* 16721 */     RewriteRuleTokenStream stream_END_PACKED_SWITCH_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_PACKED_SWITCH_DIRECTIVE");
/* 16722 */     RewriteRuleSubtreeStream stream_fixed_32bit_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
/* 16723 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 16729 */       PACKED_SWITCH_DIRECTIVE467 = (Token)match(this.input, 194, FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive5636);
/* 16730 */       stream_PACKED_SWITCH_DIRECTIVE.add(PACKED_SWITCH_DIRECTIVE467);
/*       */ 
/* 16732 */       pushFollow(FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive5642);
/* 16733 */       fixed_32bit_literal468 = fixed_32bit_literal();
/* 16734 */       this.state._fsp -= 1;
/*       */ 
/* 16736 */       stream_fixed_32bit_literal.add(fixed_32bit_literal468.getTree());
/*       */       while (true)
/*       */       {
/* 16740 */         int alt46 = 2;
/* 16741 */         int LA46_0 = this.input.LA(1);
/* 16742 */         if (LA46_0 == 30) {
/* 16743 */           alt46 = 1;
/*       */         }
/*       */ 
/* 16746 */         switch (alt46)
/*       */         {
/*       */         case 1:
/* 16750 */           pushFollow(FOLLOW_label_ref_in_insn_packed_switch_directive5648);
/* 16751 */           label_ref469 = label_ref();
/* 16752 */           this.state._fsp -= 1;
/*       */ 
/* 16754 */           stream_label_ref.add(label_ref469.getTree());
/*       */ 
/* 16756 */           break;
/*       */         default:
/* 16759 */           break label257;
/*       */         }
/*       */       }
/*       */ 
/* 16763 */       label257: END_PACKED_SWITCH_DIRECTIVE470 = (Token)match(this.input, 41, FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive5655);
/* 16764 */       stream_END_PACKED_SWITCH_DIRECTIVE.add(END_PACKED_SWITCH_DIRECTIVE470);
/*       */ 
/* 16773 */       retval.tree = root_0;
/* 16774 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 16776 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16781 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 16782 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(178, retval.start, "I_STATEMENT_PACKED_SWITCH"), root_1);
/*       */ 
/* 16785 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 16786 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(135, retval.start, "I_PACKED_SWITCH_START_KEY"), root_2);
/* 16787 */       this.adaptor.addChild(root_2, stream_fixed_32bit_literal.nextTree());
/* 16788 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/* 16793 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 16794 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(134, retval.start, "I_PACKED_SWITCH_ELEMENTS"), root_2);
/*       */ 
/* 16796 */       while (stream_label_ref.hasNext()) {
/* 16797 */         this.adaptor.addChild(root_2, stream_label_ref.nextTree());
/*       */       }
/* 16799 */       stream_label_ref.reset();
/*       */ 
/* 16801 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/* 16804 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 16810 */       retval.tree = root_0;
/*       */ 
/* 16814 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16816 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16817 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16821 */       reportError(re);
/* 16822 */       recover(this.input, re);
/* 16823 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16828 */     return retval;
/*       */   }
/*       */ 
/*       */   public final insn_sparse_switch_directive_return insn_sparse_switch_directive()
/*       */     throws RecognitionException
/*       */   {
/* 16843 */     insn_sparse_switch_directive_return retval = new insn_sparse_switch_directive_return();
/* 16844 */     retval.start = this.input.LT(1);
/*       */ 
/* 16846 */     CommonTree root_0 = null;
/*       */ 
/* 16848 */     Token SPARSE_SWITCH_DIRECTIVE471 = null;
/* 16849 */     Token ARROW473 = null;
/* 16850 */     Token END_SPARSE_SWITCH_DIRECTIVE475 = null;
/* 16851 */     ParserRuleReturnScope fixed_32bit_literal472 = null;
/* 16852 */     ParserRuleReturnScope label_ref474 = null;
/*       */ 
/* 16854 */     CommonTree SPARSE_SWITCH_DIRECTIVE471_tree = null;
/* 16855 */     CommonTree ARROW473_tree = null;
/* 16856 */     CommonTree END_SPARSE_SWITCH_DIRECTIVE475_tree = null;
/* 16857 */     RewriteRuleTokenStream stream_SPARSE_SWITCH_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token SPARSE_SWITCH_DIRECTIVE");
/* 16858 */     RewriteRuleTokenStream stream_ARROW = new RewriteRuleTokenStream(this.adaptor, "token ARROW");
/* 16859 */     RewriteRuleTokenStream stream_END_SPARSE_SWITCH_DIRECTIVE = new RewriteRuleTokenStream(this.adaptor, "token END_SPARSE_SWITCH_DIRECTIVE");
/* 16860 */     RewriteRuleSubtreeStream stream_fixed_32bit_literal = new RewriteRuleSubtreeStream(this.adaptor, "rule fixed_32bit_literal");
/* 16861 */     RewriteRuleSubtreeStream stream_label_ref = new RewriteRuleSubtreeStream(this.adaptor, "rule label_ref");
/*       */     try
/*       */     {
/* 16867 */       SPARSE_SWITCH_DIRECTIVE471 = (Token)match(this.input, 207, FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive5729);
/* 16868 */       stream_SPARSE_SWITCH_DIRECTIVE.add(SPARSE_SWITCH_DIRECTIVE471);
/*       */       while (true)
/*       */       {
/* 16873 */         int alt47 = 2;
/* 16874 */         int LA47_0 = this.input.LA(1);
/* 16875 */         if (((LA47_0 >= 21) && (LA47_0 <= 22)) || (LA47_0 == 25) || ((LA47_0 >= 51) && (LA47_0 <= 52)) || (LA47_0 == 187) || (LA47_0 == 190) || (LA47_0 == 198) || (LA47_0 == 204)) {
/* 16876 */           alt47 = 1;
/*       */         }
/*       */ 
/* 16879 */         switch (alt47)
/*       */         {
/*       */         case 1:
/* 16883 */           pushFollow(FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive5736);
/* 16884 */           fixed_32bit_literal472 = fixed_32bit_literal();
/* 16885 */           this.state._fsp -= 1;
/*       */ 
/* 16887 */           stream_fixed_32bit_literal.add(fixed_32bit_literal472.getTree());
/* 16888 */           ARROW473 = (Token)match(this.input, 9, FOLLOW_ARROW_in_insn_sparse_switch_directive5738);
/* 16889 */           stream_ARROW.add(ARROW473);
/*       */ 
/* 16891 */           pushFollow(FOLLOW_label_ref_in_insn_sparse_switch_directive5740);
/* 16892 */           label_ref474 = label_ref();
/* 16893 */           this.state._fsp -= 1;
/*       */ 
/* 16895 */           stream_label_ref.add(label_ref474.getTree());
/*       */ 
/* 16897 */           break;
/*       */         default:
/* 16900 */           break label362;
/*       */         }
/*       */       }
/*       */ 
/* 16904 */       label362: END_SPARSE_SWITCH_DIRECTIVE475 = (Token)match(this.input, 43, FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive5748);
/* 16905 */       stream_END_SPARSE_SWITCH_DIRECTIVE.add(END_SPARSE_SWITCH_DIRECTIVE475);
/*       */ 
/* 16914 */       retval.tree = root_0;
/* 16915 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
/*       */ 
/* 16917 */       root_0 = (CommonTree)this.adaptor.nil();
/*       */ 
/* 16922 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 16923 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(179, retval.start, "I_STATEMENT_SPARSE_SWITCH"), root_1);
/*       */ 
/* 16926 */       CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 16927 */       root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(145, retval.start, "I_SPARSE_SWITCH_ELEMENTS"), root_2);
/*       */ 
/* 16929 */       while ((stream_label_ref.hasNext()) || (stream_fixed_32bit_literal.hasNext())) {
/* 16930 */         this.adaptor.addChild(root_2, stream_fixed_32bit_literal.nextTree());
/* 16931 */         this.adaptor.addChild(root_2, stream_label_ref.nextTree());
/*       */       }
/* 16933 */       stream_label_ref.reset();
/* 16934 */       stream_fixed_32bit_literal.reset();
/*       */ 
/* 16936 */       this.adaptor.addChild(root_1, root_2);
/*       */ 
/* 16939 */       this.adaptor.addChild(root_0, root_1);
/*       */ 
/* 16945 */       retval.tree = root_0;
/*       */ 
/* 16949 */       retval.stop = this.input.LT(-1);
/*       */ 
/* 16951 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 16952 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*       */     }
/*       */     catch (RecognitionException re)
/*       */     {
/* 16956 */       reportError(re);
/* 16957 */       recover(this.input, re);
/* 16958 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*       */     }
/*       */     finally
/*       */     {
/*       */     }
/* 16963 */     return retval;
/*       */   }
/*       */ 
/*       */   static
/*       */   {
/* 17062 */     int numStates = DFA33_transitionS.length;
/* 17063 */     DFA33_transition = new short[numStates][];
/* 17064 */     for (int i = 0; i < numStates; i++) {
/* 17065 */       DFA33_transition[i] = DFA.unpackEncodedString(DFA33_transitionS[i]);
/*       */     }
/*       */ 
/* 17111 */     FOLLOW_class_spec_in_smali_file1145 = new BitSet(new long[] { 72620544125567008L, 0L, 2305843009213693952L, 278528L });
/* 17112 */     FOLLOW_super_spec_in_smali_file1156 = new BitSet(new long[] { 72620544125567008L, 0L, 2305843009213693952L, 278528L });
/* 17113 */     FOLLOW_implements_spec_in_smali_file1164 = new BitSet(new long[] { 72620544125567008L, 0L, 2305843009213693952L, 278528L });
/* 17114 */     FOLLOW_source_spec_in_smali_file1173 = new BitSet(new long[] { 72620544125567008L, 0L, 2305843009213693952L, 278528L });
/* 17115 */     FOLLOW_method_in_smali_file1181 = new BitSet(new long[] { 72620544125567008L, 0L, 2305843009213693952L, 278528L });
/* 17116 */     FOLLOW_field_in_smali_file1187 = new BitSet(new long[] { 72620544125567008L, 0L, 2305843009213693952L, 278528L });
/* 17117 */     FOLLOW_annotation_in_smali_file1193 = new BitSet(new long[] { 72620544125567008L, 0L, 2305843009213693952L, 278528L });
/* 17118 */     FOLLOW_EOF_in_smali_file1204 = new BitSet(new long[] { 2L });
/* 17119 */     FOLLOW_CLASS_DIRECTIVE_in_class_spec1291 = new BitSet(new long[] { 67108880L });
/* 17120 */     FOLLOW_access_list_in_class_spec1293 = new BitSet(new long[] { 67108864L });
/* 17121 */     FOLLOW_CLASS_DESCRIPTOR_in_class_spec1295 = new BitSet(new long[] { 2L });
/* 17122 */     FOLLOW_SUPER_DIRECTIVE_in_super_spec1313 = new BitSet(new long[] { 67108864L });
/* 17123 */     FOLLOW_CLASS_DESCRIPTOR_in_super_spec1315 = new BitSet(new long[] { 2L });
/* 17124 */     FOLLOW_IMPLEMENTS_DIRECTIVE_in_implements_spec1334 = new BitSet(new long[] { 67108864L });
/* 17125 */     FOLLOW_CLASS_DESCRIPTOR_in_implements_spec1336 = new BitSet(new long[] { 2L });
/* 17126 */     FOLLOW_SOURCE_DIRECTIVE_in_source_spec1355 = new BitSet(new long[] { 0L, 0L, 0L, 65536L });
/* 17127 */     FOLLOW_STRING_LITERAL_in_source_spec1357 = new BitSet(new long[] { 2L });
/* 17128 */     FOLLOW_ACCESS_SPEC_in_access_list1376 = new BitSet(new long[] { 18L });
/* 17129 */     FOLLOW_FIELD_DIRECTIVE_in_field1407 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -3458764513820540928L, 1581792L });
/* 17130 */     FOLLOW_access_list_in_field1409 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -3458764513820540928L, 1581792L });
/* 17131 */     FOLLOW_member_name_in_field1411 = new BitSet(new long[] { 1073741824L });
/* 17132 */     FOLLOW_COLON_in_field1413 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17133 */     FOLLOW_nonvoid_type_descriptor_in_field1415 = new BitSet(new long[] { 141012366262306L });
/* 17134 */     FOLLOW_EQUAL_in_field1418 = new BitSet(new long[] { 6790635459707136L, 0L, -4035225266123964416L, 1249473L });
/* 17135 */     FOLLOW_literal_in_field1420 = new BitSet(new long[] { 274877906978L });
/* 17136 */     FOLLOW_annotation_in_field1433 = new BitSet(new long[] { 274877906978L });
/* 17137 */     FOLLOW_END_FIELD_DIRECTIVE_in_field1447 = new BitSet(new long[] { 2L });
/* 17138 */     FOLLOW_METHOD_DIRECTIVE_in_method1558 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -3458764513820540928L, 1581792L });
/* 17139 */     FOLLOW_access_list_in_method1560 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -3458764513820540928L, 1581792L });
/* 17140 */     FOLLOW_member_name_in_method1562 = new BitSet(new long[] { 0L, 0L, 0L, 2L });
/* 17141 */     FOLLOW_method_prototype_in_method1564 = new BitSet(new long[] { -288158357041184608L, 274877906943L, 504403158265495552L, 52492L });
/* 17142 */     FOLLOW_statements_and_directives_in_method1566 = new BitSet(new long[] { 1099511627776L });
/* 17143 */     FOLLOW_END_METHOD_DIRECTIVE_in_method1572 = new BitSet(new long[] { 2L });
/* 17144 */     FOLLOW_ordered_method_item_in_statements_and_directives1617 = new BitSet(new long[] { -288159456552812382L, 274877906943L, 504403158265495552L, 52492L });
/* 17145 */     FOLLOW_registers_directive_in_statements_and_directives1625 = new BitSet(new long[] { -288159456552812382L, 274877906943L, 504403158265495552L, 52492L });
/* 17146 */     FOLLOW_catch_directive_in_statements_and_directives1633 = new BitSet(new long[] { -288159456552812382L, 274877906943L, 504403158265495552L, 52492L });
/* 17147 */     FOLLOW_catchall_directive_in_statements_and_directives1641 = new BitSet(new long[] { -288159456552812382L, 274877906943L, 504403158265495552L, 52492L });
/* 17148 */     FOLLOW_parameter_directive_in_statements_and_directives1649 = new BitSet(new long[] { -288159456552812382L, 274877906943L, 504403158265495552L, 52492L });
/* 17149 */     FOLLOW_annotation_in_statements_and_directives1657 = new BitSet(new long[] { -288159456552812382L, 274877906943L, 504403158265495552L, 52492L });
/* 17150 */     FOLLOW_label_in_ordered_method_item1742 = new BitSet(new long[] { 2L });
/* 17151 */     FOLLOW_instruction_in_ordered_method_item1748 = new BitSet(new long[] { 2L });
/* 17152 */     FOLLOW_debug_directive_in_ordered_method_item1754 = new BitSet(new long[] { 2L });
/* 17153 */     FOLLOW_REGISTERS_DIRECTIVE_in_registers_directive1774 = new BitSet(new long[] { 37748736L, 0L, 5188146770730811392L, 4160L });
/* 17154 */     FOLLOW_integral_literal_in_registers_directive1778 = new BitSet(new long[] { 2L });
/* 17155 */     FOLLOW_LOCALS_DIRECTIVE_in_registers_directive1798 = new BitSet(new long[] { 37748736L, 0L, 5188146770730811392L, 4160L });
/* 17156 */     FOLLOW_integral_literal_in_registers_directive1802 = new BitSet(new long[] { 2L });
/* 17157 */     FOLLOW_SIMPLE_NAME_in_simple_name1836 = new BitSet(new long[] { 2L });
/* 17158 */     FOLLOW_ACCESS_SPEC_in_simple_name1842 = new BitSet(new long[] { 2L });
/* 17159 */     FOLLOW_VERIFICATION_ERROR_TYPE_in_simple_name1853 = new BitSet(new long[] { 2L });
/* 17160 */     FOLLOW_POSITIVE_INTEGER_LITERAL_in_simple_name1864 = new BitSet(new long[] { 2L });
/* 17161 */     FOLLOW_NEGATIVE_INTEGER_LITERAL_in_simple_name1875 = new BitSet(new long[] { 2L });
/* 17162 */     FOLLOW_FLOAT_LITERAL_OR_ID_in_simple_name1886 = new BitSet(new long[] { 2L });
/* 17163 */     FOLLOW_DOUBLE_LITERAL_OR_ID_in_simple_name1897 = new BitSet(new long[] { 2L });
/* 17164 */     FOLLOW_BOOL_LITERAL_in_simple_name1908 = new BitSet(new long[] { 2L });
/* 17165 */     FOLLOW_NULL_LITERAL_in_simple_name1919 = new BitSet(new long[] { 2L });
/* 17166 */     FOLLOW_REGISTER_in_simple_name1930 = new BitSet(new long[] { 2L });
/* 17167 */     FOLLOW_PARAM_LIST_OR_ID_in_simple_name1941 = new BitSet(new long[] { 2L });
/* 17168 */     FOLLOW_PRIMITIVE_TYPE_in_simple_name1952 = new BitSet(new long[] { 2L });
/* 17169 */     FOLLOW_VOID_TYPE_in_simple_name1963 = new BitSet(new long[] { 2L });
/* 17170 */     FOLLOW_ANNOTATION_VISIBILITY_in_simple_name1974 = new BitSet(new long[] { 2L });
/* 17171 */     FOLLOW_INSTRUCTION_FORMAT10t_in_simple_name1985 = new BitSet(new long[] { 2L });
/* 17172 */     FOLLOW_INSTRUCTION_FORMAT10x_in_simple_name1996 = new BitSet(new long[] { 2L });
/* 17173 */     FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_simple_name2007 = new BitSet(new long[] { 2L });
/* 17174 */     FOLLOW_INSTRUCTION_FORMAT11x_in_simple_name2018 = new BitSet(new long[] { 2L });
/* 17175 */     FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_simple_name2029 = new BitSet(new long[] { 2L });
/* 17176 */     FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_simple_name2040 = new BitSet(new long[] { 2L });
/* 17177 */     FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_simple_name2051 = new BitSet(new long[] { 2L });
/* 17178 */     FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_simple_name2062 = new BitSet(new long[] { 2L });
/* 17179 */     FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_simple_name2073 = new BitSet(new long[] { 2L });
/* 17180 */     FOLLOW_INSTRUCTION_FORMAT21t_in_simple_name2084 = new BitSet(new long[] { 2L });
/* 17181 */     FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_simple_name2095 = new BitSet(new long[] { 2L });
/* 17182 */     FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_simple_name2106 = new BitSet(new long[] { 2L });
/* 17183 */     FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_simple_name2117 = new BitSet(new long[] { 2L });
/* 17184 */     FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_simple_name2128 = new BitSet(new long[] { 2L });
/* 17185 */     FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_simple_name2139 = new BitSet(new long[] { 2L });
/* 17186 */     FOLLOW_INSTRUCTION_FORMAT22t_in_simple_name2150 = new BitSet(new long[] { 2L });
/* 17187 */     FOLLOW_INSTRUCTION_FORMAT23x_in_simple_name2161 = new BitSet(new long[] { 2L });
/* 17188 */     FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_simple_name2172 = new BitSet(new long[] { 2L });
/* 17189 */     FOLLOW_INSTRUCTION_FORMAT31t_in_simple_name2183 = new BitSet(new long[] { 2L });
/* 17190 */     FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_simple_name2194 = new BitSet(new long[] { 2L });
/* 17191 */     FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_simple_name2205 = new BitSet(new long[] { 2L });
/* 17192 */     FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_simple_name2216 = new BitSet(new long[] { 2L });
/* 17193 */     FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_simple_name2227 = new BitSet(new long[] { 2L });
/* 17194 */     FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_simple_name2238 = new BitSet(new long[] { 2L });
/* 17195 */     FOLLOW_INSTRUCTION_FORMAT51l_in_simple_name2249 = new BitSet(new long[] { 2L });
/* 17196 */     FOLLOW_simple_name_in_member_name2264 = new BitSet(new long[] { 2L });
/* 17197 */     FOLLOW_MEMBER_NAME_in_member_name2270 = new BitSet(new long[] { 2L });
/* 17198 */     FOLLOW_OPEN_PAREN_in_method_prototype2285 = new BitSet(new long[] { 603980032L, 0L, 0L, 176L });
/* 17199 */     FOLLOW_param_list_in_method_prototype2287 = new BitSet(new long[] { 536870912L });
/* 17200 */     FOLLOW_CLOSE_PAREN_in_method_prototype2289 = new BitSet(new long[] { 67109120L, 0L, 0L, 1048704L });
/* 17201 */     FOLLOW_type_descriptor_in_method_prototype2291 = new BitSet(new long[] { 2L });
/* 17202 */     FOLLOW_PARAM_LIST_in_param_list2321 = new BitSet(new long[] { 2L });
/* 17203 */     FOLLOW_PARAM_LIST_OR_ID_in_param_list2331 = new BitSet(new long[] { 2L });
/* 17204 */     FOLLOW_nonvoid_type_descriptor_in_param_list2341 = new BitSet(new long[] { 67109122L, 0L, 0L, 128L });
/* 17205 */     FOLLOW_POSITIVE_INTEGER_LITERAL_in_integer_literal2418 = new BitSet(new long[] { 2L });
/* 17206 */     FOLLOW_NEGATIVE_INTEGER_LITERAL_in_integer_literal2429 = new BitSet(new long[] { 2L });
/* 17207 */     FOLLOW_FLOAT_LITERAL_OR_ID_in_float_literal2444 = new BitSet(new long[] { 2L });
/* 17208 */     FOLLOW_FLOAT_LITERAL_in_float_literal2455 = new BitSet(new long[] { 2L });
/* 17209 */     FOLLOW_DOUBLE_LITERAL_OR_ID_in_double_literal2465 = new BitSet(new long[] { 2L });
/* 17210 */     FOLLOW_DOUBLE_LITERAL_in_double_literal2476 = new BitSet(new long[] { 2L });
/* 17211 */     FOLLOW_LONG_LITERAL_in_literal2486 = new BitSet(new long[] { 2L });
/* 17212 */     FOLLOW_integer_literal_in_literal2492 = new BitSet(new long[] { 2L });
/* 17213 */     FOLLOW_SHORT_LITERAL_in_literal2498 = new BitSet(new long[] { 2L });
/* 17214 */     FOLLOW_BYTE_LITERAL_in_literal2504 = new BitSet(new long[] { 2L });
/* 17215 */     FOLLOW_float_literal_in_literal2510 = new BitSet(new long[] { 2L });
/* 17216 */     FOLLOW_double_literal_in_literal2516 = new BitSet(new long[] { 2L });
/* 17217 */     FOLLOW_CHAR_LITERAL_in_literal2522 = new BitSet(new long[] { 2L });
/* 17218 */     FOLLOW_STRING_LITERAL_in_literal2528 = new BitSet(new long[] { 2L });
/* 17219 */     FOLLOW_BOOL_LITERAL_in_literal2534 = new BitSet(new long[] { 2L });
/* 17220 */     FOLLOW_NULL_LITERAL_in_literal2540 = new BitSet(new long[] { 2L });
/* 17221 */     FOLLOW_array_literal_in_literal2546 = new BitSet(new long[] { 2L });
/* 17222 */     FOLLOW_subannotation_in_literal2552 = new BitSet(new long[] { 2L });
/* 17223 */     FOLLOW_type_field_method_literal_in_literal2558 = new BitSet(new long[] { 2L });
/* 17224 */     FOLLOW_enum_literal_in_literal2564 = new BitSet(new long[] { 2L });
/* 17225 */     FOLLOW_integer_literal_in_parsed_integer_literal2577 = new BitSet(new long[] { 2L });
/* 17226 */     FOLLOW_LONG_LITERAL_in_integral_literal2589 = new BitSet(new long[] { 2L });
/* 17227 */     FOLLOW_integer_literal_in_integral_literal2595 = new BitSet(new long[] { 2L });
/* 17228 */     FOLLOW_SHORT_LITERAL_in_integral_literal2601 = new BitSet(new long[] { 2L });
/* 17229 */     FOLLOW_CHAR_LITERAL_in_integral_literal2607 = new BitSet(new long[] { 2L });
/* 17230 */     FOLLOW_BYTE_LITERAL_in_integral_literal2613 = new BitSet(new long[] { 2L });
/* 17231 */     FOLLOW_LONG_LITERAL_in_fixed_32bit_literal2623 = new BitSet(new long[] { 2L });
/* 17232 */     FOLLOW_integer_literal_in_fixed_32bit_literal2629 = new BitSet(new long[] { 2L });
/* 17233 */     FOLLOW_SHORT_LITERAL_in_fixed_32bit_literal2635 = new BitSet(new long[] { 2L });
/* 17234 */     FOLLOW_BYTE_LITERAL_in_fixed_32bit_literal2641 = new BitSet(new long[] { 2L });
/* 17235 */     FOLLOW_float_literal_in_fixed_32bit_literal2647 = new BitSet(new long[] { 2L });
/* 17236 */     FOLLOW_CHAR_LITERAL_in_fixed_32bit_literal2653 = new BitSet(new long[] { 2L });
/* 17237 */     FOLLOW_BOOL_LITERAL_in_fixed_32bit_literal2659 = new BitSet(new long[] { 2L });
/* 17238 */     FOLLOW_integer_literal_in_fixed_literal2669 = new BitSet(new long[] { 2L });
/* 17239 */     FOLLOW_LONG_LITERAL_in_fixed_literal2675 = new BitSet(new long[] { 2L });
/* 17240 */     FOLLOW_SHORT_LITERAL_in_fixed_literal2681 = new BitSet(new long[] { 2L });
/* 17241 */     FOLLOW_BYTE_LITERAL_in_fixed_literal2687 = new BitSet(new long[] { 2L });
/* 17242 */     FOLLOW_float_literal_in_fixed_literal2693 = new BitSet(new long[] { 2L });
/* 17243 */     FOLLOW_double_literal_in_fixed_literal2699 = new BitSet(new long[] { 2L });
/* 17244 */     FOLLOW_CHAR_LITERAL_in_fixed_literal2705 = new BitSet(new long[] { 2L });
/* 17245 */     FOLLOW_BOOL_LITERAL_in_fixed_literal2711 = new BitSet(new long[] { 2L });
/* 17246 */     FOLLOW_OPEN_BRACE_in_array_literal2721 = new BitSet(new long[] { 6790635728142592L, 0L, -4035225266123964416L, 1249473L });
/* 17247 */     FOLLOW_literal_in_array_literal2724 = new BitSet(new long[] { 2415919104L });
/* 17248 */     FOLLOW_COMMA_in_array_literal2727 = new BitSet(new long[] { 6790635459707136L, 0L, -4035225266123964416L, 1249473L });
/* 17249 */     FOLLOW_literal_in_array_literal2729 = new BitSet(new long[] { 2415919104L });
/* 17250 */     FOLLOW_CLOSE_BRACE_in_array_literal2737 = new BitSet(new long[] { 2L });
/* 17251 */     FOLLOW_simple_name_in_annotation_element2761 = new BitSet(new long[] { 140737488355328L });
/* 17252 */     FOLLOW_EQUAL_in_annotation_element2763 = new BitSet(new long[] { 6790635459707136L, 0L, -4035225266123964416L, 1249473L });
/* 17253 */     FOLLOW_literal_in_annotation_element2765 = new BitSet(new long[] { 2L });
/* 17254 */     FOLLOW_ANNOTATION_DIRECTIVE_in_annotation2790 = new BitSet(new long[] { 64L });
/* 17255 */     FOLLOW_ANNOTATION_VISIBILITY_in_annotation2792 = new BitSet(new long[] { 67108864L });
/* 17256 */     FOLLOW_CLASS_DESCRIPTOR_in_annotation2794 = new BitSet(new long[] { 6633802354198052944L, 141651539065L, -4611686018427387904L, 1581792L });
/* 17257 */     FOLLOW_annotation_element_in_annotation2800 = new BitSet(new long[] { 6633802354198052944L, 141651539065L, -4611686018427387904L, 1581792L });
/* 17258 */     FOLLOW_END_ANNOTATION_DIRECTIVE_in_annotation2803 = new BitSet(new long[] { 2L });
/* 17259 */     FOLLOW_SUBANNOTATION_DIRECTIVE_in_subannotation2836 = new BitSet(new long[] { 67108864L });
/* 17260 */     FOLLOW_CLASS_DESCRIPTOR_in_subannotation2838 = new BitSet(new long[] { 6633819877664620624L, 141651539065L, -4611686018427387904L, 1581792L });
/* 17261 */     FOLLOW_annotation_element_in_subannotation2840 = new BitSet(new long[] { 6633819877664620624L, 141651539065L, -4611686018427387904L, 1581792L });
/* 17262 */     FOLLOW_END_SUBANNOTATION_DIRECTIVE_in_subannotation2843 = new BitSet(new long[] { 2L });
/* 17263 */     FOLLOW_ENUM_DIRECTIVE_in_enum_literal2869 = new BitSet(new long[] { 67109120L });
/* 17264 */     FOLLOW_reference_type_descriptor_in_enum_literal2871 = new BitSet(new long[] { 512L });
/* 17265 */     FOLLOW_ARROW_in_enum_literal2873 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -4611686018427387904L, 1581792L });
/* 17266 */     FOLLOW_simple_name_in_enum_literal2875 = new BitSet(new long[] { 1073741824L });
/* 17267 */     FOLLOW_COLON_in_enum_literal2877 = new BitSet(new long[] { 67109120L });
/* 17268 */     FOLLOW_reference_type_descriptor_in_enum_literal2879 = new BitSet(new long[] { 2L });
/* 17269 */     FOLLOW_reference_type_descriptor_in_type_field_method_literal2903 = new BitSet(new long[] { 514L });
/* 17270 */     FOLLOW_ARROW_in_type_field_method_literal2911 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -3458764513820540928L, 1581792L });
/* 17271 */     FOLLOW_member_name_in_type_field_method_literal2921 = new BitSet(new long[] { 1073741824L });
/* 17272 */     FOLLOW_COLON_in_type_field_method_literal2923 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17273 */     FOLLOW_nonvoid_type_descriptor_in_type_field_method_literal2925 = new BitSet(new long[] { 2L });
/* 17274 */     FOLLOW_member_name_in_type_field_method_literal2947 = new BitSet(new long[] { 0L, 0L, 0L, 2L });
/* 17275 */     FOLLOW_method_prototype_in_type_field_method_literal2949 = new BitSet(new long[] { 2L });
/* 17276 */     FOLLOW_PRIMITIVE_TYPE_in_type_field_method_literal2991 = new BitSet(new long[] { 2L });
/* 17277 */     FOLLOW_VOID_TYPE_in_type_field_method_literal2997 = new BitSet(new long[] { 2L });
/* 17278 */     FOLLOW_reference_type_descriptor_in_fully_qualified_method3007 = new BitSet(new long[] { 512L });
/* 17279 */     FOLLOW_ARROW_in_fully_qualified_method3009 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -3458764513820540928L, 1581792L });
/* 17280 */     FOLLOW_member_name_in_fully_qualified_method3011 = new BitSet(new long[] { 0L, 0L, 0L, 2L });
/* 17281 */     FOLLOW_method_prototype_in_fully_qualified_method3013 = new BitSet(new long[] { 2L });
/* 17282 */     FOLLOW_reference_type_descriptor_in_fully_qualified_field3033 = new BitSet(new long[] { 512L });
/* 17283 */     FOLLOW_ARROW_in_fully_qualified_field3035 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -3458764513820540928L, 1581792L });
/* 17284 */     FOLLOW_member_name_in_fully_qualified_field3037 = new BitSet(new long[] { 1073741824L });
/* 17285 */     FOLLOW_COLON_in_fully_qualified_field3039 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17286 */     FOLLOW_nonvoid_type_descriptor_in_fully_qualified_field3041 = new BitSet(new long[] { 2L });
/* 17287 */     FOLLOW_COLON_in_label3061 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -4611686018427387904L, 1581792L });
/* 17288 */     FOLLOW_simple_name_in_label3063 = new BitSet(new long[] { 2L });
/* 17289 */     FOLLOW_COLON_in_label_ref3082 = new BitSet(new long[] { 6633802285478576208L, 141651539065L, -4611686018427387904L, 1581792L });
/* 17290 */     FOLLOW_simple_name_in_label_ref3084 = new BitSet(new long[] { 2L });
/* 17291 */     FOLLOW_REGISTER_in_register_list3098 = new BitSet(new long[] { 2147483650L });
/* 17292 */     FOLLOW_COMMA_in_register_list3101 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17293 */     FOLLOW_REGISTER_in_register_list3103 = new BitSet(new long[] { 2147483650L });
/* 17294 */     FOLLOW_REGISTER_in_register_range3138 = new BitSet(new long[] { 8589934594L });
/* 17295 */     FOLLOW_DOTDOT_in_register_range3141 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17296 */     FOLLOW_REGISTER_in_register_range3145 = new BitSet(new long[] { 2L });
/* 17297 */     FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference3174 = new BitSet(new long[] { 2L });
/* 17298 */     FOLLOW_fully_qualified_field_in_verification_error_reference3178 = new BitSet(new long[] { 2L });
/* 17299 */     FOLLOW_fully_qualified_method_in_verification_error_reference3182 = new BitSet(new long[] { 2L });
/* 17300 */     FOLLOW_CATCH_DIRECTIVE_in_catch_directive3192 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17301 */     FOLLOW_nonvoid_type_descriptor_in_catch_directive3194 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17302 */     FOLLOW_OPEN_BRACE_in_catch_directive3196 = new BitSet(new long[] { 1073741824L });
/* 17303 */     FOLLOW_label_ref_in_catch_directive3200 = new BitSet(new long[] { 8589934592L });
/* 17304 */     FOLLOW_DOTDOT_in_catch_directive3202 = new BitSet(new long[] { 1073741824L });
/* 17305 */     FOLLOW_label_ref_in_catch_directive3206 = new BitSet(new long[] { 268435456L });
/* 17306 */     FOLLOW_CLOSE_BRACE_in_catch_directive3208 = new BitSet(new long[] { 1073741824L });
/* 17307 */     FOLLOW_label_ref_in_catch_directive3212 = new BitSet(new long[] { 2L });
/* 17308 */     FOLLOW_CATCHALL_DIRECTIVE_in_catchall_directive3244 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17309 */     FOLLOW_OPEN_BRACE_in_catchall_directive3246 = new BitSet(new long[] { 1073741824L });
/* 17310 */     FOLLOW_label_ref_in_catchall_directive3250 = new BitSet(new long[] { 8589934592L });
/* 17311 */     FOLLOW_DOTDOT_in_catchall_directive3252 = new BitSet(new long[] { 1073741824L });
/* 17312 */     FOLLOW_label_ref_in_catchall_directive3256 = new BitSet(new long[] { 268435456L });
/* 17313 */     FOLLOW_CLOSE_BRACE_in_catchall_directive3258 = new BitSet(new long[] { 1073741824L });
/* 17314 */     FOLLOW_label_ref_in_catchall_directive3262 = new BitSet(new long[] { 2L });
/* 17315 */     FOLLOW_PARAMETER_DIRECTIVE_in_parameter_directive3301 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17316 */     FOLLOW_REGISTER_in_parameter_directive3303 = new BitSet(new long[] { 4400193994786L });
/* 17317 */     FOLLOW_COMMA_in_parameter_directive3306 = new BitSet(new long[] { 0L, 0L, 0L, 65536L });
/* 17318 */     FOLLOW_STRING_LITERAL_in_parameter_directive3308 = new BitSet(new long[] { 4398046511138L });
/* 17319 */     FOLLOW_annotation_in_parameter_directive3319 = new BitSet(new long[] { 4398046511138L });
/* 17320 */     FOLLOW_END_PARAMETER_DIRECTIVE_in_parameter_directive3332 = new BitSet(new long[] { 2L });
/* 17321 */     FOLLOW_line_directive_in_debug_directive3405 = new BitSet(new long[] { 2L });
/* 17322 */     FOLLOW_local_directive_in_debug_directive3411 = new BitSet(new long[] { 2L });
/* 17323 */     FOLLOW_end_local_directive_in_debug_directive3417 = new BitSet(new long[] { 2L });
/* 17324 */     FOLLOW_restart_local_directive_in_debug_directive3423 = new BitSet(new long[] { 2L });
/* 17325 */     FOLLOW_prologue_directive_in_debug_directive3429 = new BitSet(new long[] { 2L });
/* 17326 */     FOLLOW_epilogue_directive_in_debug_directive3435 = new BitSet(new long[] { 2L });
/* 17327 */     FOLLOW_source_directive_in_debug_directive3441 = new BitSet(new long[] { 2L });
/* 17328 */     FOLLOW_LINE_DIRECTIVE_in_line_directive3451 = new BitSet(new long[] { 37748736L, 0L, 5188146770730811392L, 4160L });
/* 17329 */     FOLLOW_integral_literal_in_line_directive3453 = new BitSet(new long[] { 2L });
/* 17330 */     FOLLOW_LOCAL_DIRECTIVE_in_local_directive3476 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17331 */     FOLLOW_REGISTER_in_local_directive3478 = new BitSet(new long[] { 2147483650L });
/* 17332 */     FOLLOW_COMMA_in_local_directive3481 = new BitSet(new long[] { 0L, 0L, -9223372036854775808L, 65536L });
/* 17333 */     FOLLOW_NULL_LITERAL_in_local_directive3484 = new BitSet(new long[] { 1073741824L });
/* 17334 */     FOLLOW_STRING_LITERAL_in_local_directive3490 = new BitSet(new long[] { 1073741824L });
/* 17335 */     FOLLOW_COLON_in_local_directive3493 = new BitSet(new long[] { 67109120L, 0L, 0L, 1048704L });
/* 17336 */     FOLLOW_VOID_TYPE_in_local_directive3496 = new BitSet(new long[] { 2147483650L });
/* 17337 */     FOLLOW_nonvoid_type_descriptor_in_local_directive3500 = new BitSet(new long[] { 2147483650L });
/* 17338 */     FOLLOW_COMMA_in_local_directive3534 = new BitSet(new long[] { 0L, 0L, 0L, 65536L });
/* 17339 */     FOLLOW_STRING_LITERAL_in_local_directive3538 = new BitSet(new long[] { 2L });
/* 17340 */     FOLLOW_END_LOCAL_DIRECTIVE_in_end_local_directive3580 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17341 */     FOLLOW_REGISTER_in_end_local_directive3582 = new BitSet(new long[] { 2L });
/* 17342 */     FOLLOW_RESTART_LOCAL_DIRECTIVE_in_restart_local_directive3605 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17343 */     FOLLOW_REGISTER_in_restart_local_directive3607 = new BitSet(new long[] { 2L });
/* 17344 */     FOLLOW_PROLOGUE_DIRECTIVE_in_prologue_directive3630 = new BitSet(new long[] { 2L });
/* 17345 */     FOLLOW_EPILOGUE_DIRECTIVE_in_epilogue_directive3651 = new BitSet(new long[] { 2L });
/* 17346 */     FOLLOW_SOURCE_DIRECTIVE_in_source_directive3672 = new BitSet(new long[] { 2L, 0L, 0L, 65536L });
/* 17347 */     FOLLOW_STRING_LITERAL_in_source_directive3674 = new BitSet(new long[] { 2L });
/* 17348 */     FOLLOW_INSTRUCTION_FORMAT12x_in_instruction_format12x3699 = new BitSet(new long[] { 2L });
/* 17349 */     FOLLOW_INSTRUCTION_FORMAT12x_OR_ID_in_instruction_format12x3705 = new BitSet(new long[] { 2L });
/* 17350 */     FOLLOW_INSTRUCTION_FORMAT22s_in_instruction_format22s3720 = new BitSet(new long[] { 2L });
/* 17351 */     FOLLOW_INSTRUCTION_FORMAT22s_OR_ID_in_instruction_format22s3726 = new BitSet(new long[] { 2L });
/* 17352 */     FOLLOW_INSTRUCTION_FORMAT31i_in_instruction_format31i3741 = new BitSet(new long[] { 2L });
/* 17353 */     FOLLOW_INSTRUCTION_FORMAT31i_OR_ID_in_instruction_format31i3747 = new BitSet(new long[] { 2L });
/* 17354 */     FOLLOW_insn_format10t_in_instruction3764 = new BitSet(new long[] { 2L });
/* 17355 */     FOLLOW_insn_format10x_in_instruction3770 = new BitSet(new long[] { 2L });
/* 17356 */     FOLLOW_insn_format10x_odex_in_instruction3776 = new BitSet(new long[] { 2L });
/* 17357 */     FOLLOW_insn_format11n_in_instruction3782 = new BitSet(new long[] { 2L });
/* 17358 */     FOLLOW_insn_format11x_in_instruction3788 = new BitSet(new long[] { 2L });
/* 17359 */     FOLLOW_insn_format12x_in_instruction3794 = new BitSet(new long[] { 2L });
/* 17360 */     FOLLOW_insn_format20bc_in_instruction3800 = new BitSet(new long[] { 2L });
/* 17361 */     FOLLOW_insn_format20t_in_instruction3806 = new BitSet(new long[] { 2L });
/* 17362 */     FOLLOW_insn_format21c_field_in_instruction3812 = new BitSet(new long[] { 2L });
/* 17363 */     FOLLOW_insn_format21c_field_odex_in_instruction3818 = new BitSet(new long[] { 2L });
/* 17364 */     FOLLOW_insn_format21c_string_in_instruction3824 = new BitSet(new long[] { 2L });
/* 17365 */     FOLLOW_insn_format21c_type_in_instruction3830 = new BitSet(new long[] { 2L });
/* 17366 */     FOLLOW_insn_format21ih_in_instruction3836 = new BitSet(new long[] { 2L });
/* 17367 */     FOLLOW_insn_format21lh_in_instruction3842 = new BitSet(new long[] { 2L });
/* 17368 */     FOLLOW_insn_format21s_in_instruction3848 = new BitSet(new long[] { 2L });
/* 17369 */     FOLLOW_insn_format21t_in_instruction3854 = new BitSet(new long[] { 2L });
/* 17370 */     FOLLOW_insn_format22b_in_instruction3860 = new BitSet(new long[] { 2L });
/* 17371 */     FOLLOW_insn_format22c_field_in_instruction3866 = new BitSet(new long[] { 2L });
/* 17372 */     FOLLOW_insn_format22c_field_odex_in_instruction3872 = new BitSet(new long[] { 2L });
/* 17373 */     FOLLOW_insn_format22c_type_in_instruction3878 = new BitSet(new long[] { 2L });
/* 17374 */     FOLLOW_insn_format22cs_field_in_instruction3884 = new BitSet(new long[] { 2L });
/* 17375 */     FOLLOW_insn_format22s_in_instruction3890 = new BitSet(new long[] { 2L });
/* 17376 */     FOLLOW_insn_format22t_in_instruction3896 = new BitSet(new long[] { 2L });
/* 17377 */     FOLLOW_insn_format22x_in_instruction3902 = new BitSet(new long[] { 2L });
/* 17378 */     FOLLOW_insn_format23x_in_instruction3908 = new BitSet(new long[] { 2L });
/* 17379 */     FOLLOW_insn_format30t_in_instruction3914 = new BitSet(new long[] { 2L });
/* 17380 */     FOLLOW_insn_format31c_in_instruction3920 = new BitSet(new long[] { 2L });
/* 17381 */     FOLLOW_insn_format31i_in_instruction3926 = new BitSet(new long[] { 2L });
/* 17382 */     FOLLOW_insn_format31t_in_instruction3932 = new BitSet(new long[] { 2L });
/* 17383 */     FOLLOW_insn_format32x_in_instruction3938 = new BitSet(new long[] { 2L });
/* 17384 */     FOLLOW_insn_format35c_method_in_instruction3944 = new BitSet(new long[] { 2L });
/* 17385 */     FOLLOW_insn_format35c_type_in_instruction3950 = new BitSet(new long[] { 2L });
/* 17386 */     FOLLOW_insn_format35c_method_odex_in_instruction3956 = new BitSet(new long[] { 2L });
/* 17387 */     FOLLOW_insn_format35mi_method_in_instruction3962 = new BitSet(new long[] { 2L });
/* 17388 */     FOLLOW_insn_format35ms_method_in_instruction3968 = new BitSet(new long[] { 2L });
/* 17389 */     FOLLOW_insn_format3rc_method_in_instruction3974 = new BitSet(new long[] { 2L });
/* 17390 */     FOLLOW_insn_format3rc_method_odex_in_instruction3980 = new BitSet(new long[] { 2L });
/* 17391 */     FOLLOW_insn_format3rc_type_in_instruction3986 = new BitSet(new long[] { 2L });
/* 17392 */     FOLLOW_insn_format3rmi_method_in_instruction3992 = new BitSet(new long[] { 2L });
/* 17393 */     FOLLOW_insn_format3rms_method_in_instruction3998 = new BitSet(new long[] { 2L });
/* 17394 */     FOLLOW_insn_format51l_in_instruction4004 = new BitSet(new long[] { 2L });
/* 17395 */     FOLLOW_insn_array_data_directive_in_instruction4010 = new BitSet(new long[] { 2L });
/* 17396 */     FOLLOW_insn_packed_switch_directive_in_instruction4016 = new BitSet(new long[] { 2L });
/* 17397 */     FOLLOW_insn_sparse_switch_directive_in_instruction4022 = new BitSet(new long[] { 2L });
/* 17398 */     FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t4042 = new BitSet(new long[] { 1073741824L });
/* 17399 */     FOLLOW_label_ref_in_insn_format10t4044 = new BitSet(new long[] { 2L });
/* 17400 */     FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x4074 = new BitSet(new long[] { 2L });
/* 17401 */     FOLLOW_INSTRUCTION_FORMAT10x_ODEX_in_insn_format10x_odex4102 = new BitSet(new long[] { 2L });
/* 17402 */     FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n4123 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17403 */     FOLLOW_REGISTER_in_insn_format11n4125 = new BitSet(new long[] { 2147483648L });
/* 17404 */     FOLLOW_COMMA_in_insn_format11n4127 = new BitSet(new long[] { 37748736L, 0L, 5188146770730811392L, 4160L });
/* 17405 */     FOLLOW_integral_literal_in_insn_format11n4129 = new BitSet(new long[] { 2L });
/* 17406 */     FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x4161 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17407 */     FOLLOW_REGISTER_in_insn_format11x4163 = new BitSet(new long[] { 2L });
/* 17408 */     FOLLOW_instruction_format12x_in_insn_format12x4193 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17409 */     FOLLOW_REGISTER_in_insn_format12x4195 = new BitSet(new long[] { 2147483648L });
/* 17410 */     FOLLOW_COMMA_in_insn_format12x4197 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17411 */     FOLLOW_REGISTER_in_insn_format12x4199 = new BitSet(new long[] { 2L });
/* 17412 */     FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc4231 = new BitSet(new long[] { 0L, 0L, 0L, 524288L });
/* 17413 */     FOLLOW_VERIFICATION_ERROR_TYPE_in_insn_format20bc4233 = new BitSet(new long[] { 2147483648L });
/* 17414 */     FOLLOW_COMMA_in_insn_format20bc4235 = new BitSet(new long[] { 67109120L });
/* 17415 */     FOLLOW_verification_error_reference_in_insn_format20bc4237 = new BitSet(new long[] { 2L });
/* 17416 */     FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t4274 = new BitSet(new long[] { 1073741824L });
/* 17417 */     FOLLOW_label_ref_in_insn_format20t4276 = new BitSet(new long[] { 2L });
/* 17418 */     FOLLOW_INSTRUCTION_FORMAT21c_FIELD_in_insn_format21c_field4306 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17419 */     FOLLOW_REGISTER_in_insn_format21c_field4308 = new BitSet(new long[] { 2147483648L });
/* 17420 */     FOLLOW_COMMA_in_insn_format21c_field4310 = new BitSet(new long[] { 67109120L });
/* 17421 */     FOLLOW_fully_qualified_field_in_insn_format21c_field4312 = new BitSet(new long[] { 2L });
/* 17422 */     FOLLOW_INSTRUCTION_FORMAT21c_FIELD_ODEX_in_insn_format21c_field_odex4344 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17423 */     FOLLOW_REGISTER_in_insn_format21c_field_odex4346 = new BitSet(new long[] { 2147483648L });
/* 17424 */     FOLLOW_COMMA_in_insn_format21c_field_odex4348 = new BitSet(new long[] { 67109120L });
/* 17425 */     FOLLOW_fully_qualified_field_in_insn_format21c_field_odex4350 = new BitSet(new long[] { 2L });
/* 17426 */     FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string4388 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17427 */     FOLLOW_REGISTER_in_insn_format21c_string4390 = new BitSet(new long[] { 2147483648L });
/* 17428 */     FOLLOW_COMMA_in_insn_format21c_string4392 = new BitSet(new long[] { 0L, 0L, 0L, 65536L });
/* 17429 */     FOLLOW_STRING_LITERAL_in_insn_format21c_string4394 = new BitSet(new long[] { 2L });
/* 17430 */     FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type4426 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17431 */     FOLLOW_REGISTER_in_insn_format21c_type4428 = new BitSet(new long[] { 2147483648L });
/* 17432 */     FOLLOW_COMMA_in_insn_format21c_type4430 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17433 */     FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type4432 = new BitSet(new long[] { 2L });
/* 17434 */     FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih4464 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17435 */     FOLLOW_REGISTER_in_insn_format21ih4466 = new BitSet(new long[] { 2147483648L });
/* 17436 */     FOLLOW_COMMA_in_insn_format21ih4468 = new BitSet(new long[] { 6755399480901632L, 0L, 5188146770730811392L, 4160L });
/* 17437 */     FOLLOW_fixed_32bit_literal_in_insn_format21ih4470 = new BitSet(new long[] { 2L });
/* 17438 */     FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh4502 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17439 */     FOLLOW_REGISTER_in_insn_format21lh4504 = new BitSet(new long[] { 2147483648L });
/* 17440 */     FOLLOW_COMMA_in_insn_format21lh4506 = new BitSet(new long[] { 6755399480901632L, 0L, 5188146770730811392L, 4160L });
/* 17441 */     FOLLOW_fixed_32bit_literal_in_insn_format21lh4508 = new BitSet(new long[] { 2L });
/* 17442 */     FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s4540 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17443 */     FOLLOW_REGISTER_in_insn_format21s4542 = new BitSet(new long[] { 2147483648L });
/* 17444 */     FOLLOW_COMMA_in_insn_format21s4544 = new BitSet(new long[] { 37748736L, 0L, 5188146770730811392L, 4160L });
/* 17445 */     FOLLOW_integral_literal_in_insn_format21s4546 = new BitSet(new long[] { 2L });
/* 17446 */     FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t4578 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17447 */     FOLLOW_REGISTER_in_insn_format21t4580 = new BitSet(new long[] { 2147483648L });
/* 17448 */     FOLLOW_COMMA_in_insn_format21t4582 = new BitSet(new long[] { 1073741824L });
/* 17449 */     FOLLOW_label_ref_in_insn_format21t4584 = new BitSet(new long[] { 2L });
/* 17450 */     FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b4616 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17451 */     FOLLOW_REGISTER_in_insn_format22b4618 = new BitSet(new long[] { 2147483648L });
/* 17452 */     FOLLOW_COMMA_in_insn_format22b4620 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17453 */     FOLLOW_REGISTER_in_insn_format22b4622 = new BitSet(new long[] { 2147483648L });
/* 17454 */     FOLLOW_COMMA_in_insn_format22b4624 = new BitSet(new long[] { 37748736L, 0L, 5188146770730811392L, 4160L });
/* 17455 */     FOLLOW_integral_literal_in_insn_format22b4626 = new BitSet(new long[] { 2L });
/* 17456 */     FOLLOW_INSTRUCTION_FORMAT22c_FIELD_in_insn_format22c_field4660 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17457 */     FOLLOW_REGISTER_in_insn_format22c_field4662 = new BitSet(new long[] { 2147483648L });
/* 17458 */     FOLLOW_COMMA_in_insn_format22c_field4664 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17459 */     FOLLOW_REGISTER_in_insn_format22c_field4666 = new BitSet(new long[] { 2147483648L });
/* 17460 */     FOLLOW_COMMA_in_insn_format22c_field4668 = new BitSet(new long[] { 67109120L });
/* 17461 */     FOLLOW_fully_qualified_field_in_insn_format22c_field4670 = new BitSet(new long[] { 2L });
/* 17462 */     FOLLOW_INSTRUCTION_FORMAT22c_FIELD_ODEX_in_insn_format22c_field_odex4704 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17463 */     FOLLOW_REGISTER_in_insn_format22c_field_odex4706 = new BitSet(new long[] { 2147483648L });
/* 17464 */     FOLLOW_COMMA_in_insn_format22c_field_odex4708 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17465 */     FOLLOW_REGISTER_in_insn_format22c_field_odex4710 = new BitSet(new long[] { 2147483648L });
/* 17466 */     FOLLOW_COMMA_in_insn_format22c_field_odex4712 = new BitSet(new long[] { 67109120L });
/* 17467 */     FOLLOW_fully_qualified_field_in_insn_format22c_field_odex4714 = new BitSet(new long[] { 2L });
/* 17468 */     FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type4754 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17469 */     FOLLOW_REGISTER_in_insn_format22c_type4756 = new BitSet(new long[] { 2147483648L });
/* 17470 */     FOLLOW_COMMA_in_insn_format22c_type4758 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17471 */     FOLLOW_REGISTER_in_insn_format22c_type4760 = new BitSet(new long[] { 2147483648L });
/* 17472 */     FOLLOW_COMMA_in_insn_format22c_type4762 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17473 */     FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type4764 = new BitSet(new long[] { 2L });
/* 17474 */     FOLLOW_INSTRUCTION_FORMAT22cs_FIELD_in_insn_format22cs_field4798 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17475 */     FOLLOW_REGISTER_in_insn_format22cs_field4800 = new BitSet(new long[] { 2147483648L });
/* 17476 */     FOLLOW_COMMA_in_insn_format22cs_field4802 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17477 */     FOLLOW_REGISTER_in_insn_format22cs_field4804 = new BitSet(new long[] { 2147483648L });
/* 17478 */     FOLLOW_COMMA_in_insn_format22cs_field4806 = new BitSet(new long[] { 1125899906842624L });
/* 17479 */     FOLLOW_FIELD_OFFSET_in_insn_format22cs_field4808 = new BitSet(new long[] { 2L });
/* 17480 */     FOLLOW_instruction_format22s_in_insn_format22s4829 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17481 */     FOLLOW_REGISTER_in_insn_format22s4831 = new BitSet(new long[] { 2147483648L });
/* 17482 */     FOLLOW_COMMA_in_insn_format22s4833 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17483 */     FOLLOW_REGISTER_in_insn_format22s4835 = new BitSet(new long[] { 2147483648L });
/* 17484 */     FOLLOW_COMMA_in_insn_format22s4837 = new BitSet(new long[] { 37748736L, 0L, 5188146770730811392L, 4160L });
/* 17485 */     FOLLOW_integral_literal_in_insn_format22s4839 = new BitSet(new long[] { 2L });
/* 17486 */     FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t4873 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17487 */     FOLLOW_REGISTER_in_insn_format22t4875 = new BitSet(new long[] { 2147483648L });
/* 17488 */     FOLLOW_COMMA_in_insn_format22t4877 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17489 */     FOLLOW_REGISTER_in_insn_format22t4879 = new BitSet(new long[] { 2147483648L });
/* 17490 */     FOLLOW_COMMA_in_insn_format22t4881 = new BitSet(new long[] { 1073741824L });
/* 17491 */     FOLLOW_label_ref_in_insn_format22t4883 = new BitSet(new long[] { 2L });
/* 17492 */     FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x4917 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17493 */     FOLLOW_REGISTER_in_insn_format22x4919 = new BitSet(new long[] { 2147483648L });
/* 17494 */     FOLLOW_COMMA_in_insn_format22x4921 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17495 */     FOLLOW_REGISTER_in_insn_format22x4923 = new BitSet(new long[] { 2L });
/* 17496 */     FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x4955 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17497 */     FOLLOW_REGISTER_in_insn_format23x4957 = new BitSet(new long[] { 2147483648L });
/* 17498 */     FOLLOW_COMMA_in_insn_format23x4959 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17499 */     FOLLOW_REGISTER_in_insn_format23x4961 = new BitSet(new long[] { 2147483648L });
/* 17500 */     FOLLOW_COMMA_in_insn_format23x4963 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17501 */     FOLLOW_REGISTER_in_insn_format23x4965 = new BitSet(new long[] { 2L });
/* 17502 */     FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t4999 = new BitSet(new long[] { 1073741824L });
/* 17503 */     FOLLOW_label_ref_in_insn_format30t5001 = new BitSet(new long[] { 2L });
/* 17504 */     FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c5031 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17505 */     FOLLOW_REGISTER_in_insn_format31c5033 = new BitSet(new long[] { 2147483648L });
/* 17506 */     FOLLOW_COMMA_in_insn_format31c5035 = new BitSet(new long[] { 0L, 0L, 0L, 65536L });
/* 17507 */     FOLLOW_STRING_LITERAL_in_insn_format31c5037 = new BitSet(new long[] { 2L });
/* 17508 */     FOLLOW_instruction_format31i_in_insn_format31i5068 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17509 */     FOLLOW_REGISTER_in_insn_format31i5070 = new BitSet(new long[] { 2147483648L });
/* 17510 */     FOLLOW_COMMA_in_insn_format31i5072 = new BitSet(new long[] { 6755399480901632L, 0L, 5188146770730811392L, 4160L });
/* 17511 */     FOLLOW_fixed_32bit_literal_in_insn_format31i5074 = new BitSet(new long[] { 2L });
/* 17512 */     FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t5106 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17513 */     FOLLOW_REGISTER_in_insn_format31t5108 = new BitSet(new long[] { 2147483648L });
/* 17514 */     FOLLOW_COMMA_in_insn_format31t5110 = new BitSet(new long[] { 1073741824L });
/* 17515 */     FOLLOW_label_ref_in_insn_format31t5112 = new BitSet(new long[] { 2L });
/* 17516 */     FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x5144 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17517 */     FOLLOW_REGISTER_in_insn_format32x5146 = new BitSet(new long[] { 2147483648L });
/* 17518 */     FOLLOW_COMMA_in_insn_format32x5148 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17519 */     FOLLOW_REGISTER_in_insn_format32x5150 = new BitSet(new long[] { 2L });
/* 17520 */     FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method5182 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17521 */     FOLLOW_OPEN_BRACE_in_insn_format35c_method5184 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17522 */     FOLLOW_register_list_in_insn_format35c_method5186 = new BitSet(new long[] { 268435456L });
/* 17523 */     FOLLOW_CLOSE_BRACE_in_insn_format35c_method5188 = new BitSet(new long[] { 2147483648L });
/* 17524 */     FOLLOW_COMMA_in_insn_format35c_method5190 = new BitSet(new long[] { 67109120L });
/* 17525 */     FOLLOW_fully_qualified_method_in_insn_format35c_method5192 = new BitSet(new long[] { 2L });
/* 17526 */     FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type5224 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17527 */     FOLLOW_OPEN_BRACE_in_insn_format35c_type5226 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17528 */     FOLLOW_register_list_in_insn_format35c_type5228 = new BitSet(new long[] { 268435456L });
/* 17529 */     FOLLOW_CLOSE_BRACE_in_insn_format35c_type5230 = new BitSet(new long[] { 2147483648L });
/* 17530 */     FOLLOW_COMMA_in_insn_format35c_type5232 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17531 */     FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type5234 = new BitSet(new long[] { 2L });
/* 17532 */     FOLLOW_INSTRUCTION_FORMAT35c_METHOD_ODEX_in_insn_format35c_method_odex5266 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17533 */     FOLLOW_OPEN_BRACE_in_insn_format35c_method_odex5268 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17534 */     FOLLOW_register_list_in_insn_format35c_method_odex5270 = new BitSet(new long[] { 268435456L });
/* 17535 */     FOLLOW_CLOSE_BRACE_in_insn_format35c_method_odex5272 = new BitSet(new long[] { 2147483648L });
/* 17536 */     FOLLOW_COMMA_in_insn_format35c_method_odex5274 = new BitSet(new long[] { 67109120L });
/* 17537 */     FOLLOW_fully_qualified_method_in_insn_format35c_method_odex5276 = new BitSet(new long[] { 2L });
/* 17538 */     FOLLOW_INSTRUCTION_FORMAT35mi_METHOD_in_insn_format35mi_method5297 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17539 */     FOLLOW_OPEN_BRACE_in_insn_format35mi_method5299 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17540 */     FOLLOW_register_list_in_insn_format35mi_method5301 = new BitSet(new long[] { 268435456L });
/* 17541 */     FOLLOW_CLOSE_BRACE_in_insn_format35mi_method5303 = new BitSet(new long[] { 2147483648L });
/* 17542 */     FOLLOW_COMMA_in_insn_format35mi_method5305 = new BitSet(new long[] { 144115188075855872L });
/* 17543 */     FOLLOW_INLINE_INDEX_in_insn_format35mi_method5307 = new BitSet(new long[] { 2L });
/* 17544 */     FOLLOW_INSTRUCTION_FORMAT35ms_METHOD_in_insn_format35ms_method5328 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17545 */     FOLLOW_OPEN_BRACE_in_insn_format35ms_method5330 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17546 */     FOLLOW_register_list_in_insn_format35ms_method5332 = new BitSet(new long[] { 268435456L });
/* 17547 */     FOLLOW_CLOSE_BRACE_in_insn_format35ms_method5334 = new BitSet(new long[] { 2147483648L });
/* 17548 */     FOLLOW_COMMA_in_insn_format35ms_method5336 = new BitSet(new long[] { 0L, 0L, 0L, 2097152L });
/* 17549 */     FOLLOW_VTABLE_INDEX_in_insn_format35ms_method5338 = new BitSet(new long[] { 2L });
/* 17550 */     FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method5359 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17551 */     FOLLOW_OPEN_BRACE_in_insn_format3rc_method5361 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17552 */     FOLLOW_register_range_in_insn_format3rc_method5363 = new BitSet(new long[] { 268435456L });
/* 17553 */     FOLLOW_CLOSE_BRACE_in_insn_format3rc_method5365 = new BitSet(new long[] { 2147483648L });
/* 17554 */     FOLLOW_COMMA_in_insn_format3rc_method5367 = new BitSet(new long[] { 67109120L });
/* 17555 */     FOLLOW_fully_qualified_method_in_insn_format3rc_method5369 = new BitSet(new long[] { 2L });
/* 17556 */     FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_ODEX_in_insn_format3rc_method_odex5401 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17557 */     FOLLOW_OPEN_BRACE_in_insn_format3rc_method_odex5403 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17558 */     FOLLOW_register_list_in_insn_format3rc_method_odex5405 = new BitSet(new long[] { 268435456L });
/* 17559 */     FOLLOW_CLOSE_BRACE_in_insn_format3rc_method_odex5407 = new BitSet(new long[] { 2147483648L });
/* 17560 */     FOLLOW_COMMA_in_insn_format3rc_method_odex5409 = new BitSet(new long[] { 67109120L });
/* 17561 */     FOLLOW_fully_qualified_method_in_insn_format3rc_method_odex5411 = new BitSet(new long[] { 2L });
/* 17562 */     FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type5432 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17563 */     FOLLOW_OPEN_BRACE_in_insn_format3rc_type5434 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17564 */     FOLLOW_register_range_in_insn_format3rc_type5436 = new BitSet(new long[] { 268435456L });
/* 17565 */     FOLLOW_CLOSE_BRACE_in_insn_format3rc_type5438 = new BitSet(new long[] { 2147483648L });
/* 17566 */     FOLLOW_COMMA_in_insn_format3rc_type5440 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 17567 */     FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type5442 = new BitSet(new long[] { 2L });
/* 17568 */     FOLLOW_INSTRUCTION_FORMAT3rmi_METHOD_in_insn_format3rmi_method5474 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17569 */     FOLLOW_OPEN_BRACE_in_insn_format3rmi_method5476 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17570 */     FOLLOW_register_range_in_insn_format3rmi_method5478 = new BitSet(new long[] { 268435456L });
/* 17571 */     FOLLOW_CLOSE_BRACE_in_insn_format3rmi_method5480 = new BitSet(new long[] { 2147483648L });
/* 17572 */     FOLLOW_COMMA_in_insn_format3rmi_method5482 = new BitSet(new long[] { 144115188075855872L });
/* 17573 */     FOLLOW_INLINE_INDEX_in_insn_format3rmi_method5484 = new BitSet(new long[] { 2L });
/* 17574 */     FOLLOW_INSTRUCTION_FORMAT3rms_METHOD_in_insn_format3rms_method5505 = new BitSet(new long[] { 0L, 0L, 0L, 1L });
/* 17575 */     FOLLOW_OPEN_BRACE_in_insn_format3rms_method5507 = new BitSet(new long[] { 268435456L, 0L, 0L, 512L });
/* 17576 */     FOLLOW_register_range_in_insn_format3rms_method5509 = new BitSet(new long[] { 268435456L });
/* 17577 */     FOLLOW_CLOSE_BRACE_in_insn_format3rms_method5511 = new BitSet(new long[] { 2147483648L });
/* 17578 */     FOLLOW_COMMA_in_insn_format3rms_method5513 = new BitSet(new long[] { 0L, 0L, 0L, 2097152L });
/* 17579 */     FOLLOW_VTABLE_INDEX_in_insn_format3rms_method5515 = new BitSet(new long[] { 2L });
/* 17580 */     FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l5536 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 17581 */     FOLLOW_REGISTER_in_insn_format51l5538 = new BitSet(new long[] { 2147483648L });
/* 17582 */     FOLLOW_COMMA_in_insn_format51l5540 = new BitSet(new long[] { 6755451020509184L, 0L, 5188146770730811392L, 4160L });
/* 17583 */     FOLLOW_fixed_literal_in_insn_format51l5542 = new BitSet(new long[] { 2L });
/* 17584 */     FOLLOW_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive5569 = new BitSet(new long[] { 0L, 0L, 4611686018427387904L, 64L });
/* 17585 */     FOLLOW_parsed_integer_literal_in_insn_array_data_directive5575 = new BitSet(new long[] { 6755588459462656L, 0L, 5188146770730811392L, 4160L });
/* 17586 */     FOLLOW_fixed_literal_in_insn_array_data_directive5587 = new BitSet(new long[] { 6755588459462656L, 0L, 5188146770730811392L, 4160L });
/* 17587 */     FOLLOW_END_ARRAY_DATA_DIRECTIVE_in_insn_array_data_directive5590 = new BitSet(new long[] { 2L });
/* 17588 */     FOLLOW_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive5636 = new BitSet(new long[] { 6755399480901632L, 0L, 5188146770730811392L, 4160L });
/* 17589 */     FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive5642 = new BitSet(new long[] { 2200096997376L });
/* 17590 */     FOLLOW_label_ref_in_insn_packed_switch_directive5648 = new BitSet(new long[] { 2200096997376L });
/* 17591 */     FOLLOW_END_PACKED_SWITCH_DIRECTIVE_in_insn_packed_switch_directive5655 = new BitSet(new long[] { 2L });
/* 17592 */     FOLLOW_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive5729 = new BitSet(new long[] { 6764195573923840L, 0L, 5188146770730811392L, 4160L });
/* 17593 */     FOLLOW_fixed_32bit_literal_in_insn_sparse_switch_directive5736 = new BitSet(new long[] { 512L });
/* 17594 */     FOLLOW_ARROW_in_insn_sparse_switch_directive5738 = new BitSet(new long[] { 1073741824L });
/* 17595 */     FOLLOW_label_ref_in_insn_sparse_switch_directive5740 = new BitSet(new long[] { 6764195573923840L, 0L, 5188146770730811392L, 4160L });
/* 17596 */     FOLLOW_END_SPARSE_SWITCH_DIRECTIVE_in_insn_sparse_switch_directive5748 = new BitSet(new long[] { 2L });
/*       */   }
/*       */ 
/*       */   protected class DFA33 extends DFA
/*       */   {
/*       */     public DFA33(BaseRecognizer recognizer)
/*       */     {
/* 17072 */       this.recognizer = recognizer;
/* 17073 */       this.decisionNumber = 33;
/* 17074 */       this.eot = smaliParser.DFA33_eot;
/* 17075 */       this.eof = smaliParser.DFA33_eof;
/* 17076 */       this.min = smaliParser.DFA33_min;
/* 17077 */       this.max = smaliParser.DFA33_max;
/* 17078 */       this.accept = smaliParser.DFA33_accept;
/* 17079 */       this.special = smaliParser.DFA33_special;
/* 17080 */       this.transition = smaliParser.DFA33_transition;
/*       */     }
/*       */ 
/*       */     public String getDescription() {
/* 17084 */       return "()* loopback of 749:5: ({...}? annotation )*";
/*       */     }
/*       */ 
/*       */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 17088 */       TokenStream input = (TokenStream)_input;
/* 17089 */       int _s = s;
/* 17090 */       switch (s) {
/*       */       case 0:
/* 17092 */         int LA33_63 = input.LA(1);
/*       */ 
/* 17094 */         int index33_63 = input.index();
/* 17095 */         input.rewind();
/* 17096 */         s = -1;
/* 17097 */         if (input.LA(1) == 5) s = 64; else {
/* 17098 */           s = 1;
/*       */         }
/* 17100 */         input.seek(index33_63);
/* 17101 */         if (s < 0) break; return s;
/*       */       }
/*       */ 
/* 17104 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 33, _s, input);
/*       */ 
/* 17106 */       error(nvae);
/* 17107 */       throw nvae;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_sparse_switch_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16836 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_packed_switch_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16701 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_array_data_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16560 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format51l_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16465 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format3rms_method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16380 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format3rmi_method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16295 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format3rc_type_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16187 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format3rc_method_odex_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 16101 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format3rc_method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15993 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format35ms_method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15908 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format35mi_method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15823 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format35c_method_odex_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15737 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format35c_type_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15629 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format35c_method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15521 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format32x_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15428 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format31t_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15333 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format31i_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15237 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format31c_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15143 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format30t_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 15061 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format23x_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14957 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22x_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14864 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22t_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14758 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22s_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14651 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22cs_field_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14567 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22c_type_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14461 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22c_field_odex_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14350 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22c_field_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14244 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format22b_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14138 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21t_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 14043 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21s_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13948 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21lh_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13853 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21ih_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13758 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21c_type_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13663 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21c_string_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13569 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21c_field_odex_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13469 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format21c_field_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13374 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format20t_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13292 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format20bc_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13192 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format12x_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13098 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format11x_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 13017 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format11n_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 12922 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format10x_odex_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 12868 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format10x_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 12794 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class insn_format10t_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 12712 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class instruction_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11781 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class instruction_format31i_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11681 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class instruction_format22s_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11581 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class instruction_format12x_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11481 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class source_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11381 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class epilogue_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11308 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class prologue_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11235 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class restart_local_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11155 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class end_local_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 11075 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class local_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 10832 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class line_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 10751 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class debug_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 10563 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class parameter_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 10332 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class catchall_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 10216 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class catch_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/* 10092 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class verification_error_reference_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  7851 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class register_range_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  7723 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class register_list_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  7563 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class label_ref_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  7489 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class label_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  7408 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class fully_qualified_field_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  7312 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class fully_qualified_method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  7222 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class type_field_method_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  5992 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class enum_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  5884 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class subannotation_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  5764 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class annotation_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  5630 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class annotation_element_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  5541 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class array_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  5386 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class fixed_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  5180 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class fixed_32bit_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4995 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class integral_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4850 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class parsed_integer_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     public int value;
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4797 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4469 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class double_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4369 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class float_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4269 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class integer_literal_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4153 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class reference_type_descriptor_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4096 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class nonvoid_type_descriptor_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  4039 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class type_descriptor_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  3982 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class param_list_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  3820 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class method_prototype_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  3713 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class member_name_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  3612 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class simple_name_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  2215 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class registers_directive_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  2060 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class ordered_method_item_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  1900 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class statements_and_directives_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  1611 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   protected static class statements_and_directives_scope
/*       */   {
/*       */     boolean hasRegistersDirective;
/*       */     List<CommonTree> methodAnnotations;
/*       */   }
/*       */ 
/*       */   public static class method_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  1494 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class field_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  1196 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class access_list_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  1092 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class source_spec_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*  1012 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class implements_spec_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*   932 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class super_spec_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*   852 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class class_spec_return extends ParserRuleReturnScope
/*       */   {
/*       */     public String className;
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*   770 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   public static class smali_file_return extends ParserRuleReturnScope
/*       */   {
/*       */     CommonTree tree;
/*       */ 
/*       */     public CommonTree getTree()
/*       */     {
/*   499 */       return this.tree;
/*       */     }
/*       */   }
/*       */ 
/*       */   protected static class smali_file_scope
/*       */   {
/*       */     boolean hasClassSpec;
/*       */     boolean hasSuperSpec;
/*       */     boolean hasSourceSpec;
/*       */     List<CommonTree> classAnnotations;
/*       */   }
/*       */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.smaliParser
 * JD-Core Version:    0.6.0
 */