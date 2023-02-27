/*      */ package org.jf.smali;
/*      */ 
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Stack;
/*      */ import org.antlr.runtime.BitSet;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.runtime.tree.CommonTree;
/*      */ import org.antlr.runtime.tree.TreeNodeStream;
/*      */ import org.antlr.runtime.tree.TreeParser;
/*      */ import org.antlr.runtime.tree.TreeRuleReturnScope;
/*      */ import org.jf.dexlib2.AccessFlags;
/*      */ import org.jf.dexlib2.AnnotationVisibility;
/*      */ import org.jf.dexlib2.Opcode;
/*      */ import org.jf.dexlib2.Opcodes;
/*      */ import org.jf.dexlib2.VerificationError;
/*      */ import org.jf.dexlib2.builder.Label;
/*      */ import org.jf.dexlib2.builder.MethodImplementationBuilder;
/*      */ import org.jf.dexlib2.builder.SwitchLabelElement;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderArrayPayload;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction10t;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction10x;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction11n;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction11x;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction12x;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction20bc;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction20t;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21c;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21ih;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21lh;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21s;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21t;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22b;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22c;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22s;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22t;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22x;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction23x;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction30t;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction31c;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction31i;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction31t;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction32x;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction35c;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction51l;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderPackedSwitchPayload;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload;
/*      */ import org.jf.dexlib2.iface.Annotation;
/*      */ import org.jf.dexlib2.iface.AnnotationElement;
/*      */ import org.jf.dexlib2.iface.ClassDef;
/*      */ import org.jf.dexlib2.iface.MethodImplementation;
/*      */ import org.jf.dexlib2.iface.reference.FieldReference;
/*      */ import org.jf.dexlib2.iface.reference.MethodReference;
/*      */ import org.jf.dexlib2.iface.value.EncodedValue;
/*      */ import org.jf.dexlib2.immutable.ImmutableAnnotation;
/*      */ import org.jf.dexlib2.immutable.ImmutableAnnotationElement;
/*      */ import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
/*      */ import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
/*      */ import org.jf.dexlib2.immutable.reference.ImmutableReference;
/*      */ import org.jf.dexlib2.immutable.reference.ImmutableTypeReference;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableAnnotationEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableArrayEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableBooleanEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableEnumEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableFieldEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableMethodEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableNullEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableStringEncodedValue;
/*      */ import org.jf.dexlib2.immutable.value.ImmutableTypeEncodedValue;
/*      */ import org.jf.dexlib2.util.MethodUtil;
/*      */ import org.jf.dexlib2.writer.builder.BuilderField;
/*      */ import org.jf.dexlib2.writer.builder.BuilderMethod;
/*      */ import org.jf.dexlib2.writer.builder.BuilderTryBlock;
/*      */ import org.jf.dexlib2.writer.builder.DexBuilder;
/*      */ import org.jf.util.LinearSearch;
/*      */ 
/*      */ public class smaliTreeWalker extends TreeParser
/*      */ {
/*   50 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACCESS_SPEC", "ANNOTATION_DIRECTIVE", "ANNOTATION_VISIBILITY", "ARRAY_DATA_DIRECTIVE", "ARRAY_DESCRIPTOR", "ARROW", "BASE_ARRAY_DESCRIPTOR", "BASE_CHAR_LITERAL", "BASE_CLASS_DESCRIPTOR", "BASE_FLOAT", "BASE_FLOAT_OR_ID", "BASE_INTEGER", "BASE_PRIMITIVE_TYPE", "BASE_SIMPLE_NAME", "BASE_STRING_LITERAL", "BASE_TYPE", "BINARY_EXPONENT", "BOOL_LITERAL", "BYTE_LITERAL", "CATCHALL_DIRECTIVE", "CATCH_DIRECTIVE", "CHAR_LITERAL", "CLASS_DESCRIPTOR", "CLASS_DIRECTIVE", "CLOSE_BRACE", "CLOSE_PAREN", "COLON", "COMMA", "DECIMAL_EXPONENT", "DOTDOT", "DOUBLE_LITERAL", "DOUBLE_LITERAL_OR_ID", "END_ANNOTATION_DIRECTIVE", "END_ARRAY_DATA_DIRECTIVE", "END_FIELD_DIRECTIVE", "END_LOCAL_DIRECTIVE", "END_METHOD_DIRECTIVE", "END_PACKED_SWITCH_DIRECTIVE", "END_PARAMETER_DIRECTIVE", "END_SPARSE_SWITCH_DIRECTIVE", "END_SUBANNOTATION_DIRECTIVE", "ENUM_DIRECTIVE", "EPILOGUE_DIRECTIVE", "EQUAL", "ESCAPE_SEQUENCE", "FIELD_DIRECTIVE", "FIELD_OFFSET", "FLOAT_LITERAL", "FLOAT_LITERAL_OR_ID", "HEX_DIGIT", "HEX_DIGITS", "HEX_PREFIX", "IMPLEMENTS_DIRECTIVE", "INLINE_INDEX", "INSTRUCTION_FORMAT10t", "INSTRUCTION_FORMAT10x", "INSTRUCTION_FORMAT10x_ODEX", "INSTRUCTION_FORMAT11n", "INSTRUCTION_FORMAT11x", "INSTRUCTION_FORMAT12x", "INSTRUCTION_FORMAT12x_OR_ID", "INSTRUCTION_FORMAT20bc", "INSTRUCTION_FORMAT20t", "INSTRUCTION_FORMAT21c_FIELD", "INSTRUCTION_FORMAT21c_FIELD_ODEX", "INSTRUCTION_FORMAT21c_STRING", "INSTRUCTION_FORMAT21c_TYPE", "INSTRUCTION_FORMAT21ih", "INSTRUCTION_FORMAT21lh", "INSTRUCTION_FORMAT21s", "INSTRUCTION_FORMAT21t", "INSTRUCTION_FORMAT22b", "INSTRUCTION_FORMAT22c_FIELD", "INSTRUCTION_FORMAT22c_FIELD_ODEX", "INSTRUCTION_FORMAT22c_TYPE", "INSTRUCTION_FORMAT22cs_FIELD", "INSTRUCTION_FORMAT22s", "INSTRUCTION_FORMAT22s_OR_ID", "INSTRUCTION_FORMAT22t", "INSTRUCTION_FORMAT22x", "INSTRUCTION_FORMAT23x", "INSTRUCTION_FORMAT30t", "INSTRUCTION_FORMAT31c", "INSTRUCTION_FORMAT31i", "INSTRUCTION_FORMAT31i_OR_ID", "INSTRUCTION_FORMAT31t", "INSTRUCTION_FORMAT32x", "INSTRUCTION_FORMAT35c_METHOD", "INSTRUCTION_FORMAT35c_METHOD_ODEX", "INSTRUCTION_FORMAT35c_TYPE", "INSTRUCTION_FORMAT35mi_METHOD", "INSTRUCTION_FORMAT35ms_METHOD", "INSTRUCTION_FORMAT3rc_METHOD", "INSTRUCTION_FORMAT3rc_METHOD_ODEX", "INSTRUCTION_FORMAT3rc_TYPE", "INSTRUCTION_FORMAT3rmi_METHOD", "INSTRUCTION_FORMAT3rms_METHOD", "INSTRUCTION_FORMAT51l", "INTEGER_LITERAL", "INVALID_TOKEN", "I_ACCESS_LIST", "I_ANNOTATION", "I_ANNOTATIONS", "I_ANNOTATION_ELEMENT", "I_ARRAY_ELEMENTS", "I_ARRAY_ELEMENT_SIZE", "I_CATCH", "I_CATCHALL", "I_CATCHES", "I_CLASS_DEF", "I_ENCODED_ARRAY", "I_ENCODED_ENUM", "I_ENCODED_FIELD", "I_ENCODED_METHOD", "I_END_LOCAL", "I_EPILOGUE", "I_FIELD", "I_FIELDS", "I_FIELD_INITIAL_VALUE", "I_FIELD_TYPE", "I_IMPLEMENTS", "I_LABEL", "I_LINE", "I_LOCAL", "I_LOCALS", "I_METHOD", "I_METHODS", "I_METHOD_PROTOTYPE", "I_METHOD_RETURN_TYPE", "I_ORDERED_METHOD_ITEMS", "I_PACKED_SWITCH_ELEMENTS", "I_PACKED_SWITCH_START_KEY", "I_PARAMETER", "I_PARAMETERS", "I_PARAMETER_NOT_SPECIFIED", "I_PROLOGUE", "I_REGISTERS", "I_REGISTER_LIST", "I_REGISTER_RANGE", "I_RESTART_LOCAL", "I_SOURCE", "I_SPARSE_SWITCH_ELEMENTS", "I_STATEMENT_ARRAY_DATA", "I_STATEMENT_FORMAT10t", "I_STATEMENT_FORMAT10x", "I_STATEMENT_FORMAT11n", "I_STATEMENT_FORMAT11x", "I_STATEMENT_FORMAT12x", "I_STATEMENT_FORMAT20bc", "I_STATEMENT_FORMAT20t", "I_STATEMENT_FORMAT21c_FIELD", "I_STATEMENT_FORMAT21c_STRING", "I_STATEMENT_FORMAT21c_TYPE", "I_STATEMENT_FORMAT21ih", "I_STATEMENT_FORMAT21lh", "I_STATEMENT_FORMAT21s", "I_STATEMENT_FORMAT21t", "I_STATEMENT_FORMAT22b", "I_STATEMENT_FORMAT22c_FIELD", "I_STATEMENT_FORMAT22c_TYPE", "I_STATEMENT_FORMAT22s", "I_STATEMENT_FORMAT22t", "I_STATEMENT_FORMAT22x", "I_STATEMENT_FORMAT23x", "I_STATEMENT_FORMAT30t", "I_STATEMENT_FORMAT31c", "I_STATEMENT_FORMAT31i", "I_STATEMENT_FORMAT31t", "I_STATEMENT_FORMAT32x", "I_STATEMENT_FORMAT35c_METHOD", "I_STATEMENT_FORMAT35c_TYPE", "I_STATEMENT_FORMAT3rc_METHOD", "I_STATEMENT_FORMAT3rc_TYPE", "I_STATEMENT_FORMAT51l", "I_STATEMENT_PACKED_SWITCH", "I_STATEMENT_SPARSE_SWITCH", "I_SUBANNOTATION", "I_SUPER", "LABEL", "LINE_COMMENT", "LINE_DIRECTIVE", "LOCALS_DIRECTIVE", "LOCAL_DIRECTIVE", "LONG_LITERAL", "MEMBER_NAME", "METHOD_DIRECTIVE", "NEGATIVE_INTEGER_LITERAL", "NULL_LITERAL", "OPEN_BRACE", "OPEN_PAREN", "PACKED_SWITCH_DIRECTIVE", "PARAMETER_DIRECTIVE", "PARAM_LIST", "PARAM_LIST_OR_ID", "POSITIVE_INTEGER_LITERAL", "PRIMITIVE_TYPE", "PROLOGUE_DIRECTIVE", "REGISTER", "REGISTERS_DIRECTIVE", "RESTART_LOCAL_DIRECTIVE", "SHORT_LITERAL", "SIMPLE_NAME", "SOURCE_DIRECTIVE", "SPARSE_SWITCH_DIRECTIVE", "STRING_LITERAL", "SUBANNOTATION_DIRECTIVE", "SUPER_DIRECTIVE", "VERIFICATION_ERROR_TYPE", "VOID_TYPE", "VTABLE_INDEX", "WHITE_SPACE" };
/*      */   public String classType;
/*  344 */   private boolean verboseErrors = false;
/*  345 */   private int apiLevel = 15;
/*  346 */   private Opcodes opcodes = new Opcodes(this.apiLevel);
/*      */   private DexBuilder dexBuilder;
/* 2088 */   protected Stack<method_scope> method_stack = new Stack();
/*      */ 
/* 6981 */   public static final BitSet FOLLOW_I_CLASS_DEF_in_smali_file52 = new BitSet(new long[] { 4L });
/* 6982 */   public static final BitSet FOLLOW_header_in_smali_file54 = new BitSet(new long[] { 0L, 0L, 4L });
/* 6983 */   public static final BitSet FOLLOW_methods_in_smali_file56 = new BitSet(new long[] { 0L, 144115188075855872L });
/* 6984 */   public static final BitSet FOLLOW_fields_in_smali_file58 = new BitSet(new long[] { 0L, 4398046511104L });
/* 6985 */   public static final BitSet FOLLOW_annotations_in_smali_file60 = new BitSet(new long[] { 8L });
/* 6986 */   public static final BitSet FOLLOW_class_spec_in_header85 = new BitSet(new long[] { 0L, 1152921504606846976L, 9007199254806528L });
/* 6987 */   public static final BitSet FOLLOW_super_spec_in_header87 = new BitSet(new long[] { 0L, 1152921504606846976L, 65536L });
/* 6988 */   public static final BitSet FOLLOW_implements_list_in_header90 = new BitSet(new long[] { 0L, 0L, 65536L });
/* 6989 */   public static final BitSet FOLLOW_source_spec_in_header92 = new BitSet(new long[] { 2L });
/* 6990 */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_class_spec110 = new BitSet(new long[] { 0L, 1099511627776L });
/* 6991 */   public static final BitSet FOLLOW_access_list_in_class_spec112 = new BitSet(new long[] { 2L });
/* 6992 */   public static final BitSet FOLLOW_I_SUPER_in_super_spec130 = new BitSet(new long[] { 4L });
/* 6993 */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_super_spec132 = new BitSet(new long[] { 8L });
/* 6994 */   public static final BitSet FOLLOW_I_IMPLEMENTS_in_implements_spec152 = new BitSet(new long[] { 4L });
/* 6995 */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_implements_spec154 = new BitSet(new long[] { 8L });
/* 6996 */   public static final BitSet FOLLOW_implements_spec_in_implements_list184 = new BitSet(new long[] { 2L, 1152921504606846976L });
/* 6997 */   public static final BitSet FOLLOW_I_SOURCE_in_source_spec213 = new BitSet(new long[] { 4L });
/* 6998 */   public static final BitSet FOLLOW_string_literal_in_source_spec215 = new BitSet(new long[] { 8L });
/* 6999 */   public static final BitSet FOLLOW_I_ACCESS_LIST_in_access_list248 = new BitSet(new long[] { 4L });
/* 7000 */   public static final BitSet FOLLOW_ACCESS_SPEC_in_access_list266 = new BitSet(new long[] { 24L });
/* 7001 */   public static final BitSet FOLLOW_I_FIELDS_in_fields308 = new BitSet(new long[] { 4L });
/* 7002 */   public static final BitSet FOLLOW_field_in_fields317 = new BitSet(new long[] { 8L, 72057594037927936L });
/* 7003 */   public static final BitSet FOLLOW_I_METHODS_in_methods349 = new BitSet(new long[] { 4L });
/* 7004 */   public static final BitSet FOLLOW_method_in_methods358 = new BitSet(new long[] { 8L, 0L, 2L });
/* 7005 */   public static final BitSet FOLLOW_I_FIELD_in_field383 = new BitSet(new long[] { 4L });
/* 7006 */   public static final BitSet FOLLOW_SIMPLE_NAME_in_field385 = new BitSet(new long[] { 0L, 1099511627776L });
/* 7007 */   public static final BitSet FOLLOW_access_list_in_field387 = new BitSet(new long[] { 0L, 576460752303423488L });
/* 7008 */   public static final BitSet FOLLOW_I_FIELD_TYPE_in_field390 = new BitSet(new long[] { 4L });
/* 7009 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field392 = new BitSet(new long[] { 8L });
/* 7010 */   public static final BitSet FOLLOW_field_initial_value_in_field395 = new BitSet(new long[] { 8L, 4398046511104L });
/* 7011 */   public static final BitSet FOLLOW_annotations_in_field397 = new BitSet(new long[] { 8L });
/* 7012 */   public static final BitSet FOLLOW_I_FIELD_INITIAL_VALUE_in_field_initial_value418 = new BitSet(new long[] { 4L });
/* 7013 */   public static final BitSet FOLLOW_literal_in_field_initial_value420 = new BitSet(new long[] { 8L });
/* 7014 */   public static final BitSet FOLLOW_integer_literal_in_literal442 = new BitSet(new long[] { 2L });
/* 7015 */   public static final BitSet FOLLOW_long_literal_in_literal450 = new BitSet(new long[] { 2L });
/* 7016 */   public static final BitSet FOLLOW_short_literal_in_literal458 = new BitSet(new long[] { 2L });
/* 7017 */   public static final BitSet FOLLOW_byte_literal_in_literal466 = new BitSet(new long[] { 2L });
/* 7018 */   public static final BitSet FOLLOW_float_literal_in_literal474 = new BitSet(new long[] { 2L });
/* 7019 */   public static final BitSet FOLLOW_double_literal_in_literal482 = new BitSet(new long[] { 2L });
/* 7020 */   public static final BitSet FOLLOW_char_literal_in_literal490 = new BitSet(new long[] { 2L });
/* 7021 */   public static final BitSet FOLLOW_string_literal_in_literal498 = new BitSet(new long[] { 2L });
/* 7022 */   public static final BitSet FOLLOW_bool_literal_in_literal506 = new BitSet(new long[] { 2L });
/* 7023 */   public static final BitSet FOLLOW_NULL_LITERAL_in_literal514 = new BitSet(new long[] { 2L });
/* 7024 */   public static final BitSet FOLLOW_type_descriptor_in_literal522 = new BitSet(new long[] { 2L });
/* 7025 */   public static final BitSet FOLLOW_array_literal_in_literal530 = new BitSet(new long[] { 2L });
/* 7026 */   public static final BitSet FOLLOW_subannotation_in_literal538 = new BitSet(new long[] { 2L });
/* 7027 */   public static final BitSet FOLLOW_field_literal_in_literal546 = new BitSet(new long[] { 2L });
/* 7028 */   public static final BitSet FOLLOW_method_literal_in_literal554 = new BitSet(new long[] { 2L });
/* 7029 */   public static final BitSet FOLLOW_enum_literal_in_literal562 = new BitSet(new long[] { 2L });
/* 7030 */   public static final BitSet FOLLOW_integer_literal_in_fixed_64bit_literal_number578 = new BitSet(new long[] { 2L });
/* 7031 */   public static final BitSet FOLLOW_long_literal_in_fixed_64bit_literal_number586 = new BitSet(new long[] { 2L });
/* 7032 */   public static final BitSet FOLLOW_short_literal_in_fixed_64bit_literal_number594 = new BitSet(new long[] { 2L });
/* 7033 */   public static final BitSet FOLLOW_byte_literal_in_fixed_64bit_literal_number602 = new BitSet(new long[] { 2L });
/* 7034 */   public static final BitSet FOLLOW_float_literal_in_fixed_64bit_literal_number610 = new BitSet(new long[] { 2L });
/* 7035 */   public static final BitSet FOLLOW_double_literal_in_fixed_64bit_literal_number618 = new BitSet(new long[] { 2L });
/* 7036 */   public static final BitSet FOLLOW_char_literal_in_fixed_64bit_literal_number626 = new BitSet(new long[] { 2L });
/* 7037 */   public static final BitSet FOLLOW_bool_literal_in_fixed_64bit_literal_number634 = new BitSet(new long[] { 2L });
/* 7038 */   public static final BitSet FOLLOW_integer_literal_in_fixed_64bit_literal649 = new BitSet(new long[] { 2L });
/* 7039 */   public static final BitSet FOLLOW_long_literal_in_fixed_64bit_literal657 = new BitSet(new long[] { 2L });
/* 7040 */   public static final BitSet FOLLOW_short_literal_in_fixed_64bit_literal665 = new BitSet(new long[] { 2L });
/* 7041 */   public static final BitSet FOLLOW_byte_literal_in_fixed_64bit_literal673 = new BitSet(new long[] { 2L });
/* 7042 */   public static final BitSet FOLLOW_float_literal_in_fixed_64bit_literal681 = new BitSet(new long[] { 2L });
/* 7043 */   public static final BitSet FOLLOW_double_literal_in_fixed_64bit_literal689 = new BitSet(new long[] { 2L });
/* 7044 */   public static final BitSet FOLLOW_char_literal_in_fixed_64bit_literal697 = new BitSet(new long[] { 2L });
/* 7045 */   public static final BitSet FOLLOW_bool_literal_in_fixed_64bit_literal705 = new BitSet(new long[] { 2L });
/* 7046 */   public static final BitSet FOLLOW_integer_literal_in_fixed_32bit_literal722 = new BitSet(new long[] { 2L });
/* 7047 */   public static final BitSet FOLLOW_long_literal_in_fixed_32bit_literal730 = new BitSet(new long[] { 2L });
/* 7048 */   public static final BitSet FOLLOW_short_literal_in_fixed_32bit_literal738 = new BitSet(new long[] { 2L });
/* 7049 */   public static final BitSet FOLLOW_byte_literal_in_fixed_32bit_literal746 = new BitSet(new long[] { 2L });
/* 7050 */   public static final BitSet FOLLOW_float_literal_in_fixed_32bit_literal754 = new BitSet(new long[] { 2L });
/* 7051 */   public static final BitSet FOLLOW_char_literal_in_fixed_32bit_literal762 = new BitSet(new long[] { 2L });
/* 7052 */   public static final BitSet FOLLOW_bool_literal_in_fixed_32bit_literal770 = new BitSet(new long[] { 2L });
/* 7053 */   public static final BitSet FOLLOW_I_ARRAY_ELEMENTS_in_array_elements792 = new BitSet(new long[] { 4L });
/* 7054 */   public static final BitSet FOLLOW_fixed_64bit_literal_number_in_array_elements801 = new BitSet(new long[] { 2251817033400328L, 274877906944L, 576460752303423488L, 4096L });
/* 7055 */   public static final BitSet FOLLOW_I_PACKED_SWITCH_ELEMENTS_in_packed_switch_elements837 = new BitSet(new long[] { 4L });
/* 7056 */   public static final BitSet FOLLOW_label_ref_in_packed_switch_elements846 = new BitSet(new long[] { 8L, 0L, 0L, 8192L });
/* 7057 */   public static final BitSet FOLLOW_I_SPARSE_SWITCH_ELEMENTS_in_sparse_switch_elements881 = new BitSet(new long[] { 4L });
/* 7058 */   public static final BitSet FOLLOW_fixed_32bit_literal_in_sparse_switch_elements891 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7059 */   public static final BitSet FOLLOW_label_ref_in_sparse_switch_elements893 = new BitSet(new long[] { 2251799853531144L, 274877906944L, 576460752303423488L, 4096L });
/* 7060 */   public static final BitSet FOLLOW_I_METHOD_in_method945 = new BitSet(new long[] { 4L });
/* 7061 */   public static final BitSet FOLLOW_method_name_and_prototype_in_method953 = new BitSet(new long[] { 0L, 1099511627776L });
/* 7062 */   public static final BitSet FOLLOW_access_list_in_method961 = new BitSet(new long[] { 0L, 0L, 4129L });
/* 7063 */   public static final BitSet FOLLOW_registers_directive_in_method988 = new BitSet(new long[] { 0L, 0L, 32L });
/* 7064 */   public static final BitSet FOLLOW_ordered_method_items_in_method1045 = new BitSet(new long[] { 0L, 281474976710656L });
/* 7065 */   public static final BitSet FOLLOW_catches_in_method1053 = new BitSet(new long[] { 0L, 0L, 512L });
/* 7066 */   public static final BitSet FOLLOW_parameters_in_method1061 = new BitSet(new long[] { 0L, 4398046511104L });
/* 7067 */   public static final BitSet FOLLOW_annotations_in_method1070 = new BitSet(new long[] { 8L });
/* 7068 */   public static final BitSet FOLLOW_I_METHOD_PROTOTYPE_in_method_prototype1094 = new BitSet(new long[] { 4L });
/* 7069 */   public static final BitSet FOLLOW_I_METHOD_RETURN_TYPE_in_method_prototype1097 = new BitSet(new long[] { 4L });
/* 7070 */   public static final BitSet FOLLOW_type_descriptor_in_method_prototype1099 = new BitSet(new long[] { 8L });
/* 7071 */   public static final BitSet FOLLOW_field_type_list_in_method_prototype1102 = new BitSet(new long[] { 8L });
/* 7072 */   public static final BitSet FOLLOW_SIMPLE_NAME_in_method_name_and_prototype1120 = new BitSet(new long[] { 0L, 0L, 8L });
/* 7073 */   public static final BitSet FOLLOW_method_prototype_in_method_name_and_prototype1122 = new BitSet(new long[] { 2L });
/* 7074 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_field_type_list1156 = new BitSet(new long[] { 67109122L, 0L, 0L, 128L });
/* 7075 */   public static final BitSet FOLLOW_reference_type_descriptor_in_fully_qualified_method1185 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7076 */   public static final BitSet FOLLOW_SIMPLE_NAME_in_fully_qualified_method1187 = new BitSet(new long[] { 0L, 0L, 8L });
/* 7077 */   public static final BitSet FOLLOW_method_prototype_in_fully_qualified_method1189 = new BitSet(new long[] { 2L });
/* 7078 */   public static final BitSet FOLLOW_reference_type_descriptor_in_fully_qualified_field1206 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7079 */   public static final BitSet FOLLOW_SIMPLE_NAME_in_fully_qualified_field1208 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 7080 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_fully_qualified_field1210 = new BitSet(new long[] { 2L });
/* 7081 */   public static final BitSet FOLLOW_I_REGISTERS_in_registers_directive1236 = new BitSet(new long[] { 4L });
/* 7082 */   public static final BitSet FOLLOW_I_LOCALS_in_registers_directive1248 = new BitSet(new long[] { 4L });
/* 7083 */   public static final BitSet FOLLOW_short_integral_literal_in_registers_directive1266 = new BitSet(new long[] { 8L });
/* 7084 */   public static final BitSet FOLLOW_I_LABEL_in_label_def1286 = new BitSet(new long[] { 4L });
/* 7085 */   public static final BitSet FOLLOW_SIMPLE_NAME_in_label_def1288 = new BitSet(new long[] { 8L });
/* 7086 */   public static final BitSet FOLLOW_I_CATCHES_in_catches1314 = new BitSet(new long[] { 4L });
/* 7087 */   public static final BitSet FOLLOW_catch_directive_in_catches1316 = new BitSet(new long[] { 8L, 211106232532992L });
/* 7088 */   public static final BitSet FOLLOW_catchall_directive_in_catches1319 = new BitSet(new long[] { 8L, 140737488355328L });
/* 7089 */   public static final BitSet FOLLOW_I_CATCH_in_catch_directive1332 = new BitSet(new long[] { 4L });
/* 7090 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_catch_directive1334 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7091 */   public static final BitSet FOLLOW_label_ref_in_catch_directive1338 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7092 */   public static final BitSet FOLLOW_label_ref_in_catch_directive1342 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7093 */   public static final BitSet FOLLOW_label_ref_in_catch_directive1346 = new BitSet(new long[] { 8L });
/* 7094 */   public static final BitSet FOLLOW_I_CATCHALL_in_catchall_directive1362 = new BitSet(new long[] { 4L });
/* 7095 */   public static final BitSet FOLLOW_label_ref_in_catchall_directive1366 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7096 */   public static final BitSet FOLLOW_label_ref_in_catchall_directive1370 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7097 */   public static final BitSet FOLLOW_label_ref_in_catchall_directive1374 = new BitSet(new long[] { 8L });
/* 7098 */   public static final BitSet FOLLOW_I_PARAMETERS_in_parameters1391 = new BitSet(new long[] { 4L });
/* 7099 */   public static final BitSet FOLLOW_parameter_in_parameters1394 = new BitSet(new long[] { 8L, 0L, 256L });
/* 7100 */   public static final BitSet FOLLOW_I_PARAMETER_in_parameter1410 = new BitSet(new long[] { 4L });
/* 7101 */   public static final BitSet FOLLOW_REGISTER_in_parameter1412 = new BitSet(new long[] { 0L, 4398046511104L, 0L, 65536L });
/* 7102 */   public static final BitSet FOLLOW_string_literal_in_parameter1414 = new BitSet(new long[] { 0L, 4398046511104L });
/* 7103 */   public static final BitSet FOLLOW_annotations_in_parameter1417 = new BitSet(new long[] { 8L });
/* 7104 */   public static final BitSet FOLLOW_line_in_debug_directive1434 = new BitSet(new long[] { 2L });
/* 7105 */   public static final BitSet FOLLOW_local_in_debug_directive1440 = new BitSet(new long[] { 2L });
/* 7106 */   public static final BitSet FOLLOW_end_local_in_debug_directive1446 = new BitSet(new long[] { 2L });
/* 7107 */   public static final BitSet FOLLOW_restart_local_in_debug_directive1452 = new BitSet(new long[] { 2L });
/* 7108 */   public static final BitSet FOLLOW_prologue_in_debug_directive1458 = new BitSet(new long[] { 2L });
/* 7109 */   public static final BitSet FOLLOW_epilogue_in_debug_directive1464 = new BitSet(new long[] { 2L });
/* 7110 */   public static final BitSet FOLLOW_source_in_debug_directive1470 = new BitSet(new long[] { 2L });
/* 7111 */   public static final BitSet FOLLOW_I_LINE_in_line1481 = new BitSet(new long[] { 4L });
/* 7112 */   public static final BitSet FOLLOW_integral_literal_in_line1483 = new BitSet(new long[] { 8L });
/* 7113 */   public static final BitSet FOLLOW_I_LOCAL_in_local1501 = new BitSet(new long[] { 4L });
/* 7114 */   public static final BitSet FOLLOW_REGISTER_in_local1503 = new BitSet(new long[] { 8L, 0L, -9223372036854775808L, 65536L });
/* 7115 */   public static final BitSet FOLLOW_NULL_LITERAL_in_local1507 = new BitSet(new long[] { 67109128L, 0L, 0L, 65664L });
/* 7116 */   public static final BitSet FOLLOW_string_literal_in_local1513 = new BitSet(new long[] { 67109128L, 0L, 0L, 65664L });
/* 7117 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_local1516 = new BitSet(new long[] { 8L, 0L, 0L, 65536L });
/* 7118 */   public static final BitSet FOLLOW_string_literal_in_local1521 = new BitSet(new long[] { 8L });
/* 7119 */   public static final BitSet FOLLOW_I_END_LOCAL_in_end_local1542 = new BitSet(new long[] { 4L });
/* 7120 */   public static final BitSet FOLLOW_REGISTER_in_end_local1544 = new BitSet(new long[] { 8L });
/* 7121 */   public static final BitSet FOLLOW_I_RESTART_LOCAL_in_restart_local1562 = new BitSet(new long[] { 4L });
/* 7122 */   public static final BitSet FOLLOW_REGISTER_in_restart_local1564 = new BitSet(new long[] { 8L });
/* 7123 */   public static final BitSet FOLLOW_I_PROLOGUE_in_prologue1581 = new BitSet(new long[] { 2L });
/* 7124 */   public static final BitSet FOLLOW_I_EPILOGUE_in_epilogue1597 = new BitSet(new long[] { 2L });
/* 7125 */   public static final BitSet FOLLOW_I_SOURCE_in_source1614 = new BitSet(new long[] { 4L });
/* 7126 */   public static final BitSet FOLLOW_string_literal_in_source1616 = new BitSet(new long[] { 8L });
/* 7127 */   public static final BitSet FOLLOW_I_ORDERED_METHOD_ITEMS_in_ordered_method_items1635 = new BitSet(new long[] { 4L });
/* 7128 */   public static final BitSet FOLLOW_label_def_in_ordered_method_items1638 = new BitSet(new long[] { 8L, -2251799813685248000L, 4503599627208704L });
/* 7129 */   public static final BitSet FOLLOW_instruction_in_ordered_method_items1642 = new BitSet(new long[] { 8L, -2251799813685248000L, 4503599627208704L });
/* 7130 */   public static final BitSet FOLLOW_debug_directive_in_ordered_method_items1646 = new BitSet(new long[] { 8L, -2251799813685248000L, 4503599627208704L });
/* 7131 */   public static final BitSet FOLLOW_SIMPLE_NAME_in_label_ref1662 = new BitSet(new long[] { 2L });
/* 7132 */   public static final BitSet FOLLOW_I_REGISTER_LIST_in_register_list1687 = new BitSet(new long[] { 4L });
/* 7133 */   public static final BitSet FOLLOW_REGISTER_in_register_list1696 = new BitSet(new long[] { 8L, 0L, 0L, 512L });
/* 7134 */   public static final BitSet FOLLOW_I_REGISTER_RANGE_in_register_range1721 = new BitSet(new long[] { 4L });
/* 7135 */   public static final BitSet FOLLOW_REGISTER_in_register_range1726 = new BitSet(new long[] { 8L, 0L, 0L, 512L });
/* 7136 */   public static final BitSet FOLLOW_REGISTER_in_register_range1730 = new BitSet(new long[] { 8L });
/* 7137 */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference1753 = new BitSet(new long[] { 2L });
/* 7138 */   public static final BitSet FOLLOW_fully_qualified_field_in_verification_error_reference1763 = new BitSet(new long[] { 2L });
/* 7139 */   public static final BitSet FOLLOW_fully_qualified_method_in_verification_error_reference1773 = new BitSet(new long[] { 2L });
/* 7140 */   public static final BitSet FOLLOW_VERIFICATION_ERROR_TYPE_in_verification_error_type1790 = new BitSet(new long[] { 2L });
/* 7141 */   public static final BitSet FOLLOW_insn_format10t_in_instruction1804 = new BitSet(new long[] { 2L });
/* 7142 */   public static final BitSet FOLLOW_insn_format10x_in_instruction1810 = new BitSet(new long[] { 2L });
/* 7143 */   public static final BitSet FOLLOW_insn_format11n_in_instruction1816 = new BitSet(new long[] { 2L });
/* 7144 */   public static final BitSet FOLLOW_insn_format11x_in_instruction1822 = new BitSet(new long[] { 2L });
/* 7145 */   public static final BitSet FOLLOW_insn_format12x_in_instruction1828 = new BitSet(new long[] { 2L });
/* 7146 */   public static final BitSet FOLLOW_insn_format20bc_in_instruction1834 = new BitSet(new long[] { 2L });
/* 7147 */   public static final BitSet FOLLOW_insn_format20t_in_instruction1840 = new BitSet(new long[] { 2L });
/* 7148 */   public static final BitSet FOLLOW_insn_format21c_field_in_instruction1846 = new BitSet(new long[] { 2L });
/* 7149 */   public static final BitSet FOLLOW_insn_format21c_string_in_instruction1852 = new BitSet(new long[] { 2L });
/* 7150 */   public static final BitSet FOLLOW_insn_format21c_type_in_instruction1858 = new BitSet(new long[] { 2L });
/* 7151 */   public static final BitSet FOLLOW_insn_format21ih_in_instruction1864 = new BitSet(new long[] { 2L });
/* 7152 */   public static final BitSet FOLLOW_insn_format21lh_in_instruction1870 = new BitSet(new long[] { 2L });
/* 7153 */   public static final BitSet FOLLOW_insn_format21s_in_instruction1876 = new BitSet(new long[] { 2L });
/* 7154 */   public static final BitSet FOLLOW_insn_format21t_in_instruction1882 = new BitSet(new long[] { 2L });
/* 7155 */   public static final BitSet FOLLOW_insn_format22b_in_instruction1888 = new BitSet(new long[] { 2L });
/* 7156 */   public static final BitSet FOLLOW_insn_format22c_field_in_instruction1894 = new BitSet(new long[] { 2L });
/* 7157 */   public static final BitSet FOLLOW_insn_format22c_type_in_instruction1900 = new BitSet(new long[] { 2L });
/* 7158 */   public static final BitSet FOLLOW_insn_format22s_in_instruction1906 = new BitSet(new long[] { 2L });
/* 7159 */   public static final BitSet FOLLOW_insn_format22t_in_instruction1912 = new BitSet(new long[] { 2L });
/* 7160 */   public static final BitSet FOLLOW_insn_format22x_in_instruction1918 = new BitSet(new long[] { 2L });
/* 7161 */   public static final BitSet FOLLOW_insn_format23x_in_instruction1924 = new BitSet(new long[] { 2L });
/* 7162 */   public static final BitSet FOLLOW_insn_format30t_in_instruction1930 = new BitSet(new long[] { 2L });
/* 7163 */   public static final BitSet FOLLOW_insn_format31c_in_instruction1936 = new BitSet(new long[] { 2L });
/* 7164 */   public static final BitSet FOLLOW_insn_format31i_in_instruction1942 = new BitSet(new long[] { 2L });
/* 7165 */   public static final BitSet FOLLOW_insn_format31t_in_instruction1948 = new BitSet(new long[] { 2L });
/* 7166 */   public static final BitSet FOLLOW_insn_format32x_in_instruction1954 = new BitSet(new long[] { 2L });
/* 7167 */   public static final BitSet FOLLOW_insn_format35c_method_in_instruction1960 = new BitSet(new long[] { 2L });
/* 7168 */   public static final BitSet FOLLOW_insn_format35c_type_in_instruction1966 = new BitSet(new long[] { 2L });
/* 7169 */   public static final BitSet FOLLOW_insn_format3rc_method_in_instruction1972 = new BitSet(new long[] { 2L });
/* 7170 */   public static final BitSet FOLLOW_insn_format3rc_type_in_instruction1978 = new BitSet(new long[] { 2L });
/* 7171 */   public static final BitSet FOLLOW_insn_format51l_type_in_instruction1984 = new BitSet(new long[] { 2L });
/* 7172 */   public static final BitSet FOLLOW_insn_array_data_directive_in_instruction1990 = new BitSet(new long[] { 2L });
/* 7173 */   public static final BitSet FOLLOW_insn_packed_switch_directive_in_instruction1996 = new BitSet(new long[] { 2L });
/* 7174 */   public static final BitSet FOLLOW_insn_sparse_switch_directive_in_instruction2002 = new BitSet(new long[] { 2L });
/* 7175 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT10t_in_insn_format10t2026 = new BitSet(new long[] { 4L });
/* 7176 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t2028 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7177 */   public static final BitSet FOLLOW_label_ref_in_insn_format10t2030 = new BitSet(new long[] { 8L });
/* 7178 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT10x_in_insn_format10x2053 = new BitSet(new long[] { 4L });
/* 7179 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x2055 = new BitSet(new long[] { 8L });
/* 7180 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT11n_in_insn_format11n2078 = new BitSet(new long[] { 4L });
/* 7181 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n2080 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7182 */   public static final BitSet FOLLOW_REGISTER_in_insn_format11n2082 = new BitSet(new long[] { 37748736L, 274877906944L, 576460752303423488L, 4096L });
/* 7183 */   public static final BitSet FOLLOW_short_integral_literal_in_insn_format11n2084 = new BitSet(new long[] { 8L });
/* 7184 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT11x_in_insn_format11x2107 = new BitSet(new long[] { 4L });
/* 7185 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x2109 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7186 */   public static final BitSet FOLLOW_REGISTER_in_insn_format11x2111 = new BitSet(new long[] { 8L });
/* 7187 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT12x_in_insn_format12x2134 = new BitSet(new long[] { 4L });
/* 7188 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT12x_in_insn_format12x2136 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7189 */   public static final BitSet FOLLOW_REGISTER_in_insn_format12x2140 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7190 */   public static final BitSet FOLLOW_REGISTER_in_insn_format12x2144 = new BitSet(new long[] { 8L });
/* 7191 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT20bc_in_insn_format20bc2167 = new BitSet(new long[] { 4L });
/* 7192 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc2169 = new BitSet(new long[] { 0L, 0L, 0L, 524288L });
/* 7193 */   public static final BitSet FOLLOW_verification_error_type_in_insn_format20bc2171 = new BitSet(new long[] { 67109120L });
/* 7194 */   public static final BitSet FOLLOW_verification_error_reference_in_insn_format20bc2173 = new BitSet(new long[] { 8L });
/* 7195 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT20t_in_insn_format20t2196 = new BitSet(new long[] { 4L });
/* 7196 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t2198 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7197 */   public static final BitSet FOLLOW_label_ref_in_insn_format20t2200 = new BitSet(new long[] { 8L });
/* 7198 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_FIELD_in_insn_format21c_field2223 = new BitSet(new long[] { 4L });
/* 7199 */   public static final BitSet FOLLOW_set_in_insn_format21c_field2227 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7200 */   public static final BitSet FOLLOW_REGISTER_in_insn_format21c_field2235 = new BitSet(new long[] { 67109120L });
/* 7201 */   public static final BitSet FOLLOW_fully_qualified_field_in_insn_format21c_field2237 = new BitSet(new long[] { 8L });
/* 7202 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_STRING_in_insn_format21c_string2260 = new BitSet(new long[] { 4L });
/* 7203 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string2262 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7204 */   public static final BitSet FOLLOW_REGISTER_in_insn_format21c_string2264 = new BitSet(new long[] { 0L, 0L, 0L, 65536L });
/* 7205 */   public static final BitSet FOLLOW_string_literal_in_insn_format21c_string2266 = new BitSet(new long[] { 8L });
/* 7206 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT21c_TYPE_in_insn_format21c_type2289 = new BitSet(new long[] { 4L });
/* 7207 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type2291 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7208 */   public static final BitSet FOLLOW_REGISTER_in_insn_format21c_type2293 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 7209 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type2295 = new BitSet(new long[] { 8L });
/* 7210 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT21ih_in_insn_format21ih2318 = new BitSet(new long[] { 4L });
/* 7211 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih2320 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7212 */   public static final BitSet FOLLOW_REGISTER_in_insn_format21ih2322 = new BitSet(new long[] { 2251799853531136L, 274877906944L, 576460752303423488L, 4096L });
/* 7213 */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format21ih2324 = new BitSet(new long[] { 8L });
/* 7214 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT21lh_in_insn_format21lh2347 = new BitSet(new long[] { 4L });
/* 7215 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh2349 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7216 */   public static final BitSet FOLLOW_REGISTER_in_insn_format21lh2351 = new BitSet(new long[] { 2251817033400320L, 274877906944L, 576460752303423488L, 4096L });
/* 7217 */   public static final BitSet FOLLOW_fixed_64bit_literal_in_insn_format21lh2353 = new BitSet(new long[] { 8L });
/* 7218 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT21s_in_insn_format21s2376 = new BitSet(new long[] { 4L });
/* 7219 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s2378 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7220 */   public static final BitSet FOLLOW_REGISTER_in_insn_format21s2380 = new BitSet(new long[] { 37748736L, 274877906944L, 576460752303423488L, 4096L });
/* 7221 */   public static final BitSet FOLLOW_short_integral_literal_in_insn_format21s2382 = new BitSet(new long[] { 8L });
/* 7222 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT21t_in_insn_format21t2405 = new BitSet(new long[] { 4L });
/* 7223 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t2407 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7224 */   public static final BitSet FOLLOW_REGISTER_in_insn_format21t2409 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7225 */   public static final BitSet FOLLOW_label_ref_in_insn_format21t2411 = new BitSet(new long[] { 8L });
/* 7226 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT22b_in_insn_format22b2434 = new BitSet(new long[] { 4L });
/* 7227 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b2436 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7228 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22b2440 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7229 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22b2444 = new BitSet(new long[] { 37748736L, 274877906944L, 576460752303423488L, 4096L });
/* 7230 */   public static final BitSet FOLLOW_short_integral_literal_in_insn_format22b2446 = new BitSet(new long[] { 8L });
/* 7231 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT22c_FIELD_in_insn_format22c_field2469 = new BitSet(new long[] { 4L });
/* 7232 */   public static final BitSet FOLLOW_set_in_insn_format22c_field2473 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7233 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field2483 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7234 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_field2487 = new BitSet(new long[] { 67109120L });
/* 7235 */   public static final BitSet FOLLOW_fully_qualified_field_in_insn_format22c_field2489 = new BitSet(new long[] { 8L });
/* 7236 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT22c_TYPE_in_insn_format22c_type2512 = new BitSet(new long[] { 4L });
/* 7237 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type2514 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7238 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type2518 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7239 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22c_type2522 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 7240 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type2524 = new BitSet(new long[] { 8L });
/* 7241 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT22s_in_insn_format22s2547 = new BitSet(new long[] { 4L });
/* 7242 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22s_in_insn_format22s2549 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7243 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22s2553 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7244 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22s2557 = new BitSet(new long[] { 37748736L, 274877906944L, 576460752303423488L, 4096L });
/* 7245 */   public static final BitSet FOLLOW_short_integral_literal_in_insn_format22s2559 = new BitSet(new long[] { 8L });
/* 7246 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT22t_in_insn_format22t2582 = new BitSet(new long[] { 4L });
/* 7247 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t2584 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7248 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22t2588 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7249 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22t2592 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7250 */   public static final BitSet FOLLOW_label_ref_in_insn_format22t2594 = new BitSet(new long[] { 8L });
/* 7251 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT22x_in_insn_format22x2617 = new BitSet(new long[] { 4L });
/* 7252 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x2619 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7253 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22x2623 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7254 */   public static final BitSet FOLLOW_REGISTER_in_insn_format22x2627 = new BitSet(new long[] { 8L });
/* 7255 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT23x_in_insn_format23x2650 = new BitSet(new long[] { 4L });
/* 7256 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x2652 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7257 */   public static final BitSet FOLLOW_REGISTER_in_insn_format23x2656 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7258 */   public static final BitSet FOLLOW_REGISTER_in_insn_format23x2660 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7259 */   public static final BitSet FOLLOW_REGISTER_in_insn_format23x2664 = new BitSet(new long[] { 8L });
/* 7260 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT30t_in_insn_format30t2687 = new BitSet(new long[] { 4L });
/* 7261 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t2689 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7262 */   public static final BitSet FOLLOW_label_ref_in_insn_format30t2691 = new BitSet(new long[] { 8L });
/* 7263 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT31c_in_insn_format31c2714 = new BitSet(new long[] { 4L });
/* 7264 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c2716 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7265 */   public static final BitSet FOLLOW_REGISTER_in_insn_format31c2718 = new BitSet(new long[] { 0L, 0L, 0L, 65536L });
/* 7266 */   public static final BitSet FOLLOW_string_literal_in_insn_format31c2720 = new BitSet(new long[] { 8L });
/* 7267 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT31i_in_insn_format31i2743 = new BitSet(new long[] { 4L });
/* 7268 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31i_in_insn_format31i2745 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7269 */   public static final BitSet FOLLOW_REGISTER_in_insn_format31i2747 = new BitSet(new long[] { 2251799853531136L, 274877906944L, 576460752303423488L, 4096L });
/* 7270 */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_format31i2749 = new BitSet(new long[] { 8L });
/* 7271 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT31t_in_insn_format31t2772 = new BitSet(new long[] { 4L });
/* 7272 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t2774 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7273 */   public static final BitSet FOLLOW_REGISTER_in_insn_format31t2776 = new BitSet(new long[] { 0L, 0L, 0L, 8192L });
/* 7274 */   public static final BitSet FOLLOW_label_ref_in_insn_format31t2778 = new BitSet(new long[] { 8L });
/* 7275 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT32x_in_insn_format32x2801 = new BitSet(new long[] { 4L });
/* 7276 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x2803 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7277 */   public static final BitSet FOLLOW_REGISTER_in_insn_format32x2807 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7278 */   public static final BitSet FOLLOW_REGISTER_in_insn_format32x2811 = new BitSet(new long[] { 8L });
/* 7279 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_METHOD_in_insn_format35c_method2834 = new BitSet(new long[] { 4L });
/* 7280 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method2836 = new BitSet(new long[] { 0L, 0L, 8192L });
/* 7281 */   public static final BitSet FOLLOW_register_list_in_insn_format35c_method2838 = new BitSet(new long[] { 67109120L });
/* 7282 */   public static final BitSet FOLLOW_fully_qualified_method_in_insn_format35c_method2840 = new BitSet(new long[] { 8L });
/* 7283 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT35c_TYPE_in_insn_format35c_type2863 = new BitSet(new long[] { 4L });
/* 7284 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type2865 = new BitSet(new long[] { 0L, 0L, 8192L });
/* 7285 */   public static final BitSet FOLLOW_register_list_in_insn_format35c_type2867 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 7286 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type2869 = new BitSet(new long[] { 8L });
/* 7287 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_METHOD_in_insn_format3rc_method2892 = new BitSet(new long[] { 4L });
/* 7288 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method2894 = new BitSet(new long[] { 0L, 0L, 16384L });
/* 7289 */   public static final BitSet FOLLOW_register_range_in_insn_format3rc_method2896 = new BitSet(new long[] { 67109120L });
/* 7290 */   public static final BitSet FOLLOW_fully_qualified_method_in_insn_format3rc_method2898 = new BitSet(new long[] { 8L });
/* 7291 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT3rc_TYPE_in_insn_format3rc_type2921 = new BitSet(new long[] { 4L });
/* 7292 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type2923 = new BitSet(new long[] { 0L, 0L, 16384L });
/* 7293 */   public static final BitSet FOLLOW_register_range_in_insn_format3rc_type2925 = new BitSet(new long[] { 67109120L, 0L, 0L, 128L });
/* 7294 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type2927 = new BitSet(new long[] { 8L });
/* 7295 */   public static final BitSet FOLLOW_I_STATEMENT_FORMAT51l_in_insn_format51l_type2950 = new BitSet(new long[] { 4L });
/* 7296 */   public static final BitSet FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l_type2952 = new BitSet(new long[] { 0L, 0L, 0L, 512L });
/* 7297 */   public static final BitSet FOLLOW_REGISTER_in_insn_format51l_type2954 = new BitSet(new long[] { 2251817033400320L, 274877906944L, 576460752303423488L, 4096L });
/* 7298 */   public static final BitSet FOLLOW_fixed_64bit_literal_in_insn_format51l_type2956 = new BitSet(new long[] { 8L });
/* 7299 */   public static final BitSet FOLLOW_I_STATEMENT_ARRAY_DATA_in_insn_array_data_directive2979 = new BitSet(new long[] { 4L });
/* 7300 */   public static final BitSet FOLLOW_I_ARRAY_ELEMENT_SIZE_in_insn_array_data_directive2982 = new BitSet(new long[] { 4L });
/* 7301 */   public static final BitSet FOLLOW_short_integral_literal_in_insn_array_data_directive2984 = new BitSet(new long[] { 8L });
/* 7302 */   public static final BitSet FOLLOW_array_elements_in_insn_array_data_directive2987 = new BitSet(new long[] { 8L });
/* 7303 */   public static final BitSet FOLLOW_I_STATEMENT_PACKED_SWITCH_in_insn_packed_switch_directive3009 = new BitSet(new long[] { 4L });
/* 7304 */   public static final BitSet FOLLOW_I_PACKED_SWITCH_START_KEY_in_insn_packed_switch_directive3012 = new BitSet(new long[] { 4L });
/* 7305 */   public static final BitSet FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive3014 = new BitSet(new long[] { 8L });
/* 7306 */   public static final BitSet FOLLOW_packed_switch_elements_in_insn_packed_switch_directive3017 = new BitSet(new long[] { 8L });
/* 7307 */   public static final BitSet FOLLOW_I_STATEMENT_SPARSE_SWITCH_in_insn_sparse_switch_directive3041 = new BitSet(new long[] { 4L });
/* 7308 */   public static final BitSet FOLLOW_sparse_switch_elements_in_insn_sparse_switch_directive3043 = new BitSet(new long[] { 8L });
/* 7309 */   public static final BitSet FOLLOW_set_in_nonvoid_type_descriptor3064 = new BitSet(new long[] { 2L });
/* 7310 */   public static final BitSet FOLLOW_set_in_reference_type_descriptor3096 = new BitSet(new long[] { 2L });
/* 7311 */   public static final BitSet FOLLOW_VOID_TYPE_in_type_descriptor3122 = new BitSet(new long[] { 2L });
/* 7312 */   public static final BitSet FOLLOW_nonvoid_type_descriptor_in_type_descriptor3130 = new BitSet(new long[] { 2L });
/* 7313 */   public static final BitSet FOLLOW_long_literal_in_short_integral_literal3148 = new BitSet(new long[] { 2L });
/* 7314 */   public static final BitSet FOLLOW_integer_literal_in_short_integral_literal3160 = new BitSet(new long[] { 2L });
/* 7315 */   public static final BitSet FOLLOW_short_literal_in_short_integral_literal3172 = new BitSet(new long[] { 2L });
/* 7316 */   public static final BitSet FOLLOW_char_literal_in_short_integral_literal3180 = new BitSet(new long[] { 2L });
/* 7317 */   public static final BitSet FOLLOW_byte_literal_in_short_integral_literal3188 = new BitSet(new long[] { 2L });
/* 7318 */   public static final BitSet FOLLOW_long_literal_in_integral_literal3203 = new BitSet(new long[] { 2L });
/* 7319 */   public static final BitSet FOLLOW_integer_literal_in_integral_literal3215 = new BitSet(new long[] { 2L });
/* 7320 */   public static final BitSet FOLLOW_short_literal_in_integral_literal3223 = new BitSet(new long[] { 2L });
/* 7321 */   public static final BitSet FOLLOW_byte_literal_in_integral_literal3231 = new BitSet(new long[] { 2L });
/* 7322 */   public static final BitSet FOLLOW_INTEGER_LITERAL_in_integer_literal3247 = new BitSet(new long[] { 2L });
/* 7323 */   public static final BitSet FOLLOW_LONG_LITERAL_in_long_literal3262 = new BitSet(new long[] { 2L });
/* 7324 */   public static final BitSet FOLLOW_SHORT_LITERAL_in_short_literal3277 = new BitSet(new long[] { 2L });
/* 7325 */   public static final BitSet FOLLOW_BYTE_LITERAL_in_byte_literal3292 = new BitSet(new long[] { 2L });
/* 7326 */   public static final BitSet FOLLOW_FLOAT_LITERAL_in_float_literal3307 = new BitSet(new long[] { 2L });
/* 7327 */   public static final BitSet FOLLOW_DOUBLE_LITERAL_in_double_literal3322 = new BitSet(new long[] { 2L });
/* 7328 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_char_literal3337 = new BitSet(new long[] { 2L });
/* 7329 */   public static final BitSet FOLLOW_STRING_LITERAL_in_string_literal3352 = new BitSet(new long[] { 2L });
/* 7330 */   public static final BitSet FOLLOW_BOOL_LITERAL_in_bool_literal3371 = new BitSet(new long[] { 2L });
/* 7331 */   public static final BitSet FOLLOW_I_ENCODED_ARRAY_in_array_literal3393 = new BitSet(new long[] { 4L });
/* 7332 */   public static final BitSet FOLLOW_literal_in_array_literal3396 = new BitSet(new long[] { 2251817100509448L, 16888773480546304L, -8642407684923981824L, 1118336L });
/* 7333 */   public static final BitSet FOLLOW_I_ANNOTATIONS_in_annotations3421 = new BitSet(new long[] { 4L });
/* 7334 */   public static final BitSet FOLLOW_annotation_in_annotations3424 = new BitSet(new long[] { 8L, 2199023255552L });
/* 7335 */   public static final BitSet FOLLOW_I_ANNOTATION_in_annotation3453 = new BitSet(new long[] { 4L });
/* 7336 */   public static final BitSet FOLLOW_ANNOTATION_VISIBILITY_in_annotation3455 = new BitSet(new long[] { 0L, 0L, 4503599627370496L });
/* 7337 */   public static final BitSet FOLLOW_subannotation_in_annotation3457 = new BitSet(new long[] { 8L });
/* 7338 */   public static final BitSet FOLLOW_I_ANNOTATION_ELEMENT_in_annotation_element3478 = new BitSet(new long[] { 4L });
/* 7339 */   public static final BitSet FOLLOW_SIMPLE_NAME_in_annotation_element3480 = new BitSet(new long[] { 2251817100509440L, 16888773480546304L, -8642407684923981824L, 1118336L });
/* 7340 */   public static final BitSet FOLLOW_literal_in_annotation_element3482 = new BitSet(new long[] { 8L });
/* 7341 */   public static final BitSet FOLLOW_I_SUBANNOTATION_in_subannotation3509 = new BitSet(new long[] { 4L });
/* 7342 */   public static final BitSet FOLLOW_CLASS_DESCRIPTOR_in_subannotation3519 = new BitSet(new long[] { 8L, 8796093022208L });
/* 7343 */   public static final BitSet FOLLOW_annotation_element_in_subannotation3530 = new BitSet(new long[] { 8L, 8796093022208L });
/* 7344 */   public static final BitSet FOLLOW_I_ENCODED_FIELD_in_field_literal3569 = new BitSet(new long[] { 4L });
/* 7345 */   public static final BitSet FOLLOW_fully_qualified_field_in_field_literal3571 = new BitSet(new long[] { 8L });
/* 7346 */   public static final BitSet FOLLOW_I_ENCODED_METHOD_in_method_literal3592 = new BitSet(new long[] { 4L });
/* 7347 */   public static final BitSet FOLLOW_fully_qualified_method_in_method_literal3594 = new BitSet(new long[] { 8L });
/* 7348 */   public static final BitSet FOLLOW_I_ENCODED_ENUM_in_enum_literal3615 = new BitSet(new long[] { 4L });
/* 7349 */   public static final BitSet FOLLOW_fully_qualified_field_in_enum_literal3617 = new BitSet(new long[] { 8L });
/*      */ 
/*      */   public smaliTreeWalker(TreeNodeStream input)
/*      */   {
/*  333 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public smaliTreeWalker(TreeNodeStream input, RecognizerSharedState state) {
/*  336 */     super(input, state);
/*      */   }
/*      */   public String[] getTokenNames() {
/*  339 */     return tokenNames; } 
/*  340 */   public String getGrammarFileName() { return "/home/jesusfreke/projects/smali/smali/src/main/antlr3/smaliTreeWalker.g";
/*      */   }
/*      */ 
/*      */   public void setDexBuilder(DexBuilder dexBuilder)
/*      */   {
/*  350 */     this.dexBuilder = dexBuilder;
/*      */   }
/*      */ 
/*      */   public void setVerboseErrors(boolean verboseErrors)
/*      */   {
/*  359 */     this.verboseErrors = verboseErrors;
/*      */   }
/*      */ 
/*      */   private byte parseRegister_nibble(String register) throws SemanticException
/*      */   {
/*  364 */     int totalMethodRegisters = ((method_scope)this.method_stack.peek()).totalMethodRegisters;
/*  365 */     int methodParameterRegisters = ((method_scope)this.method_stack.peek()).methodParameterRegisters;
/*      */ 
/*  368 */     int val = Byte.parseByte(register.substring(1));
/*  369 */     if (register.charAt(0) == 'p') {
/*  370 */       val = totalMethodRegisters - methodParameterRegisters + val;
/*      */     }
/*  372 */     if (val >= 32) {
/*  373 */       throw new SemanticException(this.input, "The maximum allowed register in this context is list of registers is v15", new Object[0]);
/*      */     }
/*      */ 
/*  376 */     return (byte)val;
/*      */   }
/*      */ 
/*      */   private short parseRegister_byte(String register)
/*      */     throws SemanticException
/*      */   {
/*  382 */     int totalMethodRegisters = ((method_scope)this.method_stack.peek()).totalMethodRegisters;
/*  383 */     int methodParameterRegisters = ((method_scope)this.method_stack.peek()).methodParameterRegisters;
/*      */ 
/*  385 */     int val = Short.parseShort(register.substring(1));
/*  386 */     if (register.charAt(0) == 'p') {
/*  387 */       val = totalMethodRegisters - methodParameterRegisters + val;
/*      */     }
/*  389 */     if (val >= 512) {
/*  390 */       throw new SemanticException(this.input, "The maximum allowed register in this context is v255", new Object[0]);
/*      */     }
/*  392 */     return (short)val;
/*      */   }
/*      */ 
/*      */   private int parseRegister_short(String register)
/*      */     throws SemanticException
/*      */   {
/*  398 */     int totalMethodRegisters = ((method_scope)this.method_stack.peek()).totalMethodRegisters;
/*  399 */     int methodParameterRegisters = ((method_scope)this.method_stack.peek()).methodParameterRegisters;
/*      */ 
/*  401 */     int val = Integer.parseInt(register.substring(1));
/*  402 */     if (register.charAt(0) == 'p') {
/*  403 */       val = totalMethodRegisters - methodParameterRegisters + val;
/*      */     }
/*  405 */     if (val >= 131072) {
/*  406 */       throw new SemanticException(this.input, "The maximum allowed register in this context is v65535", new Object[0]);
/*      */     }
/*      */ 
/*  409 */     return val;
/*      */   }
/*      */ 
/*      */   public String getErrorMessage(RecognitionException e, String[] tokenNames) {
/*  413 */     if ((e instanceof SemanticException)) {
/*  414 */       return e.getMessage();
/*      */     }
/*  416 */     return super.getErrorMessage(e, tokenNames);
/*      */   }
/*      */ 
/*      */   public String getErrorHeader(RecognitionException e)
/*      */   {
/*  421 */     return getSourceName() + "[" + e.line + "," + e.charPositionInLine + "]";
/*      */   }
/*      */ 
/*      */   public final ClassDef smali_file()
/*      */     throws RecognitionException
/*      */   {
/*  429 */     ClassDef classDef = null;
/*      */ 
/*  432 */     TreeRuleReturnScope header1 = null;
/*  433 */     Set annotations2 = null;
/*  434 */     List fields3 = null;
/*  435 */     List methods4 = null;
/*      */     try
/*      */     {
/*  441 */       match(this.input, 113, FOLLOW_I_CLASS_DEF_in_smali_file52);
/*  442 */       match(this.input, 2, null);
/*  443 */       pushFollow(FOLLOW_header_in_smali_file54);
/*  444 */       header1 = header();
/*  445 */       this.state._fsp -= 1;
/*      */ 
/*  447 */       pushFollow(FOLLOW_methods_in_smali_file56);
/*  448 */       methods4 = methods();
/*  449 */       this.state._fsp -= 1;
/*      */ 
/*  451 */       pushFollow(FOLLOW_fields_in_smali_file58);
/*  452 */       fields3 = fields();
/*  453 */       this.state._fsp -= 1;
/*      */ 
/*  455 */       pushFollow(FOLLOW_annotations_in_smali_file60);
/*  456 */       annotations2 = annotations();
/*  457 */       this.state._fsp -= 1;
/*      */ 
/*  459 */       match(this.input, 3, null);
/*      */ 
/*  462 */       classDef = this.dexBuilder.internClassDef(header1 != null ? ((header_return)header1).classType : null, header1 != null ? ((header_return)header1).accessFlags : 0, header1 != null ? ((header_return)header1).superType : null, header1 != null ? ((header_return)header1).implementsList : null, header1 != null ? ((header_return)header1).sourceSpec : null, annotations2, fields3, methods4);
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  470 */       if (this.verboseErrors) {
/*  471 */         ex.printStackTrace(System.err);
/*      */       }
/*  473 */       reportError(new SemanticException(this.input, ex));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/*  480 */     return classDef;
/*      */   }
/*      */ 
/*      */   public final header_return header()
/*      */     throws RecognitionException
/*      */   {
/*  497 */     header_return retval = new header_return();
/*  498 */     retval.start = this.input.LT(1);
/*      */ 
/*  500 */     TreeRuleReturnScope class_spec5 = null;
/*  501 */     String super_spec6 = null;
/*  502 */     List implements_list7 = null;
/*  503 */     String source_spec8 = null;
/*      */     try
/*      */     {
/*  509 */       pushFollow(FOLLOW_class_spec_in_header85);
/*  510 */       class_spec5 = class_spec();
/*  511 */       this.state._fsp -= 1;
/*      */ 
/*  514 */       int alt1 = 2;
/*  515 */       int LA1_0 = this.input.LA(1);
/*  516 */       if (LA1_0 == 181) {
/*  517 */         alt1 = 1;
/*      */       }
/*  519 */       switch (alt1)
/*      */       {
/*      */       case 1:
/*  523 */         pushFollow(FOLLOW_super_spec_in_header87);
/*  524 */         super_spec6 = super_spec();
/*  525 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  532 */       pushFollow(FOLLOW_implements_list_in_header90);
/*  533 */       implements_list7 = implements_list();
/*  534 */       this.state._fsp -= 1;
/*      */ 
/*  536 */       pushFollow(FOLLOW_source_spec_in_header92);
/*  537 */       source_spec8 = source_spec();
/*  538 */       this.state._fsp -= 1;
/*      */ 
/*  541 */       this.classType = (class_spec5 != null ? ((class_spec_return)class_spec5).type : null);
/*  542 */       retval.classType = this.classType;
/*  543 */       retval.accessFlags = (class_spec5 != null ? ((class_spec_return)class_spec5).accessFlags : 0);
/*  544 */       retval.superType = super_spec6;
/*  545 */       retval.implementsList = implements_list7;
/*  546 */       retval.sourceSpec = source_spec8;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  552 */       reportError(re);
/*  553 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  558 */     return retval;
/*      */   }
/*      */ 
/*      */   public final class_spec_return class_spec()
/*      */     throws RecognitionException
/*      */   {
/*  572 */     class_spec_return retval = new class_spec_return();
/*  573 */     retval.start = this.input.LT(1);
/*      */ 
/*  575 */     CommonTree CLASS_DESCRIPTOR9 = null;
/*  576 */     int access_list10 = 0;
/*      */     try
/*      */     {
/*  582 */       CLASS_DESCRIPTOR9 = (CommonTree)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_class_spec110);
/*  583 */       pushFollow(FOLLOW_access_list_in_class_spec112);
/*  584 */       access_list10 = access_list();
/*  585 */       this.state._fsp -= 1;
/*      */ 
/*  588 */       retval.type = (CLASS_DESCRIPTOR9 != null ? CLASS_DESCRIPTOR9.getText() : null);
/*  589 */       retval.accessFlags = access_list10;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  595 */       reportError(re);
/*  596 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  601 */     return retval;
/*      */   }
/*      */ 
/*      */   public final String super_spec()
/*      */     throws RecognitionException
/*      */   {
/*  610 */     String type = null;
/*      */ 
/*  613 */     CommonTree CLASS_DESCRIPTOR11 = null;
/*      */     try
/*      */     {
/*  619 */       match(this.input, 181, FOLLOW_I_SUPER_in_super_spec130);
/*  620 */       match(this.input, 2, null);
/*  621 */       CLASS_DESCRIPTOR11 = (CommonTree)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_super_spec132);
/*  622 */       match(this.input, 3, null);
/*      */ 
/*  625 */       type = CLASS_DESCRIPTOR11 != null ? CLASS_DESCRIPTOR11.getText() : null;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  631 */       reportError(re);
/*  632 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  637 */     return type;
/*      */   }
/*      */ 
/*      */   public final String implements_spec()
/*      */     throws RecognitionException
/*      */   {
/*  646 */     String type = null;
/*      */ 
/*  649 */     CommonTree CLASS_DESCRIPTOR12 = null;
/*      */     try
/*      */     {
/*  655 */       match(this.input, 124, FOLLOW_I_IMPLEMENTS_in_implements_spec152);
/*  656 */       match(this.input, 2, null);
/*  657 */       CLASS_DESCRIPTOR12 = (CommonTree)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_implements_spec154);
/*  658 */       match(this.input, 3, null);
/*      */ 
/*  661 */       type = CLASS_DESCRIPTOR12 != null ? CLASS_DESCRIPTOR12.getText() : null;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  667 */       reportError(re);
/*  668 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  673 */     return type;
/*      */   }
/*      */ 
/*      */   public final List<String> implements_list()
/*      */     throws RecognitionException
/*      */   {
/*  682 */     List implementsList = null;
/*      */ 
/*  685 */     String implements_spec13 = null;
/*      */     try
/*      */     {
/*  692 */       List typeList = Lists.newArrayList();
/*      */       while (true)
/*      */       {
/*  696 */         int alt2 = 2;
/*  697 */         int LA2_0 = this.input.LA(1);
/*  698 */         if (LA2_0 == 124) {
/*  699 */           alt2 = 1;
/*      */         }
/*      */ 
/*  702 */         switch (alt2)
/*      */         {
/*      */         case 1:
/*  706 */           pushFollow(FOLLOW_implements_spec_in_implements_list184);
/*  707 */           implements_spec13 = implements_spec();
/*  708 */           this.state._fsp -= 1;
/*      */ 
/*  710 */           typeList.add(implements_spec13);
/*      */ 
/*  712 */           break;
/*      */         default:
/*  715 */           break label94;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  720 */       label94: if (typeList.size() > 0)
/*  721 */         implementsList = typeList;
/*      */       else {
/*  723 */         implementsList = null;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  730 */       reportError(re);
/*  731 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  736 */     return implementsList;
/*      */   }
/*      */ 
/*      */   public final String source_spec()
/*      */     throws RecognitionException
/*      */   {
/*  745 */     String source = null;
/*      */ 
/*  748 */     String string_literal14 = null;
/*      */     try
/*      */     {
/*  752 */       int alt3 = 2;
/*  753 */       int LA3_0 = this.input.LA(1);
/*  754 */       if (LA3_0 == 144) {
/*  755 */         alt3 = 1;
/*      */       }
/*  757 */       else if (LA3_0 == 130) {
/*  758 */         alt3 = 2;
/*      */       }
/*      */       else
/*      */       {
/*  762 */         NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
/*      */         throw nvae;
/*      */       }
/*      */ 
/*  767 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  771 */         source = null;
/*  772 */         match(this.input, 144, FOLLOW_I_SOURCE_in_source_spec213);
/*  773 */         match(this.input, 2, null);
/*  774 */         pushFollow(FOLLOW_string_literal_in_source_spec215);
/*  775 */         string_literal14 = string_literal();
/*  776 */         this.state._fsp -= 1;
/*      */ 
/*  778 */         source = string_literal14;
/*  779 */         match(this.input, 3, null);
/*      */       case 2:
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  792 */       reportError(re);
/*  793 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  798 */     return source;
/*      */   }
/*      */ 
/*      */   public final int access_list()
/*      */     throws RecognitionException
/*      */   {
/*  807 */     int value = 0;
/*      */ 
/*  810 */     CommonTree ACCESS_SPEC15 = null;
/*      */ 
/*  813 */     value = 0;
/*      */     try
/*      */     {
/*  819 */       match(this.input, 104, FOLLOW_I_ACCESS_LIST_in_access_list248);
/*  820 */       if (this.input.LA(1) == 2) {
/*  821 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/*  825 */           int alt4 = 2;
/*  826 */           int LA4_0 = this.input.LA(1);
/*  827 */           if (LA4_0 == 4) {
/*  828 */             alt4 = 1;
/*      */           }
/*      */ 
/*  831 */           switch (alt4)
/*      */           {
/*      */           case 1:
/*  835 */             ACCESS_SPEC15 = (CommonTree)match(this.input, 4, FOLLOW_ACCESS_SPEC_in_access_list266);
/*      */ 
/*  837 */             value |= AccessFlags.getAccessFlag(ACCESS_SPEC15.getText()).getValue();
/*      */ 
/*  840 */             break;
/*      */           default:
/*  843 */             break label126;
/*      */           }
/*      */         }
/*      */ 
/*  847 */         label126: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  854 */       reportError(re);
/*  855 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  860 */     return value;
/*      */   }
/*      */ 
/*      */   public final List<BuilderField> fields()
/*      */     throws RecognitionException
/*      */   {
/*  869 */     List fields = null;
/*      */ 
/*  872 */     BuilderField field16 = null;
/*      */ 
/*  874 */     fields = Lists.newArrayList();
/*      */     try
/*      */     {
/*  879 */       match(this.input, 121, FOLLOW_I_FIELDS_in_fields308);
/*  880 */       if (this.input.LA(1) == 2) {
/*  881 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/*  885 */           int alt5 = 2;
/*  886 */           int LA5_0 = this.input.LA(1);
/*  887 */           if (LA5_0 == 120) {
/*  888 */             alt5 = 1;
/*      */           }
/*      */ 
/*  891 */           switch (alt5)
/*      */           {
/*      */           case 1:
/*  895 */             pushFollow(FOLLOW_field_in_fields317);
/*  896 */             field16 = field();
/*  897 */             this.state._fsp -= 1;
/*      */ 
/*  900 */             fields.add(field16);
/*      */ 
/*  903 */             break;
/*      */           default:
/*  906 */             break label130;
/*      */           }
/*      */         }
/*      */ 
/*  910 */         label130: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  917 */       reportError(re);
/*  918 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  923 */     return fields;
/*      */   }
/*      */ 
/*      */   public final List<BuilderMethod> methods()
/*      */     throws RecognitionException
/*      */   {
/*  932 */     List methods = null;
/*      */ 
/*  935 */     BuilderMethod method17 = null;
/*      */ 
/*  937 */     methods = Lists.newArrayList();
/*      */     try
/*      */     {
/*  942 */       match(this.input, 130, FOLLOW_I_METHODS_in_methods349);
/*  943 */       if (this.input.LA(1) == 2) {
/*  944 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/*  948 */           int alt6 = 2;
/*  949 */           int LA6_0 = this.input.LA(1);
/*  950 */           if (LA6_0 == 129) {
/*  951 */             alt6 = 1;
/*      */           }
/*      */ 
/*  954 */           switch (alt6)
/*      */           {
/*      */           case 1:
/*  958 */             pushFollow(FOLLOW_method_in_methods358);
/*  959 */             method17 = method();
/*  960 */             this.state._fsp -= 1;
/*      */ 
/*  963 */             methods.add(method17);
/*      */ 
/*  966 */             break;
/*      */           default:
/*  969 */             break label134;
/*      */           }
/*      */         }
/*      */ 
/*  973 */         label134: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  980 */       reportError(re);
/*  981 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  986 */     return methods;
/*      */   }
/*      */ 
/*      */   public final BuilderField field()
/*      */     throws RecognitionException
/*      */   {
/*  995 */     BuilderField field = null;
/*      */ 
/*  998 */     CommonTree SIMPLE_NAME20 = null;
/*  999 */     int access_list18 = 0;
/* 1000 */     EncodedValue field_initial_value19 = null;
/* 1001 */     TreeRuleReturnScope nonvoid_type_descriptor21 = null;
/* 1002 */     Set annotations22 = null;
/*      */     try
/*      */     {
/* 1008 */       match(this.input, 120, FOLLOW_I_FIELD_in_field383);
/* 1009 */       match(this.input, 2, null);
/* 1010 */       SIMPLE_NAME20 = (CommonTree)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_field385);
/* 1011 */       pushFollow(FOLLOW_access_list_in_field387);
/* 1012 */       access_list18 = access_list();
/* 1013 */       this.state._fsp -= 1;
/*      */ 
/* 1015 */       match(this.input, 123, FOLLOW_I_FIELD_TYPE_in_field390);
/* 1016 */       match(this.input, 2, null);
/* 1017 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_field392);
/* 1018 */       nonvoid_type_descriptor21 = nonvoid_type_descriptor();
/* 1019 */       this.state._fsp -= 1;
/*      */ 
/* 1021 */       match(this.input, 3, null);
/*      */ 
/* 1023 */       pushFollow(FOLLOW_field_initial_value_in_field395);
/* 1024 */       field_initial_value19 = field_initial_value();
/* 1025 */       this.state._fsp -= 1;
/*      */ 
/* 1028 */       int alt7 = 2;
/* 1029 */       int LA7_0 = this.input.LA(1);
/* 1030 */       if (LA7_0 == 106) {
/* 1031 */         alt7 = 1;
/*      */       }
/* 1033 */       switch (alt7)
/*      */       {
/*      */       case 1:
/* 1037 */         pushFollow(FOLLOW_annotations_in_field397);
/* 1038 */         annotations22 = annotations();
/* 1039 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/* 1046 */       match(this.input, 3, null);
/*      */ 
/* 1049 */       int accessFlags = access_list18;
/*      */ 
/* 1052 */       if ((!AccessFlags.STATIC.isSet(accessFlags)) && (field_initial_value19 != null)) {
/*      */         throw new SemanticException(this.input, "Initial field values can only be specified for static fields.", new Object[0]);
/*      */       }
/*      */ 
/* 1056 */       field = this.dexBuilder.internField(this.classType, SIMPLE_NAME20 != null ? SIMPLE_NAME20.getText() : null, nonvoid_type_descriptor21 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor21).type : null, access_list18, field_initial_value19, annotations22);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1063 */       reportError(re);
/* 1064 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1069 */     return field;
/*      */   }
/*      */ 
/*      */   public final EncodedValue field_initial_value()
/*      */     throws RecognitionException
/*      */   {
/* 1078 */     EncodedValue encodedValue = null;
/*      */ 
/* 1081 */     EncodedValue literal23 = null;
/*      */     try
/*      */     {
/* 1085 */       int alt8 = 2;
/* 1086 */       int LA8_0 = this.input.LA(1);
/* 1087 */       if (LA8_0 == 122) {
/* 1088 */         alt8 = 1;
/*      */       }
/* 1090 */       else if ((LA8_0 == 3) || (LA8_0 == 106)) {
/* 1091 */         alt8 = 2;
/*      */       }
/*      */       else
/*      */       {
/* 1095 */         NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
/*      */         throw nvae;
/*      */       }
/*      */ 
/* 1100 */       switch (alt8)
/*      */       {
/*      */       case 1:
/* 1104 */         match(this.input, 122, FOLLOW_I_FIELD_INITIAL_VALUE_in_field_initial_value418);
/* 1105 */         match(this.input, 2, null);
/* 1106 */         pushFollow(FOLLOW_literal_in_field_initial_value420);
/* 1107 */         literal23 = literal();
/* 1108 */         this.state._fsp -= 1;
/*      */ 
/* 1110 */         match(this.input, 3, null);
/*      */ 
/* 1112 */         encodedValue = literal23;
/*      */       case 2:
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1124 */       reportError(re);
/* 1125 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1130 */     return encodedValue;
/*      */   }
/*      */ 
/*      */   public final EncodedValue literal()
/*      */     throws RecognitionException
/*      */   {
/* 1139 */     EncodedValue encodedValue = null;
/*      */ 
/* 1142 */     int integer_literal24 = 0;
/* 1143 */     long long_literal25 = 0L;
/* 1144 */     short short_literal26 = 0;
/* 1145 */     byte byte_literal27 = 0;
/* 1146 */     float float_literal28 = 0.0F;
/* 1147 */     double double_literal29 = 0.0D;
/* 1148 */     char char_literal30 = '\000';
/* 1149 */     String string_literal31 = null;
/* 1150 */     boolean bool_literal32 = false;
/* 1151 */     String type_descriptor33 = null;
/* 1152 */     List array_literal34 = null;
/* 1153 */     TreeRuleReturnScope subannotation35 = null;
/* 1154 */     FieldReference field_literal36 = null;
/* 1155 */     MethodReference method_literal37 = null;
/* 1156 */     FieldReference enum_literal38 = null;
/*      */     try
/*      */     {
/* 1160 */       int alt9 = 16;
/* 1161 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 102:
/* 1164 */         alt9 = 1;
/*      */ 
/* 1166 */         break;
/*      */       case 187:
/* 1169 */         alt9 = 2;
/*      */ 
/* 1171 */         break;
/*      */       case 204:
/* 1174 */         alt9 = 3;
/*      */ 
/* 1176 */         break;
/*      */       case 22:
/* 1179 */         alt9 = 4;
/*      */ 
/* 1181 */         break;
/*      */       case 51:
/* 1184 */         alt9 = 5;
/*      */ 
/* 1186 */         break;
/*      */       case 34:
/* 1189 */         alt9 = 6;
/*      */ 
/* 1191 */         break;
/*      */       case 25:
/* 1194 */         alt9 = 7;
/*      */ 
/* 1196 */         break;
/*      */       case 208:
/* 1199 */         alt9 = 8;
/*      */ 
/* 1201 */         break;
/*      */       case 21:
/* 1204 */         alt9 = 9;
/*      */ 
/* 1206 */         break;
/*      */       case 191:
/* 1209 */         alt9 = 10;
/*      */ 
/* 1211 */         break;
/*      */       case 8:
/*      */       case 26:
/*      */       case 199:
/*      */       case 212:
/* 1217 */         alt9 = 11;
/*      */ 
/* 1219 */         break;
/*      */       case 114:
/* 1222 */         alt9 = 12;
/*      */ 
/* 1224 */         break;
/*      */       case 180:
/* 1227 */         alt9 = 13;
/*      */ 
/* 1229 */         break;
/*      */       case 116:
/* 1232 */         alt9 = 14;
/*      */ 
/* 1234 */         break;
/*      */       case 117:
/* 1237 */         alt9 = 15;
/*      */ 
/* 1239 */         break;
/*      */       case 115:
/* 1242 */         alt9 = 16;
/*      */ 
/* 1244 */         break;
/*      */       default:
/* 1246 */         NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 1250 */       switch (alt9)
/*      */       {
/*      */       case 1:
/* 1254 */         pushFollow(FOLLOW_integer_literal_in_literal442);
/* 1255 */         integer_literal24 = integer_literal();
/* 1256 */         this.state._fsp -= 1;
/*      */ 
/* 1258 */         encodedValue = new ImmutableIntEncodedValue(integer_literal24);
/*      */ 
/* 1260 */         break;
/*      */       case 2:
/* 1264 */         pushFollow(FOLLOW_long_literal_in_literal450);
/* 1265 */         long_literal25 = long_literal();
/* 1266 */         this.state._fsp -= 1;
/*      */ 
/* 1268 */         encodedValue = new ImmutableLongEncodedValue(long_literal25);
/*      */ 
/* 1270 */         break;
/*      */       case 3:
/* 1274 */         pushFollow(FOLLOW_short_literal_in_literal458);
/* 1275 */         short_literal26 = short_literal();
/* 1276 */         this.state._fsp -= 1;
/*      */ 
/* 1278 */         encodedValue = new ImmutableShortEncodedValue(short_literal26);
/*      */ 
/* 1280 */         break;
/*      */       case 4:
/* 1284 */         pushFollow(FOLLOW_byte_literal_in_literal466);
/* 1285 */         byte_literal27 = byte_literal();
/* 1286 */         this.state._fsp -= 1;
/*      */ 
/* 1288 */         encodedValue = new ImmutableByteEncodedValue(byte_literal27);
/*      */ 
/* 1290 */         break;
/*      */       case 5:
/* 1294 */         pushFollow(FOLLOW_float_literal_in_literal474);
/* 1295 */         float_literal28 = float_literal();
/* 1296 */         this.state._fsp -= 1;
/*      */ 
/* 1298 */         encodedValue = new ImmutableFloatEncodedValue(float_literal28);
/*      */ 
/* 1300 */         break;
/*      */       case 6:
/* 1304 */         pushFollow(FOLLOW_double_literal_in_literal482);
/* 1305 */         double_literal29 = double_literal();
/* 1306 */         this.state._fsp -= 1;
/*      */ 
/* 1308 */         encodedValue = new ImmutableDoubleEncodedValue(double_literal29);
/*      */ 
/* 1310 */         break;
/*      */       case 7:
/* 1314 */         pushFollow(FOLLOW_char_literal_in_literal490);
/* 1315 */         char_literal30 = char_literal();
/* 1316 */         this.state._fsp -= 1;
/*      */ 
/* 1318 */         encodedValue = new ImmutableCharEncodedValue(char_literal30);
/*      */ 
/* 1320 */         break;
/*      */       case 8:
/* 1324 */         pushFollow(FOLLOW_string_literal_in_literal498);
/* 1325 */         string_literal31 = string_literal();
/* 1326 */         this.state._fsp -= 1;
/*      */ 
/* 1328 */         encodedValue = new ImmutableStringEncodedValue(string_literal31);
/*      */ 
/* 1330 */         break;
/*      */       case 9:
/* 1334 */         pushFollow(FOLLOW_bool_literal_in_literal506);
/* 1335 */         bool_literal32 = bool_literal();
/* 1336 */         this.state._fsp -= 1;
/*      */ 
/* 1338 */         encodedValue = ImmutableBooleanEncodedValue.forBoolean(bool_literal32);
/*      */ 
/* 1340 */         break;
/*      */       case 10:
/* 1344 */         match(this.input, 191, FOLLOW_NULL_LITERAL_in_literal514);
/* 1345 */         encodedValue = ImmutableNullEncodedValue.INSTANCE;
/*      */ 
/* 1347 */         break;
/*      */       case 11:
/* 1351 */         pushFollow(FOLLOW_type_descriptor_in_literal522);
/* 1352 */         type_descriptor33 = type_descriptor();
/* 1353 */         this.state._fsp -= 1;
/*      */ 
/* 1355 */         encodedValue = new ImmutableTypeEncodedValue(type_descriptor33);
/*      */ 
/* 1357 */         break;
/*      */       case 12:
/* 1361 */         pushFollow(FOLLOW_array_literal_in_literal530);
/* 1362 */         array_literal34 = array_literal();
/* 1363 */         this.state._fsp -= 1;
/*      */ 
/* 1365 */         encodedValue = new ImmutableArrayEncodedValue(array_literal34);
/*      */ 
/* 1367 */         break;
/*      */       case 13:
/* 1371 */         pushFollow(FOLLOW_subannotation_in_literal538);
/* 1372 */         subannotation35 = subannotation();
/* 1373 */         this.state._fsp -= 1;
/*      */ 
/* 1375 */         encodedValue = new ImmutableAnnotationEncodedValue(subannotation35 != null ? ((subannotation_return)subannotation35).annotationType : null, subannotation35 != null ? ((subannotation_return)subannotation35).elements : null);
/*      */ 
/* 1377 */         break;
/*      */       case 14:
/* 1381 */         pushFollow(FOLLOW_field_literal_in_literal546);
/* 1382 */         field_literal36 = field_literal();
/* 1383 */         this.state._fsp -= 1;
/*      */ 
/* 1385 */         encodedValue = new ImmutableFieldEncodedValue(field_literal36);
/*      */ 
/* 1387 */         break;
/*      */       case 15:
/* 1391 */         pushFollow(FOLLOW_method_literal_in_literal554);
/* 1392 */         method_literal37 = method_literal();
/* 1393 */         this.state._fsp -= 1;
/*      */ 
/* 1395 */         encodedValue = new ImmutableMethodEncodedValue(method_literal37);
/*      */ 
/* 1397 */         break;
/*      */       case 16:
/* 1401 */         pushFollow(FOLLOW_enum_literal_in_literal562);
/* 1402 */         enum_literal38 = enum_literal();
/* 1403 */         this.state._fsp -= 1;
/*      */ 
/* 1405 */         encodedValue = new ImmutableEnumEncodedValue(enum_literal38);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1412 */       reportError(re);
/* 1413 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1418 */     return encodedValue;
/*      */   }
/*      */ 
/*      */   public final Number fixed_64bit_literal_number()
/*      */     throws RecognitionException
/*      */   {
/* 1427 */     Number value = null;
/*      */ 
/* 1430 */     int integer_literal39 = 0;
/* 1431 */     long long_literal40 = 0L;
/* 1432 */     short short_literal41 = 0;
/* 1433 */     byte byte_literal42 = 0;
/* 1434 */     float float_literal43 = 0.0F;
/* 1435 */     double double_literal44 = 0.0D;
/* 1436 */     char char_literal45 = '\000';
/* 1437 */     boolean bool_literal46 = false;
/*      */     try
/*      */     {
/* 1441 */       int alt10 = 8;
/* 1442 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 102:
/* 1445 */         alt10 = 1;
/*      */ 
/* 1447 */         break;
/*      */       case 187:
/* 1450 */         alt10 = 2;
/*      */ 
/* 1452 */         break;
/*      */       case 204:
/* 1455 */         alt10 = 3;
/*      */ 
/* 1457 */         break;
/*      */       case 22:
/* 1460 */         alt10 = 4;
/*      */ 
/* 1462 */         break;
/*      */       case 51:
/* 1465 */         alt10 = 5;
/*      */ 
/* 1467 */         break;
/*      */       case 34:
/* 1470 */         alt10 = 6;
/*      */ 
/* 1472 */         break;
/*      */       case 25:
/* 1475 */         alt10 = 7;
/*      */ 
/* 1477 */         break;
/*      */       case 21:
/* 1480 */         alt10 = 8;
/*      */ 
/* 1482 */         break;
/*      */       default:
/* 1484 */         NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 1488 */       switch (alt10)
/*      */       {
/*      */       case 1:
/* 1492 */         pushFollow(FOLLOW_integer_literal_in_fixed_64bit_literal_number578);
/* 1493 */         integer_literal39 = integer_literal();
/* 1494 */         this.state._fsp -= 1;
/*      */ 
/* 1496 */         value = Integer.valueOf(integer_literal39);
/*      */ 
/* 1498 */         break;
/*      */       case 2:
/* 1502 */         pushFollow(FOLLOW_long_literal_in_fixed_64bit_literal_number586);
/* 1503 */         long_literal40 = long_literal();
/* 1504 */         this.state._fsp -= 1;
/*      */ 
/* 1506 */         value = Long.valueOf(long_literal40);
/*      */ 
/* 1508 */         break;
/*      */       case 3:
/* 1512 */         pushFollow(FOLLOW_short_literal_in_fixed_64bit_literal_number594);
/* 1513 */         short_literal41 = short_literal();
/* 1514 */         this.state._fsp -= 1;
/*      */ 
/* 1516 */         value = Short.valueOf(short_literal41);
/*      */ 
/* 1518 */         break;
/*      */       case 4:
/* 1522 */         pushFollow(FOLLOW_byte_literal_in_fixed_64bit_literal_number602);
/* 1523 */         byte_literal42 = byte_literal();
/* 1524 */         this.state._fsp -= 1;
/*      */ 
/* 1526 */         value = Byte.valueOf(byte_literal42);
/*      */ 
/* 1528 */         break;
/*      */       case 5:
/* 1532 */         pushFollow(FOLLOW_float_literal_in_fixed_64bit_literal_number610);
/* 1533 */         float_literal43 = float_literal();
/* 1534 */         this.state._fsp -= 1;
/*      */ 
/* 1536 */         value = Integer.valueOf(Float.floatToRawIntBits(float_literal43));
/*      */ 
/* 1538 */         break;
/*      */       case 6:
/* 1542 */         pushFollow(FOLLOW_double_literal_in_fixed_64bit_literal_number618);
/* 1543 */         double_literal44 = double_literal();
/* 1544 */         this.state._fsp -= 1;
/*      */ 
/* 1546 */         value = Long.valueOf(Double.doubleToRawLongBits(double_literal44));
/*      */ 
/* 1548 */         break;
/*      */       case 7:
/* 1552 */         pushFollow(FOLLOW_char_literal_in_fixed_64bit_literal_number626);
/* 1553 */         char_literal45 = char_literal();
/* 1554 */         this.state._fsp -= 1;
/*      */ 
/* 1556 */         value = Integer.valueOf(char_literal45);
/*      */ 
/* 1558 */         break;
/*      */       case 8:
/* 1562 */         pushFollow(FOLLOW_bool_literal_in_fixed_64bit_literal_number634);
/* 1563 */         bool_literal46 = bool_literal();
/* 1564 */         this.state._fsp -= 1;
/*      */ 
/* 1566 */         value = Integer.valueOf(bool_literal46 ? 1 : 0);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1573 */       reportError(re);
/* 1574 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1579 */     return value;
/*      */   }
/*      */ 
/*      */   public final long fixed_64bit_literal()
/*      */     throws RecognitionException
/*      */   {
/* 1588 */     long value = 0L;
/*      */ 
/* 1591 */     int integer_literal47 = 0;
/* 1592 */     long long_literal48 = 0L;
/* 1593 */     short short_literal49 = 0;
/* 1594 */     byte byte_literal50 = 0;
/* 1595 */     float float_literal51 = 0.0F;
/* 1596 */     double double_literal52 = 0.0D;
/* 1597 */     char char_literal53 = '\000';
/* 1598 */     boolean bool_literal54 = false;
/*      */     try
/*      */     {
/* 1602 */       int alt11 = 8;
/* 1603 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 102:
/* 1606 */         alt11 = 1;
/*      */ 
/* 1608 */         break;
/*      */       case 187:
/* 1611 */         alt11 = 2;
/*      */ 
/* 1613 */         break;
/*      */       case 204:
/* 1616 */         alt11 = 3;
/*      */ 
/* 1618 */         break;
/*      */       case 22:
/* 1621 */         alt11 = 4;
/*      */ 
/* 1623 */         break;
/*      */       case 51:
/* 1626 */         alt11 = 5;
/*      */ 
/* 1628 */         break;
/*      */       case 34:
/* 1631 */         alt11 = 6;
/*      */ 
/* 1633 */         break;
/*      */       case 25:
/* 1636 */         alt11 = 7;
/*      */ 
/* 1638 */         break;
/*      */       case 21:
/* 1641 */         alt11 = 8;
/*      */ 
/* 1643 */         break;
/*      */       default:
/* 1645 */         NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 1649 */       switch (alt11)
/*      */       {
/*      */       case 1:
/* 1653 */         pushFollow(FOLLOW_integer_literal_in_fixed_64bit_literal649);
/* 1654 */         integer_literal47 = integer_literal();
/* 1655 */         this.state._fsp -= 1;
/*      */ 
/* 1657 */         value = integer_literal47;
/*      */ 
/* 1659 */         break;
/*      */       case 2:
/* 1663 */         pushFollow(FOLLOW_long_literal_in_fixed_64bit_literal657);
/* 1664 */         long_literal48 = long_literal();
/* 1665 */         this.state._fsp -= 1;
/*      */ 
/* 1667 */         value = long_literal48;
/*      */ 
/* 1669 */         break;
/*      */       case 3:
/* 1673 */         pushFollow(FOLLOW_short_literal_in_fixed_64bit_literal665);
/* 1674 */         short_literal49 = short_literal();
/* 1675 */         this.state._fsp -= 1;
/*      */ 
/* 1677 */         value = short_literal49;
/*      */ 
/* 1679 */         break;
/*      */       case 4:
/* 1683 */         pushFollow(FOLLOW_byte_literal_in_fixed_64bit_literal673);
/* 1684 */         byte_literal50 = byte_literal();
/* 1685 */         this.state._fsp -= 1;
/*      */ 
/* 1687 */         value = byte_literal50;
/*      */ 
/* 1689 */         break;
/*      */       case 5:
/* 1693 */         pushFollow(FOLLOW_float_literal_in_fixed_64bit_literal681);
/* 1694 */         float_literal51 = float_literal();
/* 1695 */         this.state._fsp -= 1;
/*      */ 
/* 1697 */         value = Float.floatToRawIntBits(float_literal51);
/*      */ 
/* 1699 */         break;
/*      */       case 6:
/* 1703 */         pushFollow(FOLLOW_double_literal_in_fixed_64bit_literal689);
/* 1704 */         double_literal52 = double_literal();
/* 1705 */         this.state._fsp -= 1;
/*      */ 
/* 1707 */         value = Double.doubleToRawLongBits(double_literal52);
/*      */ 
/* 1709 */         break;
/*      */       case 7:
/* 1713 */         pushFollow(FOLLOW_char_literal_in_fixed_64bit_literal697);
/* 1714 */         char_literal53 = char_literal();
/* 1715 */         this.state._fsp -= 1;
/*      */ 
/* 1717 */         value = char_literal53;
/*      */ 
/* 1719 */         break;
/*      */       case 8:
/* 1723 */         pushFollow(FOLLOW_bool_literal_in_fixed_64bit_literal705);
/* 1724 */         bool_literal54 = bool_literal();
/* 1725 */         this.state._fsp -= 1;
/*      */ 
/* 1727 */         value = bool_literal54 ? 1L : 0L;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1734 */       reportError(re);
/* 1735 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1740 */     return value;
/*      */   }
/*      */ 
/*      */   public final int fixed_32bit_literal()
/*      */     throws RecognitionException
/*      */   {
/* 1749 */     int value = 0;
/*      */ 
/* 1752 */     int integer_literal55 = 0;
/* 1753 */     long long_literal56 = 0L;
/* 1754 */     short short_literal57 = 0;
/* 1755 */     byte byte_literal58 = 0;
/* 1756 */     float float_literal59 = 0.0F;
/* 1757 */     char char_literal60 = '\000';
/* 1758 */     boolean bool_literal61 = false;
/*      */     try
/*      */     {
/* 1762 */       int alt12 = 7;
/* 1763 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 102:
/* 1766 */         alt12 = 1;
/*      */ 
/* 1768 */         break;
/*      */       case 187:
/* 1771 */         alt12 = 2;
/*      */ 
/* 1773 */         break;
/*      */       case 204:
/* 1776 */         alt12 = 3;
/*      */ 
/* 1778 */         break;
/*      */       case 22:
/* 1781 */         alt12 = 4;
/*      */ 
/* 1783 */         break;
/*      */       case 51:
/* 1786 */         alt12 = 5;
/*      */ 
/* 1788 */         break;
/*      */       case 25:
/* 1791 */         alt12 = 6;
/*      */ 
/* 1793 */         break;
/*      */       case 21:
/* 1796 */         alt12 = 7;
/*      */ 
/* 1798 */         break;
/*      */       default:
/* 1800 */         NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 1804 */       switch (alt12)
/*      */       {
/*      */       case 1:
/* 1808 */         pushFollow(FOLLOW_integer_literal_in_fixed_32bit_literal722);
/* 1809 */         integer_literal55 = integer_literal();
/* 1810 */         this.state._fsp -= 1;
/*      */ 
/* 1812 */         value = integer_literal55;
/*      */ 
/* 1814 */         break;
/*      */       case 2:
/* 1818 */         pushFollow(FOLLOW_long_literal_in_fixed_32bit_literal730);
/* 1819 */         long_literal56 = long_literal();
/* 1820 */         this.state._fsp -= 1;
/*      */ 
/* 1822 */         LiteralTools.checkInt(long_literal56); value = (int)long_literal56;
/*      */ 
/* 1824 */         break;
/*      */       case 3:
/* 1828 */         pushFollow(FOLLOW_short_literal_in_fixed_32bit_literal738);
/* 1829 */         short_literal57 = short_literal();
/* 1830 */         this.state._fsp -= 1;
/*      */ 
/* 1832 */         value = short_literal57;
/*      */ 
/* 1834 */         break;
/*      */       case 4:
/* 1838 */         pushFollow(FOLLOW_byte_literal_in_fixed_32bit_literal746);
/* 1839 */         byte_literal58 = byte_literal();
/* 1840 */         this.state._fsp -= 1;
/*      */ 
/* 1842 */         value = byte_literal58;
/*      */ 
/* 1844 */         break;
/*      */       case 5:
/* 1848 */         pushFollow(FOLLOW_float_literal_in_fixed_32bit_literal754);
/* 1849 */         float_literal59 = float_literal();
/* 1850 */         this.state._fsp -= 1;
/*      */ 
/* 1852 */         value = Float.floatToRawIntBits(float_literal59);
/*      */ 
/* 1854 */         break;
/*      */       case 6:
/* 1858 */         pushFollow(FOLLOW_char_literal_in_fixed_32bit_literal762);
/* 1859 */         char_literal60 = char_literal();
/* 1860 */         this.state._fsp -= 1;
/*      */ 
/* 1862 */         value = char_literal60;
/*      */ 
/* 1864 */         break;
/*      */       case 7:
/* 1868 */         pushFollow(FOLLOW_bool_literal_in_fixed_32bit_literal770);
/* 1869 */         bool_literal61 = bool_literal();
/* 1870 */         this.state._fsp -= 1;
/*      */ 
/* 1872 */         value = bool_literal61 ? 1 : 0;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1879 */       reportError(re);
/* 1880 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1885 */     return value;
/*      */   }
/*      */ 
/*      */   public final List<Number> array_elements()
/*      */     throws RecognitionException
/*      */   {
/* 1894 */     List elements = null;
/*      */ 
/* 1897 */     Number fixed_64bit_literal_number62 = null;
/*      */     try
/*      */     {
/* 1903 */       elements = Lists.newArrayList();
/* 1904 */       match(this.input, 108, FOLLOW_I_ARRAY_ELEMENTS_in_array_elements792);
/* 1905 */       if (this.input.LA(1) == 2) {
/* 1906 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 1910 */           int alt13 = 2;
/* 1911 */           int LA13_0 = this.input.LA(1);
/* 1912 */           if (((LA13_0 >= 21) && (LA13_0 <= 22)) || (LA13_0 == 25) || (LA13_0 == 34) || (LA13_0 == 51) || (LA13_0 == 102) || (LA13_0 == 187) || (LA13_0 == 204)) {
/* 1913 */             alt13 = 1;
/*      */           }
/*      */ 
/* 1916 */           switch (alt13)
/*      */           {
/*      */           case 1:
/* 1920 */             pushFollow(FOLLOW_fixed_64bit_literal_number_in_array_elements801);
/* 1921 */             fixed_64bit_literal_number62 = fixed_64bit_literal_number();
/* 1922 */             this.state._fsp -= 1;
/*      */ 
/* 1925 */             elements.add(fixed_64bit_literal_number62);
/*      */ 
/* 1928 */             break;
/*      */           default:
/* 1931 */             break label182;
/*      */           }
/*      */         }
/*      */ 
/* 1935 */         label182: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1942 */       reportError(re);
/* 1943 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1948 */     return elements;
/*      */   }
/*      */ 
/*      */   public final List<Label> packed_switch_elements()
/*      */     throws RecognitionException
/*      */   {
/* 1957 */     List elements = null;
/*      */ 
/* 1960 */     Label label_ref63 = null;
/*      */ 
/* 1962 */     elements = Lists.newArrayList();
/*      */     try
/*      */     {
/* 1967 */       match(this.input, 134, FOLLOW_I_PACKED_SWITCH_ELEMENTS_in_packed_switch_elements837);
/* 1968 */       if (this.input.LA(1) == 2) {
/* 1969 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 1973 */           int alt14 = 2;
/* 1974 */           int LA14_0 = this.input.LA(1);
/* 1975 */           if (LA14_0 == 205) {
/* 1976 */             alt14 = 1;
/*      */           }
/*      */ 
/* 1979 */           switch (alt14)
/*      */           {
/*      */           case 1:
/* 1983 */             pushFollow(FOLLOW_label_ref_in_packed_switch_elements846);
/* 1984 */             label_ref63 = label_ref();
/* 1985 */             this.state._fsp -= 1;
/*      */ 
/* 1987 */             elements.add(label_ref63);
/*      */ 
/* 1989 */             break;
/*      */           default:
/* 1992 */             break label134;
/*      */           }
/*      */         }
/*      */ 
/* 1996 */         label134: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2003 */       reportError(re);
/* 2004 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2009 */     return elements;
/*      */   }
/*      */ 
/*      */   public final List<SwitchLabelElement> sparse_switch_elements()
/*      */     throws RecognitionException
/*      */   {
/* 2018 */     List elements = null;
/*      */ 
/* 2021 */     int fixed_32bit_literal64 = 0;
/* 2022 */     Label label_ref65 = null;
/*      */ 
/* 2024 */     elements = Lists.newArrayList();
/*      */     try
/*      */     {
/* 2029 */       match(this.input, 145, FOLLOW_I_SPARSE_SWITCH_ELEMENTS_in_sparse_switch_elements881);
/* 2030 */       if (this.input.LA(1) == 2) {
/* 2031 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 2035 */           int alt15 = 2;
/* 2036 */           int LA15_0 = this.input.LA(1);
/* 2037 */           if (((LA15_0 >= 21) && (LA15_0 <= 22)) || (LA15_0 == 25) || (LA15_0 == 51) || (LA15_0 == 102) || (LA15_0 == 187) || (LA15_0 == 204)) {
/* 2038 */             alt15 = 1;
/*      */           }
/*      */ 
/* 2041 */           switch (alt15)
/*      */           {
/*      */           case 1:
/* 2045 */             pushFollow(FOLLOW_fixed_32bit_literal_in_sparse_switch_elements891);
/* 2046 */             fixed_32bit_literal64 = fixed_32bit_literal();
/* 2047 */             this.state._fsp -= 1;
/*      */ 
/* 2049 */             pushFollow(FOLLOW_label_ref_in_sparse_switch_elements893);
/* 2050 */             label_ref65 = label_ref();
/* 2051 */             this.state._fsp -= 1;
/*      */ 
/* 2054 */             elements.add(new SwitchLabelElement(fixed_32bit_literal64, label_ref65));
/*      */ 
/* 2057 */             break;
/*      */           default:
/* 2060 */             break label215;
/*      */           }
/*      */         }
/*      */ 
/* 2064 */         label215: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2071 */       reportError(re);
/* 2072 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2077 */     return elements;
/*      */   }
/*      */ 
/*      */   public final BuilderMethod method()
/*      */     throws RecognitionException
/*      */   {
/* 2094 */     this.method_stack.push(new method_scope());
/* 2095 */     BuilderMethod ret = null;
/*      */ 
/* 2098 */     CommonTree I_METHOD70 = null;
/* 2099 */     int access_list66 = 0;
/* 2100 */     TreeRuleReturnScope method_name_and_prototype67 = null;
/* 2101 */     TreeRuleReturnScope registers_directive68 = null;
/* 2102 */     List catches69 = null;
/* 2103 */     Set annotations71 = null;
/*      */ 
/* 2106 */     ((method_scope)this.method_stack.peek()).totalMethodRegisters = 0;
/* 2107 */     ((method_scope)this.method_stack.peek()).methodParameterRegisters = 0;
/* 2108 */     int accessFlags = 0;
/* 2109 */     ((method_scope)this.method_stack.peek()).isStatic = false;
/*      */     try
/*      */     {
/* 2115 */       I_METHOD70 = (CommonTree)match(this.input, 129, FOLLOW_I_METHOD_in_method945);
/* 2116 */       match(this.input, 2, null);
/* 2117 */       pushFollow(FOLLOW_method_name_and_prototype_in_method953);
/* 2118 */       method_name_and_prototype67 = method_name_and_prototype();
/* 2119 */       this.state._fsp -= 1;
/*      */ 
/* 2121 */       pushFollow(FOLLOW_access_list_in_method961);
/* 2122 */       access_list66 = access_list();
/* 2123 */       this.state._fsp -= 1;
/*      */ 
/* 2126 */       accessFlags = access_list66;
/* 2127 */       ((method_scope)this.method_stack.peek()).isStatic = AccessFlags.STATIC.isSet(accessFlags);
/* 2128 */       ((method_scope)this.method_stack.peek()).methodParameterRegisters = MethodUtil.getParameterRegisterCount(method_name_and_prototype67 != null ? ((method_name_and_prototype_return)method_name_and_prototype67).parameters : null, ((method_scope)this.method_stack.peek()).isStatic);
/*      */ 
/* 2132 */       int alt16 = 2;
/* 2133 */       int LA16_0 = this.input.LA(1);
/* 2134 */       if ((LA16_0 == 128) || (LA16_0 == 140)) {
/* 2135 */         alt16 = 1;
/*      */       }
/* 2137 */       else if (LA16_0 == 133) {
/* 2138 */         alt16 = 2;
/*      */       }
/*      */       else
/*      */       {
/* 2142 */         NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
/*      */ 
/* 2144 */         throw nvae;
/*      */       }
/*      */ 
/* 2147 */       switch (alt16)
/*      */       {
/*      */       case 1:
/* 2154 */         pushFollow(FOLLOW_registers_directive_in_method988);
/* 2155 */         registers_directive68 = registers_directive();
/* 2156 */         this.state._fsp -= 1;
/*      */ 
/* 2159 */         if ((registers_directive68 != null) && (((registers_directive_return)registers_directive68).isLocalsDirective))
/* 2160 */           ((method_scope)this.method_stack.peek()).totalMethodRegisters = ((registers_directive68 != null ? ((registers_directive_return)registers_directive68).registers : 0) + ((method_scope)this.method_stack.peek()).methodParameterRegisters);
/*      */         else {
/* 2162 */           ((method_scope)this.method_stack.peek()).totalMethodRegisters = (registers_directive68 != null ? ((registers_directive_return)registers_directive68).registers : 0);
/*      */         }
/*      */ 
/* 2165 */         ((method_scope)this.method_stack.peek()).methodBuilder = new MethodImplementationBuilder(((method_scope)this.method_stack.peek()).totalMethodRegisters);
/*      */ 
/* 2171 */         break;
/*      */       case 2:
/* 2176 */         ((method_scope)this.method_stack.peek()).methodBuilder = new MethodImplementationBuilder(0);
/*      */       }
/*      */ 
/* 2183 */       pushFollow(FOLLOW_ordered_method_items_in_method1045);
/* 2184 */       ordered_method_items();
/* 2185 */       this.state._fsp -= 1;
/*      */ 
/* 2187 */       pushFollow(FOLLOW_catches_in_method1053);
/* 2188 */       catches69 = catches();
/* 2189 */       this.state._fsp -= 1;
/*      */ 
/* 2191 */       pushFollow(FOLLOW_parameters_in_method1061);
/* 2192 */       parameters(method_name_and_prototype67 != null ? ((method_name_and_prototype_return)method_name_and_prototype67).parameters : null);
/* 2193 */       this.state._fsp -= 1;
/*      */ 
/* 2195 */       pushFollow(FOLLOW_annotations_in_method1070);
/* 2196 */       annotations71 = annotations();
/* 2197 */       this.state._fsp -= 1;
/*      */ 
/* 2199 */       match(this.input, 3, null);
/*      */ 
/* 2202 */       MethodImplementation methodImplementation = null;
/* 2203 */       List tryBlocks = catches69;
/*      */ 
/* 2205 */       boolean isAbstract = false;
/* 2206 */       boolean isNative = false;
/*      */ 
/* 2208 */       if ((accessFlags & AccessFlags.ABSTRACT.getValue()) != 0)
/* 2209 */         isAbstract = true;
/* 2210 */       else if ((accessFlags & AccessFlags.NATIVE.getValue()) != 0) {
/* 2211 */         isNative = true;
/*      */       }
/*      */ 
/* 2214 */       methodImplementation = ((method_scope)this.method_stack.peek()).methodBuilder.getMethodImplementation();
/*      */ 
/* 2216 */       if (Iterables.isEmpty(methodImplementation.getInstructions())) {
/* 2217 */         if ((!isAbstract) && (!isNative))
/* 2218 */           throw new SemanticException(this.input, I_METHOD70, "A non-abstract/non-native method must have at least 1 instruction", new Object[0]);
/*      */         String methodType;
/*      */         String methodType;
/* 2222 */         if (isAbstract)
/* 2223 */           methodType = "an abstract";
/*      */         else {
/* 2225 */           methodType = "a native";
/*      */         }
/*      */ 
/* 2228 */         if ((registers_directive68 != null ? (CommonTree)registers_directive68.start : null) != null) {
/* 2229 */           if ((registers_directive68 != null) && (((registers_directive_return)registers_directive68).isLocalsDirective)) {
/* 2230 */             throw new SemanticException(this.input, registers_directive68 != null ? (CommonTree)registers_directive68.start : null, "A .locals directive is not valid in %s method", new Object[] { methodType });
/*      */           }
/* 2232 */           throw new SemanticException(this.input, registers_directive68 != null ? (CommonTree)registers_directive68.start : null, "A .registers directive is not valid in %s method", new Object[] { methodType });
/*      */         }
/*      */ 
/* 2236 */         if (methodImplementation.getTryBlocks().size() > 0) {
/* 2237 */           throw new SemanticException(this.input, I_METHOD70, "try/catch blocks cannot be present in %s method", new Object[] { methodType });
/*      */         }
/*      */ 
/* 2240 */         if (!Iterables.isEmpty(methodImplementation.getDebugItems())) {
/* 2241 */           throw new SemanticException(this.input, I_METHOD70, "debug directives cannot be present in %s method", new Object[] { methodType });
/*      */         }
/*      */ 
/* 2244 */         methodImplementation = null;
/*      */       } else {
/* 2246 */         if (isAbstract) {
/* 2247 */           throw new SemanticException(this.input, I_METHOD70, "An abstract method cannot have any instructions", new Object[0]);
/*      */         }
/* 2249 */         if (isNative) {
/* 2250 */           throw new SemanticException(this.input, I_METHOD70, "A native method cannot have any instructions", new Object[0]);
/*      */         }
/*      */ 
/* 2253 */         if ((registers_directive68 != null ? (CommonTree)registers_directive68.start : null) == null) {
/* 2254 */           throw new SemanticException(this.input, I_METHOD70, "A .registers or .locals directive must be present for a non-abstract/non-final method", new Object[0]);
/*      */         }
/*      */ 
/* 2257 */         if (((method_scope)this.method_stack.peek()).totalMethodRegisters < ((method_scope)this.method_stack.peek()).methodParameterRegisters) {
/* 2258 */           throw new SemanticException(this.input, registers_directive68 != null ? (CommonTree)registers_directive68.start : null, "This method requires at least " + Integer.toString(((method_scope)this.method_stack.peek()).methodParameterRegisters) + " registers, for the method parameters", new Object[0]);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2264 */       ret = this.dexBuilder.internMethod(this.classType, method_name_and_prototype67 != null ? ((method_name_and_prototype_return)method_name_and_prototype67).name : null, method_name_and_prototype67 != null ? ((method_name_and_prototype_return)method_name_and_prototype67).parameters : null, method_name_and_prototype67 != null ? ((method_name_and_prototype_return)method_name_and_prototype67).returnType : null, accessFlags, annotations71, methodImplementation);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2277 */       reportError(re);
/* 2278 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/* 2282 */       this.method_stack.pop();
/*      */     }
/* 2284 */     return ret;
/*      */   }
/*      */ 
/*      */   public final method_prototype_return method_prototype()
/*      */     throws RecognitionException
/*      */   {
/* 2298 */     method_prototype_return retval = new method_prototype_return();
/* 2299 */     retval.start = this.input.LT(1);
/*      */ 
/* 2301 */     String type_descriptor72 = null;
/* 2302 */     List field_type_list73 = null;
/*      */     try
/*      */     {
/* 2308 */       match(this.input, 131, FOLLOW_I_METHOD_PROTOTYPE_in_method_prototype1094);
/* 2309 */       match(this.input, 2, null);
/* 2310 */       match(this.input, 132, FOLLOW_I_METHOD_RETURN_TYPE_in_method_prototype1097);
/* 2311 */       match(this.input, 2, null);
/* 2312 */       pushFollow(FOLLOW_type_descriptor_in_method_prototype1099);
/* 2313 */       type_descriptor72 = type_descriptor();
/* 2314 */       this.state._fsp -= 1;
/*      */ 
/* 2316 */       match(this.input, 3, null);
/*      */ 
/* 2318 */       pushFollow(FOLLOW_field_type_list_in_method_prototype1102);
/* 2319 */       field_type_list73 = field_type_list();
/* 2320 */       this.state._fsp -= 1;
/*      */ 
/* 2322 */       match(this.input, 3, null);
/*      */ 
/* 2325 */       retval.returnType = type_descriptor72;
/* 2326 */       retval.parameters = field_type_list73;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2332 */       reportError(re);
/* 2333 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2338 */     return retval;
/*      */   }
/*      */ 
/*      */   public final method_name_and_prototype_return method_name_and_prototype()
/*      */     throws RecognitionException
/*      */   {
/* 2353 */     method_name_and_prototype_return retval = new method_name_and_prototype_return();
/* 2354 */     retval.start = this.input.LT(1);
/*      */ 
/* 2356 */     CommonTree SIMPLE_NAME74 = null;
/* 2357 */     TreeRuleReturnScope method_prototype75 = null;
/*      */     try
/*      */     {
/* 2363 */       SIMPLE_NAME74 = (CommonTree)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_method_name_and_prototype1120);
/* 2364 */       pushFollow(FOLLOW_method_prototype_in_method_name_and_prototype1122);
/* 2365 */       method_prototype75 = method_prototype();
/* 2366 */       this.state._fsp -= 1;
/*      */ 
/* 2369 */       retval.name = (SIMPLE_NAME74 != null ? SIMPLE_NAME74.getText() : null);
/* 2370 */       retval.parameters = Lists.newArrayList();
/*      */ 
/* 2372 */       int paramRegister = 0;
/* 2373 */       for (String type : method_prototype75 != null ? ((method_prototype_return)method_prototype75).parameters : null) {
/* 2374 */         retval.parameters.add(new SmaliMethodParameter(paramRegister++, type));
/* 2375 */         char c = type.charAt(0);
/* 2376 */         if ((c == 'D') || (c == 'J')) {
/* 2377 */           paramRegister++;
/*      */         }
/*      */       }
/* 2380 */       retval.returnType = (method_prototype75 != null ? ((method_prototype_return)method_prototype75).returnType : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2386 */       reportError(re);
/* 2387 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2392 */     return retval;
/*      */   }
/*      */ 
/*      */   public final List<String> field_type_list()
/*      */     throws RecognitionException
/*      */   {
/* 2401 */     List types = null;
/*      */ 
/* 2404 */     TreeRuleReturnScope nonvoid_type_descriptor76 = null;
/*      */ 
/* 2407 */     types = Lists.newArrayList();
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/* 2416 */         int alt17 = 2;
/* 2417 */         int LA17_0 = this.input.LA(1);
/* 2418 */         if ((LA17_0 == 8) || (LA17_0 == 26) || (LA17_0 == 199)) {
/* 2419 */           alt17 = 1;
/*      */         }
/*      */ 
/* 2422 */         switch (alt17)
/*      */         {
/*      */         case 1:
/* 2426 */           pushFollow(FOLLOW_nonvoid_type_descriptor_in_field_type_list1156);
/* 2427 */           nonvoid_type_descriptor76 = nonvoid_type_descriptor();
/* 2428 */           this.state._fsp -= 1;
/*      */ 
/* 2431 */           types.add(nonvoid_type_descriptor76 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor76).type : null);
/*      */ 
/* 2434 */           break;
/*      */         default:
/* 2437 */           break label120;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2445 */       label120: reportError(re);
/* 2446 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2451 */     return types;
/*      */   }
/*      */ 
/*      */   public final ImmutableMethodReference fully_qualified_method()
/*      */     throws RecognitionException
/*      */   {
/* 2460 */     ImmutableMethodReference methodReference = null;
/*      */ 
/* 2463 */     CommonTree SIMPLE_NAME78 = null;
/* 2464 */     TreeRuleReturnScope reference_type_descriptor77 = null;
/* 2465 */     TreeRuleReturnScope method_prototype79 = null;
/*      */     try
/*      */     {
/* 2471 */       pushFollow(FOLLOW_reference_type_descriptor_in_fully_qualified_method1185);
/* 2472 */       reference_type_descriptor77 = reference_type_descriptor();
/* 2473 */       this.state._fsp -= 1;
/*      */ 
/* 2475 */       SIMPLE_NAME78 = (CommonTree)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_fully_qualified_method1187);
/* 2476 */       pushFollow(FOLLOW_method_prototype_in_fully_qualified_method1189);
/* 2477 */       method_prototype79 = method_prototype();
/* 2478 */       this.state._fsp -= 1;
/*      */ 
/* 2481 */       methodReference = new ImmutableMethodReference(reference_type_descriptor77 != null ? ((reference_type_descriptor_return)reference_type_descriptor77).type : null, SIMPLE_NAME78 != null ? SIMPLE_NAME78.getText() : null, method_prototype79 != null ? ((method_prototype_return)method_prototype79).parameters : null, method_prototype79 != null ? ((method_prototype_return)method_prototype79).returnType : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2488 */       reportError(re);
/* 2489 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2494 */     return methodReference;
/*      */   }
/*      */ 
/*      */   public final ImmutableFieldReference fully_qualified_field()
/*      */     throws RecognitionException
/*      */   {
/* 2503 */     ImmutableFieldReference fieldReference = null;
/*      */ 
/* 2506 */     CommonTree SIMPLE_NAME81 = null;
/* 2507 */     TreeRuleReturnScope reference_type_descriptor80 = null;
/* 2508 */     TreeRuleReturnScope nonvoid_type_descriptor82 = null;
/*      */     try
/*      */     {
/* 2514 */       pushFollow(FOLLOW_reference_type_descriptor_in_fully_qualified_field1206);
/* 2515 */       reference_type_descriptor80 = reference_type_descriptor();
/* 2516 */       this.state._fsp -= 1;
/*      */ 
/* 2518 */       SIMPLE_NAME81 = (CommonTree)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_fully_qualified_field1208);
/* 2519 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_fully_qualified_field1210);
/* 2520 */       nonvoid_type_descriptor82 = nonvoid_type_descriptor();
/* 2521 */       this.state._fsp -= 1;
/*      */ 
/* 2524 */       fieldReference = new ImmutableFieldReference(reference_type_descriptor80 != null ? ((reference_type_descriptor_return)reference_type_descriptor80).type : null, SIMPLE_NAME81 != null ? SIMPLE_NAME81.getText() : null, nonvoid_type_descriptor82 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor82).type : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2531 */       reportError(re);
/* 2532 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2537 */     return fieldReference;
/*      */   }
/*      */ 
/*      */   public final registers_directive_return registers_directive()
/*      */     throws RecognitionException
/*      */   {
/* 2551 */     registers_directive_return retval = new registers_directive_return();
/* 2552 */     retval.start = this.input.LT(1);
/*      */ 
/* 2554 */     short short_integral_literal83 = 0;
/*      */     try
/*      */     {
/* 2560 */       retval.registers = 0;
/*      */ 
/* 2562 */       int alt18 = 2;
/* 2563 */       int LA18_0 = this.input.LA(1);
/* 2564 */       if (LA18_0 == 140) {
/* 2565 */         alt18 = 1;
/*      */       }
/* 2567 */       else if (LA18_0 == 128) {
/* 2568 */         alt18 = 2;
/*      */       }
/*      */       else
/*      */       {
/* 2572 */         NoViableAltException nvae = new NoViableAltException("", 18, 0, this.input);
/*      */         throw nvae;
/*      */       }
/*      */ 
/* 2577 */       switch (alt18)
/*      */       {
/*      */       case 1:
/* 2581 */         match(this.input, 140, FOLLOW_I_REGISTERS_in_registers_directive1236);
/* 2582 */         retval.isLocalsDirective = false;
/*      */ 
/* 2584 */         break;
/*      */       case 2:
/* 2588 */         match(this.input, 128, FOLLOW_I_LOCALS_in_registers_directive1248);
/* 2589 */         retval.isLocalsDirective = true;
/*      */       }
/*      */ 
/* 2595 */       match(this.input, 2, null);
/* 2596 */       pushFollow(FOLLOW_short_integral_literal_in_registers_directive1266);
/* 2597 */       short_integral_literal83 = short_integral_literal();
/* 2598 */       this.state._fsp -= 1;
/*      */ 
/* 2600 */       retval.registers = short_integral_literal83;
/* 2601 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2607 */       reportError(re);
/* 2608 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2613 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void label_def()
/*      */     throws RecognitionException
/*      */   {
/* 2622 */     CommonTree SIMPLE_NAME84 = null;
/*      */     try
/*      */     {
/* 2628 */       match(this.input, 125, FOLLOW_I_LABEL_in_label_def1286);
/* 2629 */       match(this.input, 2, null);
/* 2630 */       SIMPLE_NAME84 = (CommonTree)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_label_def1288);
/* 2631 */       match(this.input, 3, null);
/*      */ 
/* 2634 */       ((method_scope)this.method_stack.peek()).methodBuilder.addLabel(SIMPLE_NAME84 != null ? SIMPLE_NAME84.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2640 */       reportError(re);
/* 2641 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final List<BuilderTryBlock> catches()
/*      */     throws RecognitionException
/*      */   {
/* 2654 */     List tryBlocks = null;
/*      */ 
/* 2657 */     tryBlocks = Lists.newArrayList();
/*      */     try
/*      */     {
/* 2662 */       match(this.input, 112, FOLLOW_I_CATCHES_in_catches1314);
/* 2663 */       if (this.input.LA(1) == 2) {
/* 2664 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 2668 */           int alt19 = 2;
/* 2669 */           int LA19_0 = this.input.LA(1);
/* 2670 */           if (LA19_0 == 110) {
/* 2671 */             alt19 = 1;
/*      */           }
/*      */ 
/* 2674 */           switch (alt19)
/*      */           {
/*      */           case 1:
/* 2678 */             pushFollow(FOLLOW_catch_directive_in_catches1316);
/* 2679 */             catch_directive();
/* 2680 */             this.state._fsp -= 1;
/*      */ 
/* 2683 */             break;
/*      */           default:
/* 2686 */             break label117;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */         while (true)
/*      */         {
/* 2693 */           label117: int alt20 = 2;
/* 2694 */           int LA20_0 = this.input.LA(1);
/* 2695 */           if (LA20_0 == 111) {
/* 2696 */             alt20 = 1;
/*      */           }
/*      */ 
/* 2699 */           switch (alt20)
/*      */           {
/*      */           case 1:
/* 2703 */             pushFollow(FOLLOW_catchall_directive_in_catches1319);
/* 2704 */             catchall_directive();
/* 2705 */             this.state._fsp -= 1;
/*      */ 
/* 2708 */             break;
/*      */           default:
/* 2711 */             break label189;
/*      */           }
/*      */         }
/*      */ 
/* 2715 */         label189: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2722 */       reportError(re);
/* 2723 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2728 */     return tryBlocks;
/*      */   }
/*      */ 
/*      */   public final void catch_directive()
/*      */     throws RecognitionException
/*      */   {
/* 2737 */     Label from = null;
/* 2738 */     Label to = null;
/* 2739 */     Label using = null;
/* 2740 */     TreeRuleReturnScope nonvoid_type_descriptor85 = null;
/*      */     try
/*      */     {
/* 2746 */       match(this.input, 110, FOLLOW_I_CATCH_in_catch_directive1332);
/* 2747 */       match(this.input, 2, null);
/* 2748 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_catch_directive1334);
/* 2749 */       nonvoid_type_descriptor85 = nonvoid_type_descriptor();
/* 2750 */       this.state._fsp -= 1;
/*      */ 
/* 2752 */       pushFollow(FOLLOW_label_ref_in_catch_directive1338);
/* 2753 */       from = label_ref();
/* 2754 */       this.state._fsp -= 1;
/*      */ 
/* 2756 */       pushFollow(FOLLOW_label_ref_in_catch_directive1342);
/* 2757 */       to = label_ref();
/* 2758 */       this.state._fsp -= 1;
/*      */ 
/* 2760 */       pushFollow(FOLLOW_label_ref_in_catch_directive1346);
/* 2761 */       using = label_ref();
/* 2762 */       this.state._fsp -= 1;
/*      */ 
/* 2764 */       match(this.input, 3, null);
/*      */ 
/* 2767 */       ((method_scope)this.method_stack.peek()).methodBuilder.addCatch(this.dexBuilder.internTypeReference(nonvoid_type_descriptor85 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor85).type : null), from, to, using);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2774 */       reportError(re);
/* 2775 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void catchall_directive()
/*      */     throws RecognitionException
/*      */   {
/* 2788 */     Label from = null;
/* 2789 */     Label to = null;
/* 2790 */     Label using = null;
/*      */     try
/*      */     {
/* 2796 */       match(this.input, 111, FOLLOW_I_CATCHALL_in_catchall_directive1362);
/* 2797 */       match(this.input, 2, null);
/* 2798 */       pushFollow(FOLLOW_label_ref_in_catchall_directive1366);
/* 2799 */       from = label_ref();
/* 2800 */       this.state._fsp -= 1;
/*      */ 
/* 2802 */       pushFollow(FOLLOW_label_ref_in_catchall_directive1370);
/* 2803 */       to = label_ref();
/* 2804 */       this.state._fsp -= 1;
/*      */ 
/* 2806 */       pushFollow(FOLLOW_label_ref_in_catchall_directive1374);
/* 2807 */       using = label_ref();
/* 2808 */       this.state._fsp -= 1;
/*      */ 
/* 2810 */       match(this.input, 3, null);
/*      */ 
/* 2813 */       ((method_scope)this.method_stack.peek()).methodBuilder.addCatch(from, to, using);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2819 */       reportError(re);
/* 2820 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void parameters(List<SmaliMethodParameter> parameters)
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2837 */       match(this.input, 137, FOLLOW_I_PARAMETERS_in_parameters1391);
/* 2838 */       if (this.input.LA(1) == 2) {
/* 2839 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 2843 */           int alt21 = 2;
/* 2844 */           int LA21_0 = this.input.LA(1);
/* 2845 */           if (LA21_0 == 136) {
/* 2846 */             alt21 = 1;
/*      */           }
/*      */ 
/* 2849 */           switch (alt21)
/*      */           {
/*      */           case 1:
/* 2853 */             pushFollow(FOLLOW_parameter_in_parameters1394);
/* 2854 */             parameter(parameters);
/* 2855 */             this.state._fsp -= 1;
/*      */ 
/* 2858 */             break;
/*      */           default:
/* 2861 */             break label114;
/*      */           }
/*      */         }
/*      */ 
/* 2865 */         label114: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2872 */       reportError(re);
/* 2873 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void parameter(List<SmaliMethodParameter> parameters)
/*      */     throws RecognitionException
/*      */   {
/* 2886 */     CommonTree REGISTER86 = null;
/* 2887 */     CommonTree I_PARAMETER87 = null;
/* 2888 */     String string_literal88 = null;
/* 2889 */     Set annotations89 = null;
/*      */     try
/*      */     {
/* 2895 */       I_PARAMETER87 = (CommonTree)match(this.input, 136, FOLLOW_I_PARAMETER_in_parameter1410);
/* 2896 */       match(this.input, 2, null);
/* 2897 */       REGISTER86 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_parameter1412);
/*      */ 
/* 2899 */       int alt22 = 2;
/* 2900 */       int LA22_0 = this.input.LA(1);
/* 2901 */       if (LA22_0 == 208) {
/* 2902 */         alt22 = 1;
/*      */       }
/* 2904 */       switch (alt22)
/*      */       {
/*      */       case 1:
/* 2908 */         pushFollow(FOLLOW_string_literal_in_parameter1414);
/* 2909 */         string_literal88 = string_literal();
/* 2910 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/* 2917 */       pushFollow(FOLLOW_annotations_in_parameter1417);
/* 2918 */       annotations89 = annotations();
/* 2919 */       this.state._fsp -= 1;
/*      */ 
/* 2921 */       match(this.input, 3, null);
/*      */ 
/* 2924 */       int registerNumber = parseRegister_short(REGISTER86 != null ? REGISTER86.getText() : null);
/* 2925 */       int totalMethodRegisters = ((method_scope)this.method_stack.peek()).totalMethodRegisters;
/* 2926 */       int methodParameterRegisters = ((method_scope)this.method_stack.peek()).methodParameterRegisters;
/*      */ 
/* 2928 */       if (registerNumber >= totalMethodRegisters) {
/*      */         throw new SemanticException(this.input, I_PARAMETER87, "Register %s is larger than the maximum register v%d for this method", new Object[] { REGISTER86 != null ? REGISTER86.getText() : null, Integer.valueOf(totalMethodRegisters - 1) });
/*      */       }
/*      */ 
/* 2932 */       int indexGuess = registerNumber - (totalMethodRegisters - methodParameterRegisters) - (((method_scope)this.method_stack.peek()).isStatic ? 0 : 1);
/*      */ 
/* 2934 */       if (indexGuess < 0) {
/*      */         throw new SemanticException(this.input, I_PARAMETER87, "Register %s is not a parameter register.", new Object[] { REGISTER86 != null ? REGISTER86.getText() : null });
/*      */       }
/*      */ 
/* 2939 */       int parameterIndex = LinearSearch.linearSearch(parameters, SmaliMethodParameter.COMPARATOR, new WithRegister(indexGuess) {
/* 2940 */         public int getRegister() { return this.val$indexGuess;
/*      */         }
/*      */       }
/*      */       , indexGuess);
/*      */ 
/* 2943 */       if (parameterIndex < 0) {
/*      */         throw new SemanticException(this.input, I_PARAMETER87, "Register %s is the second half of a wide parameter.", new Object[] { REGISTER86 != null ? REGISTER86.getText() : null });
/*      */       }
/*      */ 
/* 2948 */       SmaliMethodParameter methodParameter = (SmaliMethodParameter)parameters.get(parameterIndex);
/* 2949 */       methodParameter.name = string_literal88;
/* 2950 */       if ((annotations89 != null) && (annotations89.size() > 0)) {
/* 2951 */         methodParameter.annotations = annotations89;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2958 */       reportError(re);
/* 2959 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void debug_directive()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2974 */       int alt23 = 7;
/* 2975 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 126:
/* 2978 */         alt23 = 1;
/*      */ 
/* 2980 */         break;
/*      */       case 127:
/* 2983 */         alt23 = 2;
/*      */ 
/* 2985 */         break;
/*      */       case 118:
/* 2988 */         alt23 = 3;
/*      */ 
/* 2990 */         break;
/*      */       case 143:
/* 2993 */         alt23 = 4;
/*      */ 
/* 2995 */         break;
/*      */       case 139:
/* 2998 */         alt23 = 5;
/*      */ 
/* 3000 */         break;
/*      */       case 119:
/* 3003 */         alt23 = 6;
/*      */ 
/* 3005 */         break;
/*      */       case 144:
/* 3008 */         alt23 = 7;
/*      */ 
/* 3010 */         break;
/*      */       default:
/* 3012 */         NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 3016 */       switch (alt23)
/*      */       {
/*      */       case 1:
/* 3020 */         pushFollow(FOLLOW_line_in_debug_directive1434);
/* 3021 */         line();
/* 3022 */         this.state._fsp -= 1;
/*      */ 
/* 3025 */         break;
/*      */       case 2:
/* 3029 */         pushFollow(FOLLOW_local_in_debug_directive1440);
/* 3030 */         local();
/* 3031 */         this.state._fsp -= 1;
/*      */ 
/* 3034 */         break;
/*      */       case 3:
/* 3038 */         pushFollow(FOLLOW_end_local_in_debug_directive1446);
/* 3039 */         end_local();
/* 3040 */         this.state._fsp -= 1;
/*      */ 
/* 3043 */         break;
/*      */       case 4:
/* 3047 */         pushFollow(FOLLOW_restart_local_in_debug_directive1452);
/* 3048 */         restart_local();
/* 3049 */         this.state._fsp -= 1;
/*      */ 
/* 3052 */         break;
/*      */       case 5:
/* 3056 */         pushFollow(FOLLOW_prologue_in_debug_directive1458);
/* 3057 */         prologue();
/* 3058 */         this.state._fsp -= 1;
/*      */ 
/* 3061 */         break;
/*      */       case 6:
/* 3065 */         pushFollow(FOLLOW_epilogue_in_debug_directive1464);
/* 3066 */         epilogue();
/* 3067 */         this.state._fsp -= 1;
/*      */ 
/* 3070 */         break;
/*      */       case 7:
/* 3074 */         pushFollow(FOLLOW_source_in_debug_directive1470);
/* 3075 */         source();
/* 3076 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3084 */       reportError(re);
/* 3085 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void line()
/*      */     throws RecognitionException
/*      */   {
/* 3098 */     int integral_literal90 = 0;
/*      */     try
/*      */     {
/* 3104 */       match(this.input, 126, FOLLOW_I_LINE_in_line1481);
/* 3105 */       match(this.input, 2, null);
/* 3106 */       pushFollow(FOLLOW_integral_literal_in_line1483);
/* 3107 */       integral_literal90 = integral_literal();
/* 3108 */       this.state._fsp -= 1;
/*      */ 
/* 3110 */       match(this.input, 3, null);
/*      */ 
/* 3113 */       ((method_scope)this.method_stack.peek()).methodBuilder.addLineNumber(integral_literal90);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3119 */       reportError(re);
/* 3120 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void local()
/*      */     throws RecognitionException
/*      */   {
/* 3133 */     CommonTree REGISTER91 = null;
/* 3134 */     String name = null;
/* 3135 */     String signature = null;
/* 3136 */     TreeRuleReturnScope nonvoid_type_descriptor92 = null;
/*      */     try
/*      */     {
/* 3142 */       match(this.input, 127, FOLLOW_I_LOCAL_in_local1501);
/* 3143 */       match(this.input, 2, null);
/* 3144 */       REGISTER91 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_local1503);
/*      */ 
/* 3146 */       int alt27 = 2;
/* 3147 */       int LA27_0 = this.input.LA(1);
/* 3148 */       if ((LA27_0 == 191) || (LA27_0 == 208)) {
/* 3149 */         alt27 = 1;
/*      */       }
/* 3151 */       switch (alt27)
/*      */       {
/*      */       case 1:
/* 3156 */         int alt24 = 2;
/* 3157 */         int LA24_0 = this.input.LA(1);
/* 3158 */         if (LA24_0 == 191) {
/* 3159 */           alt24 = 1;
/*      */         }
/* 3161 */         else if (LA24_0 == 208) {
/* 3162 */           alt24 = 2;
/*      */         }
/*      */         else
/*      */         {
/* 3166 */           NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
/*      */           throw nvae;
/*      */         }
/*      */ 
/* 3171 */         switch (alt24)
/*      */         {
/*      */         case 1:
/* 3175 */           match(this.input, 191, FOLLOW_NULL_LITERAL_in_local1507);
/*      */ 
/* 3177 */           break;
/*      */         case 2:
/* 3181 */           pushFollow(FOLLOW_string_literal_in_local1513);
/* 3182 */           name = string_literal();
/* 3183 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 3191 */         int alt25 = 2;
/* 3192 */         int LA25_0 = this.input.LA(1);
/* 3193 */         if ((LA25_0 == 8) || (LA25_0 == 26) || (LA25_0 == 199)) {
/* 3194 */           alt25 = 1;
/*      */         }
/* 3196 */         switch (alt25)
/*      */         {
/*      */         case 1:
/* 3200 */           pushFollow(FOLLOW_nonvoid_type_descriptor_in_local1516);
/* 3201 */           nonvoid_type_descriptor92 = nonvoid_type_descriptor();
/* 3202 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 3210 */         int alt26 = 2;
/* 3211 */         int LA26_0 = this.input.LA(1);
/* 3212 */         if (LA26_0 == 208) {
/* 3213 */           alt26 = 1;
/*      */         }
/* 3215 */         switch (alt26)
/*      */         {
/*      */         case 1:
/* 3219 */           pushFollow(FOLLOW_string_literal_in_local1521);
/* 3220 */           signature = string_literal();
/* 3221 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3233 */       match(this.input, 3, null);
/*      */ 
/* 3236 */       int registerNumber = parseRegister_short(REGISTER91 != null ? REGISTER91.getText() : null);
/* 3237 */       ((method_scope)this.method_stack.peek()).methodBuilder.addStartLocal(registerNumber, this.dexBuilder.internNullableStringReference(name), this.dexBuilder.internNullableTypeReference(nonvoid_type_descriptor92 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor92).type : null), this.dexBuilder.internNullableStringReference(signature));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3246 */       reportError(re);
/* 3247 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void end_local()
/*      */     throws RecognitionException
/*      */   {
/* 3260 */     CommonTree REGISTER93 = null;
/*      */     try
/*      */     {
/* 3266 */       match(this.input, 118, FOLLOW_I_END_LOCAL_in_end_local1542);
/* 3267 */       match(this.input, 2, null);
/* 3268 */       REGISTER93 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_end_local1544);
/* 3269 */       match(this.input, 3, null);
/*      */ 
/* 3272 */       int registerNumber = parseRegister_short(REGISTER93 != null ? REGISTER93.getText() : null);
/* 3273 */       ((method_scope)this.method_stack.peek()).methodBuilder.addEndLocal(registerNumber);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3279 */       reportError(re);
/* 3280 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void restart_local()
/*      */     throws RecognitionException
/*      */   {
/* 3293 */     CommonTree REGISTER94 = null;
/*      */     try
/*      */     {
/* 3299 */       match(this.input, 143, FOLLOW_I_RESTART_LOCAL_in_restart_local1562);
/* 3300 */       match(this.input, 2, null);
/* 3301 */       REGISTER94 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_restart_local1564);
/* 3302 */       match(this.input, 3, null);
/*      */ 
/* 3305 */       int registerNumber = parseRegister_short(REGISTER94 != null ? REGISTER94.getText() : null);
/* 3306 */       ((method_scope)this.method_stack.peek()).methodBuilder.addRestartLocal(registerNumber);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3312 */       reportError(re);
/* 3313 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void prologue()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3330 */       match(this.input, 139, FOLLOW_I_PROLOGUE_in_prologue1581);
/*      */ 
/* 3332 */       ((method_scope)this.method_stack.peek()).methodBuilder.addPrologue();
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3338 */       reportError(re);
/* 3339 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void epilogue()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3356 */       match(this.input, 119, FOLLOW_I_EPILOGUE_in_epilogue1597);
/*      */ 
/* 3358 */       ((method_scope)this.method_stack.peek()).methodBuilder.addEpilogue();
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3364 */       reportError(re);
/* 3365 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void source()
/*      */     throws RecognitionException
/*      */   {
/* 3378 */     String string_literal95 = null;
/*      */     try
/*      */     {
/* 3384 */       match(this.input, 144, FOLLOW_I_SOURCE_in_source1614);
/* 3385 */       if (this.input.LA(1) == 2) {
/* 3386 */         match(this.input, 2, null);
/*      */ 
/* 3388 */         int alt28 = 2;
/* 3389 */         int LA28_0 = this.input.LA(1);
/* 3390 */         if (LA28_0 == 208) {
/* 3391 */           alt28 = 1;
/*      */         }
/* 3393 */         switch (alt28)
/*      */         {
/*      */         case 1:
/* 3397 */           pushFollow(FOLLOW_string_literal_in_source1616);
/* 3398 */           string_literal95 = string_literal();
/* 3399 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 3406 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/* 3410 */       ((method_scope)this.method_stack.peek()).methodBuilder.addSetSourceFile(this.dexBuilder.internNullableStringReference(string_literal95));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3416 */       reportError(re);
/* 3417 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void ordered_method_items()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3434 */       match(this.input, 133, FOLLOW_I_ORDERED_METHOD_ITEMS_in_ordered_method_items1635);
/* 3435 */       if (this.input.LA(1) == 2) {
/* 3436 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 3440 */           int alt29 = 4;
/* 3441 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 125:
/* 3444 */             alt29 = 1;
/*      */ 
/* 3446 */             break;
/*      */           case 146:
/*      */           case 147:
/*      */           case 148:
/*      */           case 149:
/*      */           case 150:
/*      */           case 151:
/*      */           case 152:
/*      */           case 153:
/*      */           case 154:
/*      */           case 155:
/*      */           case 156:
/*      */           case 157:
/*      */           case 158:
/*      */           case 159:
/*      */           case 160:
/*      */           case 161:
/*      */           case 162:
/*      */           case 163:
/*      */           case 164:
/*      */           case 165:
/*      */           case 166:
/*      */           case 167:
/*      */           case 168:
/*      */           case 169:
/*      */           case 170:
/*      */           case 171:
/*      */           case 172:
/*      */           case 173:
/*      */           case 174:
/*      */           case 175:
/*      */           case 176:
/*      */           case 177:
/*      */           case 178:
/*      */           case 179:
/* 3482 */             alt29 = 2;
/*      */ 
/* 3484 */             break;
/*      */           case 118:
/*      */           case 119:
/*      */           case 126:
/*      */           case 127:
/*      */           case 139:
/*      */           case 143:
/*      */           case 144:
/* 3493 */             alt29 = 3;
/*      */           case 120:
/*      */           case 121:
/*      */           case 122:
/*      */           case 123:
/*      */           case 124:
/*      */           case 128:
/*      */           case 129:
/*      */           case 130:
/*      */           case 131:
/*      */           case 132:
/*      */           case 133:
/*      */           case 134:
/*      */           case 135:
/*      */           case 136:
/*      */           case 137:
/*      */           case 138:
/*      */           case 140:
/*      */           case 141:
/*      */           case 142:
/* 3497 */           case 145: } switch (alt29)
/*      */           {
/*      */           case 1:
/* 3501 */             pushFollow(FOLLOW_label_def_in_ordered_method_items1638);
/* 3502 */             label_def();
/* 3503 */             this.state._fsp -= 1;
/*      */ 
/* 3506 */             break;
/*      */           case 2:
/* 3510 */             pushFollow(FOLLOW_instruction_in_ordered_method_items1642);
/* 3511 */             instruction();
/* 3512 */             this.state._fsp -= 1;
/*      */ 
/* 3515 */             break;
/*      */           case 3:
/* 3519 */             pushFollow(FOLLOW_debug_directive_in_ordered_method_items1646);
/* 3520 */             debug_directive();
/* 3521 */             this.state._fsp -= 1;
/*      */ 
/* 3524 */             break;
/*      */           default:
/* 3527 */             break label444;
/*      */           }
/*      */         }
/*      */ 
/* 3531 */         label444: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3538 */       reportError(re);
/* 3539 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Label label_ref()
/*      */     throws RecognitionException
/*      */   {
/* 3552 */     Label label = null;
/*      */ 
/* 3555 */     CommonTree SIMPLE_NAME96 = null;
/*      */     try
/*      */     {
/* 3561 */       SIMPLE_NAME96 = (CommonTree)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_label_ref1662);
/* 3562 */       label = ((method_scope)this.method_stack.peek()).methodBuilder.getLabel(SIMPLE_NAME96 != null ? SIMPLE_NAME96.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3567 */       reportError(re);
/* 3568 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3573 */     return label;
/*      */   }
/*      */ 
/*      */   public final register_list_return register_list()
/*      */     throws RecognitionException
/*      */   {
/* 3587 */     register_list_return retval = new register_list_return();
/* 3588 */     retval.start = this.input.LT(1);
/*      */ 
/* 3590 */     CommonTree I_REGISTER_LIST97 = null;
/* 3591 */     CommonTree REGISTER98 = null;
/*      */ 
/* 3594 */     retval.registers = new byte[5];
/* 3595 */     retval.registerCount = 0;
/*      */     try
/*      */     {
/* 3601 */       I_REGISTER_LIST97 = (CommonTree)match(this.input, 141, FOLLOW_I_REGISTER_LIST_in_register_list1687);
/* 3602 */       if (this.input.LA(1) == 2) {
/* 3603 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 3607 */           int alt30 = 2;
/* 3608 */           int LA30_0 = this.input.LA(1);
/* 3609 */           if (LA30_0 == 201) {
/* 3610 */             alt30 = 1;
/*      */           }
/*      */ 
/* 3613 */           switch (alt30)
/*      */           {
/*      */           case 1:
/* 3617 */             REGISTER98 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_register_list1696);
/*      */ 
/* 3619 */             if (retval.registerCount == 5) {
/*      */               throw new SemanticException(this.input, I_REGISTER_LIST97, "A list of registers can only have a maximum of 5 registers. Use the <op>/range alternate opcode instead.", new Object[0]);
/*      */             }
/*      */ 
/* 3623 */             retval.registers[(retval.registerCount++)] = parseRegister_nibble(REGISTER98 != null ? REGISTER98.getText() : null);
/*      */ 
/* 3626 */             break;
/*      */           default:
/* 3629 */             break label215;
/*      */           }
/*      */         }
/*      */ 
/* 3633 */         label215: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3640 */       reportError(re);
/* 3641 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3646 */     return retval;
/*      */   }
/*      */ 
/*      */   public final register_range_return register_range()
/*      */     throws RecognitionException
/*      */   {
/* 3660 */     register_range_return retval = new register_range_return();
/* 3661 */     retval.start = this.input.LT(1);
/*      */ 
/* 3663 */     CommonTree startReg = null;
/* 3664 */     CommonTree endReg = null;
/* 3665 */     CommonTree I_REGISTER_RANGE99 = null;
/*      */     try
/*      */     {
/* 3671 */       I_REGISTER_RANGE99 = (CommonTree)match(this.input, 142, FOLLOW_I_REGISTER_RANGE_in_register_range1721);
/* 3672 */       if (this.input.LA(1) == 2) {
/* 3673 */         match(this.input, 2, null);
/*      */ 
/* 3675 */         int alt32 = 2;
/* 3676 */         int LA32_0 = this.input.LA(1);
/* 3677 */         if (LA32_0 == 201) {
/* 3678 */           alt32 = 1;
/*      */         }
/* 3680 */         switch (alt32)
/*      */         {
/*      */         case 1:
/* 3684 */           startReg = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_register_range1726);
/*      */ 
/* 3686 */           int alt31 = 2;
/* 3687 */           int LA31_0 = this.input.LA(1);
/* 3688 */           if (LA31_0 == 201) {
/* 3689 */             alt31 = 1;
/*      */           }
/* 3691 */           switch (alt31)
/*      */           {
/*      */           case 1:
/* 3695 */             endReg = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_register_range1730);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 3706 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/* 3710 */       if (startReg == null) {
/* 3711 */         retval.startRegister = 0;
/* 3712 */         retval.endRegister = -1;
/*      */       } else {
/* 3714 */         retval.startRegister = parseRegister_short(startReg != null ? startReg.getText() : null);
/* 3715 */         if (endReg == null)
/* 3716 */           retval.endRegister = retval.startRegister;
/*      */         else {
/* 3718 */           retval.endRegister = parseRegister_short(endReg != null ? endReg.getText() : null);
/*      */         }
/*      */ 
/* 3721 */         int registerCount = retval.endRegister - retval.startRegister + 1;
/* 3722 */         if (registerCount < 1) {
/*      */           throw new SemanticException(this.input, I_REGISTER_RANGE99, "A register range must have the lower register listed first", new Object[0]);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3731 */       reportError(re);
/* 3732 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3737 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ImmutableReference verification_error_reference()
/*      */     throws RecognitionException
/*      */   {
/* 3746 */     ImmutableReference reference = null;
/*      */ 
/* 3749 */     CommonTree CLASS_DESCRIPTOR100 = null;
/* 3750 */     ImmutableFieldReference fully_qualified_field101 = null;
/* 3751 */     ImmutableMethodReference fully_qualified_method102 = null;
/*      */     try
/*      */     {
/* 3755 */       int alt33 = 3;
/* 3756 */       int LA33_0 = this.input.LA(1);
/* 3757 */       if (LA33_0 == 26) {
/* 3758 */         int LA33_1 = this.input.LA(2);
/* 3759 */         if (LA33_1 == 3) {
/* 3760 */           alt33 = 1;
/*      */         }
/* 3762 */         else if (LA33_1 == 205) {
/* 3763 */           int LA33_4 = this.input.LA(3);
/* 3764 */           if ((LA33_4 == 8) || (LA33_4 == 26) || (LA33_4 == 199)) {
/* 3765 */             alt33 = 2;
/*      */           }
/* 3767 */           else if (LA33_4 == 131) {
/* 3768 */             alt33 = 3;
/*      */           }
/*      */           else
/*      */           {
/* 3772 */             int nvaeMark = this.input.mark();
/*      */             try {
/* 3774 */               for (int nvaeConsume = 0; nvaeConsume < 2; nvaeConsume++) {
/* 3775 */                 this.input.consume();
/*      */               }
/* 3777 */               NoViableAltException nvae = new NoViableAltException("", 33, 4, this.input);
/*      */ 
/* 3779 */               throw nvae;
/*      */             } finally {
/* 3781 */               this.input.rewind(nvaeMark);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 3788 */           int nvaeMark = this.input.mark();
/*      */           try {
/* 3790 */             this.input.consume();
/* 3791 */             NoViableAltException nvae = new NoViableAltException("", 33, 1, this.input);
/*      */ 
/* 3793 */             throw nvae;
/*      */           } finally {
/* 3795 */             this.input.rewind(nvaeMark);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/* 3800 */       else if (LA33_0 == 8) {
/* 3801 */         int LA33_2 = this.input.LA(2);
/* 3802 */         if (LA33_2 == 205) {
/* 3803 */           int LA33_4 = this.input.LA(3);
/* 3804 */           if ((LA33_4 == 8) || (LA33_4 == 26) || (LA33_4 == 199)) {
/* 3805 */             alt33 = 2;
/*      */           }
/* 3807 */           else if (LA33_4 == 131) {
/* 3808 */             alt33 = 3;
/*      */           }
/*      */           else
/*      */           {
/* 3812 */             int nvaeMark = this.input.mark();
/*      */             try {
/* 3814 */               for (int nvaeConsume = 0; nvaeConsume < 2; nvaeConsume++) {
/* 3815 */                 this.input.consume();
/*      */               }
/* 3817 */               NoViableAltException nvae = new NoViableAltException("", 33, 4, this.input);
/*      */ 
/* 3819 */               throw nvae;
/*      */             } finally {
/* 3821 */               this.input.rewind(nvaeMark);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 3828 */           int nvaeMark = this.input.mark();
/*      */           try {
/* 3830 */             this.input.consume();
/* 3831 */             NoViableAltException nvae = new NoViableAltException("", 33, 2, this.input);
/*      */ 
/* 3833 */             throw nvae;
/*      */           } finally {
/* 3835 */             this.input.rewind(nvaeMark);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 3842 */         NoViableAltException nvae = new NoViableAltException("", 33, 0, this.input);
/*      */         throw nvae;
/*      */       }
/*      */ 
/* 3847 */       switch (alt33)
/*      */       {
/*      */       case 1:
/* 3851 */         CLASS_DESCRIPTOR100 = (CommonTree)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_verification_error_reference1753);
/*      */ 
/* 3853 */         reference = new ImmutableTypeReference(CLASS_DESCRIPTOR100 != null ? CLASS_DESCRIPTOR100.getText() : null);
/*      */ 
/* 3856 */         break;
/*      */       case 2:
/* 3860 */         pushFollow(FOLLOW_fully_qualified_field_in_verification_error_reference1763);
/* 3861 */         fully_qualified_field101 = fully_qualified_field();
/* 3862 */         this.state._fsp -= 1;
/*      */ 
/* 3865 */         reference = fully_qualified_field101;
/*      */ 
/* 3868 */         break;
/*      */       case 3:
/* 3872 */         pushFollow(FOLLOW_fully_qualified_method_in_verification_error_reference1773);
/* 3873 */         fully_qualified_method102 = fully_qualified_method();
/* 3874 */         this.state._fsp -= 1;
/*      */ 
/* 3877 */         reference = fully_qualified_method102;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3885 */       reportError(re);
/* 3886 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3891 */     return reference;
/*      */   }
/*      */ 
/*      */   public final int verification_error_type()
/*      */     throws RecognitionException
/*      */   {
/* 3900 */     int verificationError = 0;
/*      */ 
/* 3903 */     CommonTree VERIFICATION_ERROR_TYPE103 = null;
/*      */     try
/*      */     {
/* 3909 */       VERIFICATION_ERROR_TYPE103 = (CommonTree)match(this.input, 211, FOLLOW_VERIFICATION_ERROR_TYPE_in_verification_error_type1790);
/*      */ 
/* 3911 */       verificationError = VerificationError.getVerificationError(VERIFICATION_ERROR_TYPE103 != null ? VERIFICATION_ERROR_TYPE103.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3917 */       reportError(re);
/* 3918 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3923 */     return verificationError;
/*      */   }
/*      */ 
/*      */   public final instruction_return instruction()
/*      */     throws RecognitionException
/*      */   {
/* 3935 */     instruction_return retval = new instruction_return();
/* 3936 */     retval.start = this.input.LT(1);
/*      */     try
/*      */     {
/* 3940 */       int alt34 = 34;
/* 3941 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 147:
/* 3944 */         alt34 = 1;
/*      */ 
/* 3946 */         break;
/*      */       case 148:
/* 3949 */         alt34 = 2;
/*      */ 
/* 3951 */         break;
/*      */       case 149:
/* 3954 */         alt34 = 3;
/*      */ 
/* 3956 */         break;
/*      */       case 150:
/* 3959 */         alt34 = 4;
/*      */ 
/* 3961 */         break;
/*      */       case 151:
/* 3964 */         alt34 = 5;
/*      */ 
/* 3966 */         break;
/*      */       case 152:
/* 3969 */         alt34 = 6;
/*      */ 
/* 3971 */         break;
/*      */       case 153:
/* 3974 */         alt34 = 7;
/*      */ 
/* 3976 */         break;
/*      */       case 154:
/* 3979 */         alt34 = 8;
/*      */ 
/* 3981 */         break;
/*      */       case 155:
/* 3984 */         alt34 = 9;
/*      */ 
/* 3986 */         break;
/*      */       case 156:
/* 3989 */         alt34 = 10;
/*      */ 
/* 3991 */         break;
/*      */       case 157:
/* 3994 */         alt34 = 11;
/*      */ 
/* 3996 */         break;
/*      */       case 158:
/* 3999 */         alt34 = 12;
/*      */ 
/* 4001 */         break;
/*      */       case 159:
/* 4004 */         alt34 = 13;
/*      */ 
/* 4006 */         break;
/*      */       case 160:
/* 4009 */         alt34 = 14;
/*      */ 
/* 4011 */         break;
/*      */       case 161:
/* 4014 */         alt34 = 15;
/*      */ 
/* 4016 */         break;
/*      */       case 162:
/* 4019 */         alt34 = 16;
/*      */ 
/* 4021 */         break;
/*      */       case 163:
/* 4024 */         alt34 = 17;
/*      */ 
/* 4026 */         break;
/*      */       case 164:
/* 4029 */         alt34 = 18;
/*      */ 
/* 4031 */         break;
/*      */       case 165:
/* 4034 */         alt34 = 19;
/*      */ 
/* 4036 */         break;
/*      */       case 166:
/* 4039 */         alt34 = 20;
/*      */ 
/* 4041 */         break;
/*      */       case 167:
/* 4044 */         alt34 = 21;
/*      */ 
/* 4046 */         break;
/*      */       case 168:
/* 4049 */         alt34 = 22;
/*      */ 
/* 4051 */         break;
/*      */       case 169:
/* 4054 */         alt34 = 23;
/*      */ 
/* 4056 */         break;
/*      */       case 170:
/* 4059 */         alt34 = 24;
/*      */ 
/* 4061 */         break;
/*      */       case 171:
/* 4064 */         alt34 = 25;
/*      */ 
/* 4066 */         break;
/*      */       case 172:
/* 4069 */         alt34 = 26;
/*      */ 
/* 4071 */         break;
/*      */       case 173:
/* 4074 */         alt34 = 27;
/*      */ 
/* 4076 */         break;
/*      */       case 174:
/* 4079 */         alt34 = 28;
/*      */ 
/* 4081 */         break;
/*      */       case 175:
/* 4084 */         alt34 = 29;
/*      */ 
/* 4086 */         break;
/*      */       case 176:
/* 4089 */         alt34 = 30;
/*      */ 
/* 4091 */         break;
/*      */       case 177:
/* 4094 */         alt34 = 31;
/*      */ 
/* 4096 */         break;
/*      */       case 146:
/* 4099 */         alt34 = 32;
/*      */ 
/* 4101 */         break;
/*      */       case 178:
/* 4104 */         alt34 = 33;
/*      */ 
/* 4106 */         break;
/*      */       case 179:
/* 4109 */         alt34 = 34;
/*      */ 
/* 4111 */         break;
/*      */       default:
/* 4113 */         NoViableAltException nvae = new NoViableAltException("", 34, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 4117 */       switch (alt34)
/*      */       {
/*      */       case 1:
/* 4121 */         pushFollow(FOLLOW_insn_format10t_in_instruction1804);
/* 4122 */         insn_format10t();
/* 4123 */         this.state._fsp -= 1;
/*      */ 
/* 4126 */         break;
/*      */       case 2:
/* 4130 */         pushFollow(FOLLOW_insn_format10x_in_instruction1810);
/* 4131 */         insn_format10x();
/* 4132 */         this.state._fsp -= 1;
/*      */ 
/* 4135 */         break;
/*      */       case 3:
/* 4139 */         pushFollow(FOLLOW_insn_format11n_in_instruction1816);
/* 4140 */         insn_format11n();
/* 4141 */         this.state._fsp -= 1;
/*      */ 
/* 4144 */         break;
/*      */       case 4:
/* 4148 */         pushFollow(FOLLOW_insn_format11x_in_instruction1822);
/* 4149 */         insn_format11x();
/* 4150 */         this.state._fsp -= 1;
/*      */ 
/* 4153 */         break;
/*      */       case 5:
/* 4157 */         pushFollow(FOLLOW_insn_format12x_in_instruction1828);
/* 4158 */         insn_format12x();
/* 4159 */         this.state._fsp -= 1;
/*      */ 
/* 4162 */         break;
/*      */       case 6:
/* 4166 */         pushFollow(FOLLOW_insn_format20bc_in_instruction1834);
/* 4167 */         insn_format20bc();
/* 4168 */         this.state._fsp -= 1;
/*      */ 
/* 4171 */         break;
/*      */       case 7:
/* 4175 */         pushFollow(FOLLOW_insn_format20t_in_instruction1840);
/* 4176 */         insn_format20t();
/* 4177 */         this.state._fsp -= 1;
/*      */ 
/* 4180 */         break;
/*      */       case 8:
/* 4184 */         pushFollow(FOLLOW_insn_format21c_field_in_instruction1846);
/* 4185 */         insn_format21c_field();
/* 4186 */         this.state._fsp -= 1;
/*      */ 
/* 4189 */         break;
/*      */       case 9:
/* 4193 */         pushFollow(FOLLOW_insn_format21c_string_in_instruction1852);
/* 4194 */         insn_format21c_string();
/* 4195 */         this.state._fsp -= 1;
/*      */ 
/* 4198 */         break;
/*      */       case 10:
/* 4202 */         pushFollow(FOLLOW_insn_format21c_type_in_instruction1858);
/* 4203 */         insn_format21c_type();
/* 4204 */         this.state._fsp -= 1;
/*      */ 
/* 4207 */         break;
/*      */       case 11:
/* 4211 */         pushFollow(FOLLOW_insn_format21ih_in_instruction1864);
/* 4212 */         insn_format21ih();
/* 4213 */         this.state._fsp -= 1;
/*      */ 
/* 4216 */         break;
/*      */       case 12:
/* 4220 */         pushFollow(FOLLOW_insn_format21lh_in_instruction1870);
/* 4221 */         insn_format21lh();
/* 4222 */         this.state._fsp -= 1;
/*      */ 
/* 4225 */         break;
/*      */       case 13:
/* 4229 */         pushFollow(FOLLOW_insn_format21s_in_instruction1876);
/* 4230 */         insn_format21s();
/* 4231 */         this.state._fsp -= 1;
/*      */ 
/* 4234 */         break;
/*      */       case 14:
/* 4238 */         pushFollow(FOLLOW_insn_format21t_in_instruction1882);
/* 4239 */         insn_format21t();
/* 4240 */         this.state._fsp -= 1;
/*      */ 
/* 4243 */         break;
/*      */       case 15:
/* 4247 */         pushFollow(FOLLOW_insn_format22b_in_instruction1888);
/* 4248 */         insn_format22b();
/* 4249 */         this.state._fsp -= 1;
/*      */ 
/* 4252 */         break;
/*      */       case 16:
/* 4256 */         pushFollow(FOLLOW_insn_format22c_field_in_instruction1894);
/* 4257 */         insn_format22c_field();
/* 4258 */         this.state._fsp -= 1;
/*      */ 
/* 4261 */         break;
/*      */       case 17:
/* 4265 */         pushFollow(FOLLOW_insn_format22c_type_in_instruction1900);
/* 4266 */         insn_format22c_type();
/* 4267 */         this.state._fsp -= 1;
/*      */ 
/* 4270 */         break;
/*      */       case 18:
/* 4274 */         pushFollow(FOLLOW_insn_format22s_in_instruction1906);
/* 4275 */         insn_format22s();
/* 4276 */         this.state._fsp -= 1;
/*      */ 
/* 4279 */         break;
/*      */       case 19:
/* 4283 */         pushFollow(FOLLOW_insn_format22t_in_instruction1912);
/* 4284 */         insn_format22t();
/* 4285 */         this.state._fsp -= 1;
/*      */ 
/* 4288 */         break;
/*      */       case 20:
/* 4292 */         pushFollow(FOLLOW_insn_format22x_in_instruction1918);
/* 4293 */         insn_format22x();
/* 4294 */         this.state._fsp -= 1;
/*      */ 
/* 4297 */         break;
/*      */       case 21:
/* 4301 */         pushFollow(FOLLOW_insn_format23x_in_instruction1924);
/* 4302 */         insn_format23x();
/* 4303 */         this.state._fsp -= 1;
/*      */ 
/* 4306 */         break;
/*      */       case 22:
/* 4310 */         pushFollow(FOLLOW_insn_format30t_in_instruction1930);
/* 4311 */         insn_format30t();
/* 4312 */         this.state._fsp -= 1;
/*      */ 
/* 4315 */         break;
/*      */       case 23:
/* 4319 */         pushFollow(FOLLOW_insn_format31c_in_instruction1936);
/* 4320 */         insn_format31c();
/* 4321 */         this.state._fsp -= 1;
/*      */ 
/* 4324 */         break;
/*      */       case 24:
/* 4328 */         pushFollow(FOLLOW_insn_format31i_in_instruction1942);
/* 4329 */         insn_format31i();
/* 4330 */         this.state._fsp -= 1;
/*      */ 
/* 4333 */         break;
/*      */       case 25:
/* 4337 */         pushFollow(FOLLOW_insn_format31t_in_instruction1948);
/* 4338 */         insn_format31t();
/* 4339 */         this.state._fsp -= 1;
/*      */ 
/* 4342 */         break;
/*      */       case 26:
/* 4346 */         pushFollow(FOLLOW_insn_format32x_in_instruction1954);
/* 4347 */         insn_format32x();
/* 4348 */         this.state._fsp -= 1;
/*      */ 
/* 4351 */         break;
/*      */       case 27:
/* 4355 */         pushFollow(FOLLOW_insn_format35c_method_in_instruction1960);
/* 4356 */         insn_format35c_method();
/* 4357 */         this.state._fsp -= 1;
/*      */ 
/* 4360 */         break;
/*      */       case 28:
/* 4364 */         pushFollow(FOLLOW_insn_format35c_type_in_instruction1966);
/* 4365 */         insn_format35c_type();
/* 4366 */         this.state._fsp -= 1;
/*      */ 
/* 4369 */         break;
/*      */       case 29:
/* 4373 */         pushFollow(FOLLOW_insn_format3rc_method_in_instruction1972);
/* 4374 */         insn_format3rc_method();
/* 4375 */         this.state._fsp -= 1;
/*      */ 
/* 4378 */         break;
/*      */       case 30:
/* 4382 */         pushFollow(FOLLOW_insn_format3rc_type_in_instruction1978);
/* 4383 */         insn_format3rc_type();
/* 4384 */         this.state._fsp -= 1;
/*      */ 
/* 4387 */         break;
/*      */       case 31:
/* 4391 */         pushFollow(FOLLOW_insn_format51l_type_in_instruction1984);
/* 4392 */         insn_format51l_type();
/* 4393 */         this.state._fsp -= 1;
/*      */ 
/* 4396 */         break;
/*      */       case 32:
/* 4400 */         pushFollow(FOLLOW_insn_array_data_directive_in_instruction1990);
/* 4401 */         insn_array_data_directive();
/* 4402 */         this.state._fsp -= 1;
/*      */ 
/* 4405 */         break;
/*      */       case 33:
/* 4409 */         pushFollow(FOLLOW_insn_packed_switch_directive_in_instruction1996);
/* 4410 */         insn_packed_switch_directive();
/* 4411 */         this.state._fsp -= 1;
/*      */ 
/* 4414 */         break;
/*      */       case 34:
/* 4418 */         pushFollow(FOLLOW_insn_sparse_switch_directive_in_instruction2002);
/* 4419 */         insn_sparse_switch_directive();
/* 4420 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 4429 */       reportError(new SemanticException(this.input, (CommonTree)retval.start, ex.getMessage(), new Object[0]));
/* 4430 */       recover(this.input, null);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 4437 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void insn_format10t()
/*      */     throws RecognitionException
/*      */   {
/* 4446 */     CommonTree INSTRUCTION_FORMAT10t104 = null;
/* 4447 */     Label label_ref105 = null;
/*      */     try
/*      */     {
/* 4453 */       match(this.input, 147, FOLLOW_I_STATEMENT_FORMAT10t_in_insn_format10t2026);
/* 4454 */       match(this.input, 2, null);
/* 4455 */       INSTRUCTION_FORMAT10t104 = (CommonTree)match(this.input, 58, FOLLOW_INSTRUCTION_FORMAT10t_in_insn_format10t2028);
/* 4456 */       pushFollow(FOLLOW_label_ref_in_insn_format10t2030);
/* 4457 */       label_ref105 = label_ref();
/* 4458 */       this.state._fsp -= 1;
/*      */ 
/* 4460 */       match(this.input, 3, null);
/*      */ 
/* 4463 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT10t104 != null ? INSTRUCTION_FORMAT10t104.getText() : null);
/* 4464 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction10t(opcode, label_ref105));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4470 */       reportError(re);
/* 4471 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format10x()
/*      */     throws RecognitionException
/*      */   {
/* 4484 */     CommonTree INSTRUCTION_FORMAT10x106 = null;
/*      */     try
/*      */     {
/* 4490 */       match(this.input, 148, FOLLOW_I_STATEMENT_FORMAT10x_in_insn_format10x2053);
/* 4491 */       match(this.input, 2, null);
/* 4492 */       INSTRUCTION_FORMAT10x106 = (CommonTree)match(this.input, 59, FOLLOW_INSTRUCTION_FORMAT10x_in_insn_format10x2055);
/* 4493 */       match(this.input, 3, null);
/*      */ 
/* 4496 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT10x106 != null ? INSTRUCTION_FORMAT10x106.getText() : null);
/* 4497 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction10x(opcode));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4503 */       reportError(re);
/* 4504 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format11n()
/*      */     throws RecognitionException
/*      */   {
/* 4517 */     CommonTree INSTRUCTION_FORMAT11n107 = null;
/* 4518 */     CommonTree REGISTER108 = null;
/* 4519 */     short short_integral_literal109 = 0;
/*      */     try
/*      */     {
/* 4525 */       match(this.input, 149, FOLLOW_I_STATEMENT_FORMAT11n_in_insn_format11n2078);
/* 4526 */       match(this.input, 2, null);
/* 4527 */       INSTRUCTION_FORMAT11n107 = (CommonTree)match(this.input, 61, FOLLOW_INSTRUCTION_FORMAT11n_in_insn_format11n2080);
/* 4528 */       REGISTER108 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format11n2082);
/* 4529 */       pushFollow(FOLLOW_short_integral_literal_in_insn_format11n2084);
/* 4530 */       short_integral_literal109 = short_integral_literal();
/* 4531 */       this.state._fsp -= 1;
/*      */ 
/* 4533 */       match(this.input, 3, null);
/*      */ 
/* 4536 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT11n107 != null ? INSTRUCTION_FORMAT11n107.getText() : null);
/* 4537 */       byte regA = parseRegister_nibble(REGISTER108 != null ? REGISTER108.getText() : null);
/*      */ 
/* 4539 */       short litB = short_integral_literal109;
/* 4540 */       LiteralTools.checkNibble(litB);
/*      */ 
/* 4542 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction11n(opcode, regA, litB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4548 */       reportError(re);
/* 4549 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format11x()
/*      */     throws RecognitionException
/*      */   {
/* 4562 */     CommonTree INSTRUCTION_FORMAT11x110 = null;
/* 4563 */     CommonTree REGISTER111 = null;
/*      */     try
/*      */     {
/* 4569 */       match(this.input, 150, FOLLOW_I_STATEMENT_FORMAT11x_in_insn_format11x2107);
/* 4570 */       match(this.input, 2, null);
/* 4571 */       INSTRUCTION_FORMAT11x110 = (CommonTree)match(this.input, 62, FOLLOW_INSTRUCTION_FORMAT11x_in_insn_format11x2109);
/* 4572 */       REGISTER111 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format11x2111);
/* 4573 */       match(this.input, 3, null);
/*      */ 
/* 4576 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT11x110 != null ? INSTRUCTION_FORMAT11x110.getText() : null);
/* 4577 */       short regA = parseRegister_byte(REGISTER111 != null ? REGISTER111.getText() : null);
/*      */ 
/* 4579 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction11x(opcode, regA));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4585 */       reportError(re);
/* 4586 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format12x()
/*      */     throws RecognitionException
/*      */   {
/* 4599 */     CommonTree registerA = null;
/* 4600 */     CommonTree registerB = null;
/* 4601 */     CommonTree INSTRUCTION_FORMAT12x112 = null;
/*      */     try
/*      */     {
/* 4607 */       match(this.input, 151, FOLLOW_I_STATEMENT_FORMAT12x_in_insn_format12x2134);
/* 4608 */       match(this.input, 2, null);
/* 4609 */       INSTRUCTION_FORMAT12x112 = (CommonTree)match(this.input, 63, FOLLOW_INSTRUCTION_FORMAT12x_in_insn_format12x2136);
/* 4610 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format12x2140);
/* 4611 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format12x2144);
/* 4612 */       match(this.input, 3, null);
/*      */ 
/* 4615 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT12x112 != null ? INSTRUCTION_FORMAT12x112.getText() : null);
/* 4616 */       byte regA = parseRegister_nibble(registerA != null ? registerA.getText() : null);
/* 4617 */       byte regB = parseRegister_nibble(registerB != null ? registerB.getText() : null);
/*      */ 
/* 4619 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction12x(opcode, regA, regB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4625 */       reportError(re);
/* 4626 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format20bc()
/*      */     throws RecognitionException
/*      */   {
/* 4639 */     CommonTree INSTRUCTION_FORMAT20bc113 = null;
/* 4640 */     int verification_error_type114 = 0;
/* 4641 */     ImmutableReference verification_error_reference115 = null;
/*      */     try
/*      */     {
/* 4647 */       match(this.input, 152, FOLLOW_I_STATEMENT_FORMAT20bc_in_insn_format20bc2167);
/* 4648 */       match(this.input, 2, null);
/* 4649 */       INSTRUCTION_FORMAT20bc113 = (CommonTree)match(this.input, 65, FOLLOW_INSTRUCTION_FORMAT20bc_in_insn_format20bc2169);
/* 4650 */       pushFollow(FOLLOW_verification_error_type_in_insn_format20bc2171);
/* 4651 */       verification_error_type114 = verification_error_type();
/* 4652 */       this.state._fsp -= 1;
/*      */ 
/* 4654 */       pushFollow(FOLLOW_verification_error_reference_in_insn_format20bc2173);
/* 4655 */       verification_error_reference115 = verification_error_reference();
/* 4656 */       this.state._fsp -= 1;
/*      */ 
/* 4658 */       match(this.input, 3, null);
/*      */ 
/* 4661 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT20bc113 != null ? INSTRUCTION_FORMAT20bc113.getText() : null);
/*      */ 
/* 4663 */       int verificationError = verification_error_type114;
/* 4664 */       ImmutableReference referencedItem = verification_error_reference115;
/*      */ 
/* 4666 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction20bc(opcode, verificationError, this.dexBuilder.internReference(referencedItem)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4673 */       reportError(re);
/* 4674 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format20t()
/*      */     throws RecognitionException
/*      */   {
/* 4687 */     CommonTree INSTRUCTION_FORMAT20t116 = null;
/* 4688 */     Label label_ref117 = null;
/*      */     try
/*      */     {
/* 4694 */       match(this.input, 153, FOLLOW_I_STATEMENT_FORMAT20t_in_insn_format20t2196);
/* 4695 */       match(this.input, 2, null);
/* 4696 */       INSTRUCTION_FORMAT20t116 = (CommonTree)match(this.input, 66, FOLLOW_INSTRUCTION_FORMAT20t_in_insn_format20t2198);
/* 4697 */       pushFollow(FOLLOW_label_ref_in_insn_format20t2200);
/* 4698 */       label_ref117 = label_ref();
/* 4699 */       this.state._fsp -= 1;
/*      */ 
/* 4701 */       match(this.input, 3, null);
/*      */ 
/* 4704 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT20t116 != null ? INSTRUCTION_FORMAT20t116.getText() : null);
/* 4705 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction20t(opcode, label_ref117));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4711 */       reportError(re);
/* 4712 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format21c_field()
/*      */     throws RecognitionException
/*      */   {
/* 4725 */     CommonTree inst = null;
/* 4726 */     CommonTree REGISTER118 = null;
/* 4727 */     ImmutableFieldReference fully_qualified_field119 = null;
/*      */     try
/*      */     {
/* 4733 */       match(this.input, 154, FOLLOW_I_STATEMENT_FORMAT21c_FIELD_in_insn_format21c_field2223);
/* 4734 */       match(this.input, 2, null);
/* 4735 */       inst = (CommonTree)this.input.LT(1);
/* 4736 */       if ((this.input.LA(1) >= 67) && (this.input.LA(1) <= 68)) {
/* 4737 */         this.input.consume();
/* 4738 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/* 4741 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*      */         throw mse;
/*      */       }
/* 4744 */       REGISTER118 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21c_field2235);
/* 4745 */       pushFollow(FOLLOW_fully_qualified_field_in_insn_format21c_field2237);
/* 4746 */       fully_qualified_field119 = fully_qualified_field();
/* 4747 */       this.state._fsp -= 1;
/*      */ 
/* 4749 */       match(this.input, 3, null);
/*      */ 
/* 4752 */       Opcode opcode = this.opcodes.getOpcodeByName(inst != null ? inst.getText() : null);
/* 4753 */       short regA = parseRegister_byte(REGISTER118 != null ? REGISTER118.getText() : null);
/*      */ 
/* 4755 */       ImmutableFieldReference fieldReference = fully_qualified_field119;
/*      */ 
/* 4757 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction21c(opcode, regA, this.dexBuilder.internFieldReference(fieldReference)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4764 */       reportError(re);
/* 4765 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format21c_string()
/*      */     throws RecognitionException
/*      */   {
/* 4778 */     CommonTree INSTRUCTION_FORMAT21c_STRING120 = null;
/* 4779 */     CommonTree REGISTER121 = null;
/* 4780 */     String string_literal122 = null;
/*      */     try
/*      */     {
/* 4786 */       match(this.input, 155, FOLLOW_I_STATEMENT_FORMAT21c_STRING_in_insn_format21c_string2260);
/* 4787 */       match(this.input, 2, null);
/* 4788 */       INSTRUCTION_FORMAT21c_STRING120 = (CommonTree)match(this.input, 69, FOLLOW_INSTRUCTION_FORMAT21c_STRING_in_insn_format21c_string2262);
/* 4789 */       REGISTER121 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21c_string2264);
/* 4790 */       pushFollow(FOLLOW_string_literal_in_insn_format21c_string2266);
/* 4791 */       string_literal122 = string_literal();
/* 4792 */       this.state._fsp -= 1;
/*      */ 
/* 4794 */       match(this.input, 3, null);
/*      */ 
/* 4797 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT21c_STRING120 != null ? INSTRUCTION_FORMAT21c_STRING120.getText() : null);
/* 4798 */       short regA = parseRegister_byte(REGISTER121 != null ? REGISTER121.getText() : null);
/*      */ 
/* 4800 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction21c(opcode, regA, this.dexBuilder.internStringReference(string_literal122)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4807 */       reportError(re);
/* 4808 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format21c_type()
/*      */     throws RecognitionException
/*      */   {
/* 4821 */     CommonTree INSTRUCTION_FORMAT21c_TYPE123 = null;
/* 4822 */     CommonTree REGISTER124 = null;
/* 4823 */     TreeRuleReturnScope nonvoid_type_descriptor125 = null;
/*      */     try
/*      */     {
/* 4829 */       match(this.input, 156, FOLLOW_I_STATEMENT_FORMAT21c_TYPE_in_insn_format21c_type2289);
/* 4830 */       match(this.input, 2, null);
/* 4831 */       INSTRUCTION_FORMAT21c_TYPE123 = (CommonTree)match(this.input, 70, FOLLOW_INSTRUCTION_FORMAT21c_TYPE_in_insn_format21c_type2291);
/* 4832 */       REGISTER124 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21c_type2293);
/* 4833 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format21c_type2295);
/* 4834 */       nonvoid_type_descriptor125 = nonvoid_type_descriptor();
/* 4835 */       this.state._fsp -= 1;
/*      */ 
/* 4837 */       match(this.input, 3, null);
/*      */ 
/* 4840 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT21c_TYPE123 != null ? INSTRUCTION_FORMAT21c_TYPE123.getText() : null);
/* 4841 */       short regA = parseRegister_byte(REGISTER124 != null ? REGISTER124.getText() : null);
/*      */ 
/* 4843 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction21c(opcode, regA, this.dexBuilder.internTypeReference(nonvoid_type_descriptor125 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor125).type : null)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4850 */       reportError(re);
/* 4851 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format21ih()
/*      */     throws RecognitionException
/*      */   {
/* 4864 */     CommonTree INSTRUCTION_FORMAT21ih126 = null;
/* 4865 */     CommonTree REGISTER127 = null;
/* 4866 */     int fixed_32bit_literal128 = 0;
/*      */     try
/*      */     {
/* 4872 */       match(this.input, 157, FOLLOW_I_STATEMENT_FORMAT21ih_in_insn_format21ih2318);
/* 4873 */       match(this.input, 2, null);
/* 4874 */       INSTRUCTION_FORMAT21ih126 = (CommonTree)match(this.input, 71, FOLLOW_INSTRUCTION_FORMAT21ih_in_insn_format21ih2320);
/* 4875 */       REGISTER127 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21ih2322);
/* 4876 */       pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format21ih2324);
/* 4877 */       fixed_32bit_literal128 = fixed_32bit_literal();
/* 4878 */       this.state._fsp -= 1;
/*      */ 
/* 4880 */       match(this.input, 3, null);
/*      */ 
/* 4883 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT21ih126 != null ? INSTRUCTION_FORMAT21ih126.getText() : null);
/* 4884 */       short regA = parseRegister_byte(REGISTER127 != null ? REGISTER127.getText() : null);
/*      */ 
/* 4886 */       int litB = fixed_32bit_literal128;
/*      */ 
/* 4888 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction21ih(opcode, regA, litB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4894 */       reportError(re);
/* 4895 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format21lh()
/*      */     throws RecognitionException
/*      */   {
/* 4908 */     CommonTree INSTRUCTION_FORMAT21lh129 = null;
/* 4909 */     CommonTree REGISTER130 = null;
/* 4910 */     long fixed_64bit_literal131 = 0L;
/*      */     try
/*      */     {
/* 4916 */       match(this.input, 158, FOLLOW_I_STATEMENT_FORMAT21lh_in_insn_format21lh2347);
/* 4917 */       match(this.input, 2, null);
/* 4918 */       INSTRUCTION_FORMAT21lh129 = (CommonTree)match(this.input, 72, FOLLOW_INSTRUCTION_FORMAT21lh_in_insn_format21lh2349);
/* 4919 */       REGISTER130 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21lh2351);
/* 4920 */       pushFollow(FOLLOW_fixed_64bit_literal_in_insn_format21lh2353);
/* 4921 */       fixed_64bit_literal131 = fixed_64bit_literal();
/* 4922 */       this.state._fsp -= 1;
/*      */ 
/* 4924 */       match(this.input, 3, null);
/*      */ 
/* 4927 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT21lh129 != null ? INSTRUCTION_FORMAT21lh129.getText() : null);
/* 4928 */       short regA = parseRegister_byte(REGISTER130 != null ? REGISTER130.getText() : null);
/*      */ 
/* 4930 */       long litB = fixed_64bit_literal131;
/*      */ 
/* 4932 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction21lh(opcode, regA, litB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4938 */       reportError(re);
/* 4939 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format21s()
/*      */     throws RecognitionException
/*      */   {
/* 4952 */     CommonTree INSTRUCTION_FORMAT21s132 = null;
/* 4953 */     CommonTree REGISTER133 = null;
/* 4954 */     short short_integral_literal134 = 0;
/*      */     try
/*      */     {
/* 4960 */       match(this.input, 159, FOLLOW_I_STATEMENT_FORMAT21s_in_insn_format21s2376);
/* 4961 */       match(this.input, 2, null);
/* 4962 */       INSTRUCTION_FORMAT21s132 = (CommonTree)match(this.input, 73, FOLLOW_INSTRUCTION_FORMAT21s_in_insn_format21s2378);
/* 4963 */       REGISTER133 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21s2380);
/* 4964 */       pushFollow(FOLLOW_short_integral_literal_in_insn_format21s2382);
/* 4965 */       short_integral_literal134 = short_integral_literal();
/* 4966 */       this.state._fsp -= 1;
/*      */ 
/* 4968 */       match(this.input, 3, null);
/*      */ 
/* 4971 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT21s132 != null ? INSTRUCTION_FORMAT21s132.getText() : null);
/* 4972 */       short regA = parseRegister_byte(REGISTER133 != null ? REGISTER133.getText() : null);
/*      */ 
/* 4974 */       short litB = short_integral_literal134;
/*      */ 
/* 4976 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction21s(opcode, regA, litB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4982 */       reportError(re);
/* 4983 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format21t()
/*      */     throws RecognitionException
/*      */   {
/* 4996 */     CommonTree INSTRUCTION_FORMAT21t135 = null;
/* 4997 */     CommonTree REGISTER136 = null;
/* 4998 */     Label label_ref137 = null;
/*      */     try
/*      */     {
/* 5004 */       match(this.input, 160, FOLLOW_I_STATEMENT_FORMAT21t_in_insn_format21t2405);
/* 5005 */       match(this.input, 2, null);
/* 5006 */       INSTRUCTION_FORMAT21t135 = (CommonTree)match(this.input, 74, FOLLOW_INSTRUCTION_FORMAT21t_in_insn_format21t2407);
/* 5007 */       REGISTER136 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format21t2409);
/* 5008 */       pushFollow(FOLLOW_label_ref_in_insn_format21t2411);
/* 5009 */       label_ref137 = label_ref();
/* 5010 */       this.state._fsp -= 1;
/*      */ 
/* 5012 */       match(this.input, 3, null);
/*      */ 
/* 5015 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT21t135 != null ? INSTRUCTION_FORMAT21t135.getText() : null);
/* 5016 */       short regA = parseRegister_byte(REGISTER136 != null ? REGISTER136.getText() : null);
/*      */ 
/* 5018 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction21t(opcode, regA, label_ref137));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5024 */       reportError(re);
/* 5025 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format22b()
/*      */     throws RecognitionException
/*      */   {
/* 5038 */     CommonTree registerA = null;
/* 5039 */     CommonTree registerB = null;
/* 5040 */     CommonTree INSTRUCTION_FORMAT22b138 = null;
/* 5041 */     short short_integral_literal139 = 0;
/*      */     try
/*      */     {
/* 5047 */       match(this.input, 161, FOLLOW_I_STATEMENT_FORMAT22b_in_insn_format22b2434);
/* 5048 */       match(this.input, 2, null);
/* 5049 */       INSTRUCTION_FORMAT22b138 = (CommonTree)match(this.input, 75, FOLLOW_INSTRUCTION_FORMAT22b_in_insn_format22b2436);
/* 5050 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22b2440);
/* 5051 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22b2444);
/* 5052 */       pushFollow(FOLLOW_short_integral_literal_in_insn_format22b2446);
/* 5053 */       short_integral_literal139 = short_integral_literal();
/* 5054 */       this.state._fsp -= 1;
/*      */ 
/* 5056 */       match(this.input, 3, null);
/*      */ 
/* 5059 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT22b138 != null ? INSTRUCTION_FORMAT22b138.getText() : null);
/* 5060 */       short regA = parseRegister_byte(registerA != null ? registerA.getText() : null);
/* 5061 */       short regB = parseRegister_byte(registerB != null ? registerB.getText() : null);
/*      */ 
/* 5063 */       short litC = short_integral_literal139;
/* 5064 */       LiteralTools.checkByte(litC);
/*      */ 
/* 5066 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction22b(opcode, regA, regB, litC));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5072 */       reportError(re);
/* 5073 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format22c_field()
/*      */     throws RecognitionException
/*      */   {
/* 5086 */     CommonTree inst = null;
/* 5087 */     CommonTree registerA = null;
/* 5088 */     CommonTree registerB = null;
/* 5089 */     ImmutableFieldReference fully_qualified_field140 = null;
/*      */     try
/*      */     {
/* 5095 */       match(this.input, 162, FOLLOW_I_STATEMENT_FORMAT22c_FIELD_in_insn_format22c_field2469);
/* 5096 */       match(this.input, 2, null);
/* 5097 */       inst = (CommonTree)this.input.LT(1);
/* 5098 */       if ((this.input.LA(1) >= 76) && (this.input.LA(1) <= 77)) {
/* 5099 */         this.input.consume();
/* 5100 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/* 5103 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*      */         throw mse;
/*      */       }
/* 5106 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_field2483);
/* 5107 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_field2487);
/* 5108 */       pushFollow(FOLLOW_fully_qualified_field_in_insn_format22c_field2489);
/* 5109 */       fully_qualified_field140 = fully_qualified_field();
/* 5110 */       this.state._fsp -= 1;
/*      */ 
/* 5112 */       match(this.input, 3, null);
/*      */ 
/* 5115 */       Opcode opcode = this.opcodes.getOpcodeByName(inst != null ? inst.getText() : null);
/* 5116 */       byte regA = parseRegister_nibble(registerA != null ? registerA.getText() : null);
/* 5117 */       byte regB = parseRegister_nibble(registerB != null ? registerB.getText() : null);
/*      */ 
/* 5119 */       ImmutableFieldReference fieldReference = fully_qualified_field140;
/*      */ 
/* 5121 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction22c(opcode, regA, regB, this.dexBuilder.internFieldReference(fieldReference)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5128 */       reportError(re);
/* 5129 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format22c_type()
/*      */     throws RecognitionException
/*      */   {
/* 5142 */     CommonTree registerA = null;
/* 5143 */     CommonTree registerB = null;
/* 5144 */     CommonTree INSTRUCTION_FORMAT22c_TYPE141 = null;
/* 5145 */     TreeRuleReturnScope nonvoid_type_descriptor142 = null;
/*      */     try
/*      */     {
/* 5151 */       match(this.input, 163, FOLLOW_I_STATEMENT_FORMAT22c_TYPE_in_insn_format22c_type2512);
/* 5152 */       match(this.input, 2, null);
/* 5153 */       INSTRUCTION_FORMAT22c_TYPE141 = (CommonTree)match(this.input, 78, FOLLOW_INSTRUCTION_FORMAT22c_TYPE_in_insn_format22c_type2514);
/* 5154 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_type2518);
/* 5155 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22c_type2522);
/* 5156 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format22c_type2524);
/* 5157 */       nonvoid_type_descriptor142 = nonvoid_type_descriptor();
/* 5158 */       this.state._fsp -= 1;
/*      */ 
/* 5160 */       match(this.input, 3, null);
/*      */ 
/* 5163 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT22c_TYPE141 != null ? INSTRUCTION_FORMAT22c_TYPE141.getText() : null);
/* 5164 */       byte regA = parseRegister_nibble(registerA != null ? registerA.getText() : null);
/* 5165 */       byte regB = parseRegister_nibble(registerB != null ? registerB.getText() : null);
/*      */ 
/* 5167 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction22c(opcode, regA, regB, this.dexBuilder.internTypeReference(nonvoid_type_descriptor142 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor142).type : null)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5174 */       reportError(re);
/* 5175 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format22s()
/*      */     throws RecognitionException
/*      */   {
/* 5188 */     CommonTree registerA = null;
/* 5189 */     CommonTree registerB = null;
/* 5190 */     CommonTree INSTRUCTION_FORMAT22s143 = null;
/* 5191 */     short short_integral_literal144 = 0;
/*      */     try
/*      */     {
/* 5197 */       match(this.input, 164, FOLLOW_I_STATEMENT_FORMAT22s_in_insn_format22s2547);
/* 5198 */       match(this.input, 2, null);
/* 5199 */       INSTRUCTION_FORMAT22s143 = (CommonTree)match(this.input, 80, FOLLOW_INSTRUCTION_FORMAT22s_in_insn_format22s2549);
/* 5200 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22s2553);
/* 5201 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22s2557);
/* 5202 */       pushFollow(FOLLOW_short_integral_literal_in_insn_format22s2559);
/* 5203 */       short_integral_literal144 = short_integral_literal();
/* 5204 */       this.state._fsp -= 1;
/*      */ 
/* 5206 */       match(this.input, 3, null);
/*      */ 
/* 5209 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT22s143 != null ? INSTRUCTION_FORMAT22s143.getText() : null);
/* 5210 */       byte regA = parseRegister_nibble(registerA != null ? registerA.getText() : null);
/* 5211 */       byte regB = parseRegister_nibble(registerB != null ? registerB.getText() : null);
/*      */ 
/* 5213 */       short litC = short_integral_literal144;
/*      */ 
/* 5215 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction22s(opcode, regA, regB, litC));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5221 */       reportError(re);
/* 5222 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format22t()
/*      */     throws RecognitionException
/*      */   {
/* 5235 */     CommonTree registerA = null;
/* 5236 */     CommonTree registerB = null;
/* 5237 */     CommonTree INSTRUCTION_FORMAT22t145 = null;
/* 5238 */     Label label_ref146 = null;
/*      */     try
/*      */     {
/* 5244 */       match(this.input, 165, FOLLOW_I_STATEMENT_FORMAT22t_in_insn_format22t2582);
/* 5245 */       match(this.input, 2, null);
/* 5246 */       INSTRUCTION_FORMAT22t145 = (CommonTree)match(this.input, 82, FOLLOW_INSTRUCTION_FORMAT22t_in_insn_format22t2584);
/* 5247 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22t2588);
/* 5248 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22t2592);
/* 5249 */       pushFollow(FOLLOW_label_ref_in_insn_format22t2594);
/* 5250 */       label_ref146 = label_ref();
/* 5251 */       this.state._fsp -= 1;
/*      */ 
/* 5253 */       match(this.input, 3, null);
/*      */ 
/* 5256 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT22t145 != null ? INSTRUCTION_FORMAT22t145.getText() : null);
/* 5257 */       byte regA = parseRegister_nibble(registerA != null ? registerA.getText() : null);
/* 5258 */       byte regB = parseRegister_nibble(registerB != null ? registerB.getText() : null);
/*      */ 
/* 5260 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction22t(opcode, regA, regB, label_ref146));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5266 */       reportError(re);
/* 5267 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format22x()
/*      */     throws RecognitionException
/*      */   {
/* 5280 */     CommonTree registerA = null;
/* 5281 */     CommonTree registerB = null;
/* 5282 */     CommonTree INSTRUCTION_FORMAT22x147 = null;
/*      */     try
/*      */     {
/* 5288 */       match(this.input, 166, FOLLOW_I_STATEMENT_FORMAT22x_in_insn_format22x2617);
/* 5289 */       match(this.input, 2, null);
/* 5290 */       INSTRUCTION_FORMAT22x147 = (CommonTree)match(this.input, 83, FOLLOW_INSTRUCTION_FORMAT22x_in_insn_format22x2619);
/* 5291 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22x2623);
/* 5292 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format22x2627);
/* 5293 */       match(this.input, 3, null);
/*      */ 
/* 5296 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT22x147 != null ? INSTRUCTION_FORMAT22x147.getText() : null);
/* 5297 */       short regA = parseRegister_byte(registerA != null ? registerA.getText() : null);
/* 5298 */       int regB = parseRegister_short(registerB != null ? registerB.getText() : null);
/*      */ 
/* 5300 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction22x(opcode, regA, regB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5306 */       reportError(re);
/* 5307 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format23x()
/*      */     throws RecognitionException
/*      */   {
/* 5320 */     CommonTree registerA = null;
/* 5321 */     CommonTree registerB = null;
/* 5322 */     CommonTree registerC = null;
/* 5323 */     CommonTree INSTRUCTION_FORMAT23x148 = null;
/*      */     try
/*      */     {
/* 5329 */       match(this.input, 167, FOLLOW_I_STATEMENT_FORMAT23x_in_insn_format23x2650);
/* 5330 */       match(this.input, 2, null);
/* 5331 */       INSTRUCTION_FORMAT23x148 = (CommonTree)match(this.input, 84, FOLLOW_INSTRUCTION_FORMAT23x_in_insn_format23x2652);
/* 5332 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format23x2656);
/* 5333 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format23x2660);
/* 5334 */       registerC = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format23x2664);
/* 5335 */       match(this.input, 3, null);
/*      */ 
/* 5338 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT23x148 != null ? INSTRUCTION_FORMAT23x148.getText() : null);
/* 5339 */       short regA = parseRegister_byte(registerA != null ? registerA.getText() : null);
/* 5340 */       short regB = parseRegister_byte(registerB != null ? registerB.getText() : null);
/* 5341 */       short regC = parseRegister_byte(registerC != null ? registerC.getText() : null);
/*      */ 
/* 5343 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction23x(opcode, regA, regB, regC));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5349 */       reportError(re);
/* 5350 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format30t()
/*      */     throws RecognitionException
/*      */   {
/* 5363 */     CommonTree INSTRUCTION_FORMAT30t149 = null;
/* 5364 */     Label label_ref150 = null;
/*      */     try
/*      */     {
/* 5370 */       match(this.input, 168, FOLLOW_I_STATEMENT_FORMAT30t_in_insn_format30t2687);
/* 5371 */       match(this.input, 2, null);
/* 5372 */       INSTRUCTION_FORMAT30t149 = (CommonTree)match(this.input, 85, FOLLOW_INSTRUCTION_FORMAT30t_in_insn_format30t2689);
/* 5373 */       pushFollow(FOLLOW_label_ref_in_insn_format30t2691);
/* 5374 */       label_ref150 = label_ref();
/* 5375 */       this.state._fsp -= 1;
/*      */ 
/* 5377 */       match(this.input, 3, null);
/*      */ 
/* 5380 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT30t149 != null ? INSTRUCTION_FORMAT30t149.getText() : null);
/*      */ 
/* 5382 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction30t(opcode, label_ref150));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5388 */       reportError(re);
/* 5389 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format31c()
/*      */     throws RecognitionException
/*      */   {
/* 5402 */     CommonTree INSTRUCTION_FORMAT31c151 = null;
/* 5403 */     CommonTree REGISTER152 = null;
/* 5404 */     String string_literal153 = null;
/*      */     try
/*      */     {
/* 5410 */       match(this.input, 169, FOLLOW_I_STATEMENT_FORMAT31c_in_insn_format31c2714);
/* 5411 */       match(this.input, 2, null);
/* 5412 */       INSTRUCTION_FORMAT31c151 = (CommonTree)match(this.input, 86, FOLLOW_INSTRUCTION_FORMAT31c_in_insn_format31c2716);
/* 5413 */       REGISTER152 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format31c2718);
/* 5414 */       pushFollow(FOLLOW_string_literal_in_insn_format31c2720);
/* 5415 */       string_literal153 = string_literal();
/* 5416 */       this.state._fsp -= 1;
/*      */ 
/* 5418 */       match(this.input, 3, null);
/*      */ 
/* 5421 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT31c151 != null ? INSTRUCTION_FORMAT31c151.getText() : null);
/* 5422 */       short regA = parseRegister_byte(REGISTER152 != null ? REGISTER152.getText() : null);
/*      */ 
/* 5424 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction31c(opcode, regA, this.dexBuilder.internStringReference(string_literal153)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5431 */       reportError(re);
/* 5432 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format31i()
/*      */     throws RecognitionException
/*      */   {
/* 5445 */     CommonTree INSTRUCTION_FORMAT31i154 = null;
/* 5446 */     CommonTree REGISTER155 = null;
/* 5447 */     int fixed_32bit_literal156 = 0;
/*      */     try
/*      */     {
/* 5453 */       match(this.input, 170, FOLLOW_I_STATEMENT_FORMAT31i_in_insn_format31i2743);
/* 5454 */       match(this.input, 2, null);
/* 5455 */       INSTRUCTION_FORMAT31i154 = (CommonTree)match(this.input, 87, FOLLOW_INSTRUCTION_FORMAT31i_in_insn_format31i2745);
/* 5456 */       REGISTER155 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format31i2747);
/* 5457 */       pushFollow(FOLLOW_fixed_32bit_literal_in_insn_format31i2749);
/* 5458 */       fixed_32bit_literal156 = fixed_32bit_literal();
/* 5459 */       this.state._fsp -= 1;
/*      */ 
/* 5461 */       match(this.input, 3, null);
/*      */ 
/* 5464 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT31i154 != null ? INSTRUCTION_FORMAT31i154.getText() : null);
/* 5465 */       short regA = parseRegister_byte(REGISTER155 != null ? REGISTER155.getText() : null);
/*      */ 
/* 5467 */       int litB = fixed_32bit_literal156;
/*      */ 
/* 5469 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction31i(opcode, regA, litB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5475 */       reportError(re);
/* 5476 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format31t()
/*      */     throws RecognitionException
/*      */   {
/* 5489 */     CommonTree INSTRUCTION_FORMAT31t157 = null;
/* 5490 */     CommonTree REGISTER158 = null;
/* 5491 */     Label label_ref159 = null;
/*      */     try
/*      */     {
/* 5497 */       match(this.input, 171, FOLLOW_I_STATEMENT_FORMAT31t_in_insn_format31t2772);
/* 5498 */       match(this.input, 2, null);
/* 5499 */       INSTRUCTION_FORMAT31t157 = (CommonTree)match(this.input, 89, FOLLOW_INSTRUCTION_FORMAT31t_in_insn_format31t2774);
/* 5500 */       REGISTER158 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format31t2776);
/* 5501 */       pushFollow(FOLLOW_label_ref_in_insn_format31t2778);
/* 5502 */       label_ref159 = label_ref();
/* 5503 */       this.state._fsp -= 1;
/*      */ 
/* 5505 */       match(this.input, 3, null);
/*      */ 
/* 5508 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT31t157 != null ? INSTRUCTION_FORMAT31t157.getText() : null);
/*      */ 
/* 5510 */       short regA = parseRegister_byte(REGISTER158 != null ? REGISTER158.getText() : null);
/*      */ 
/* 5512 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction31t(opcode, regA, label_ref159));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5518 */       reportError(re);
/* 5519 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format32x()
/*      */     throws RecognitionException
/*      */   {
/* 5532 */     CommonTree registerA = null;
/* 5533 */     CommonTree registerB = null;
/* 5534 */     CommonTree INSTRUCTION_FORMAT32x160 = null;
/*      */     try
/*      */     {
/* 5540 */       match(this.input, 172, FOLLOW_I_STATEMENT_FORMAT32x_in_insn_format32x2801);
/* 5541 */       match(this.input, 2, null);
/* 5542 */       INSTRUCTION_FORMAT32x160 = (CommonTree)match(this.input, 90, FOLLOW_INSTRUCTION_FORMAT32x_in_insn_format32x2803);
/* 5543 */       registerA = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format32x2807);
/* 5544 */       registerB = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format32x2811);
/* 5545 */       match(this.input, 3, null);
/*      */ 
/* 5548 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT32x160 != null ? INSTRUCTION_FORMAT32x160.getText() : null);
/* 5549 */       int regA = parseRegister_short(registerA != null ? registerA.getText() : null);
/* 5550 */       int regB = parseRegister_short(registerB != null ? registerB.getText() : null);
/*      */ 
/* 5552 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction32x(opcode, regA, regB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5558 */       reportError(re);
/* 5559 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format35c_method()
/*      */     throws RecognitionException
/*      */   {
/* 5572 */     CommonTree INSTRUCTION_FORMAT35c_METHOD161 = null;
/* 5573 */     TreeRuleReturnScope register_list162 = null;
/* 5574 */     ImmutableMethodReference fully_qualified_method163 = null;
/*      */     try
/*      */     {
/* 5580 */       match(this.input, 173, FOLLOW_I_STATEMENT_FORMAT35c_METHOD_in_insn_format35c_method2834);
/* 5581 */       match(this.input, 2, null);
/* 5582 */       INSTRUCTION_FORMAT35c_METHOD161 = (CommonTree)match(this.input, 91, FOLLOW_INSTRUCTION_FORMAT35c_METHOD_in_insn_format35c_method2836);
/* 5583 */       pushFollow(FOLLOW_register_list_in_insn_format35c_method2838);
/* 5584 */       register_list162 = register_list();
/* 5585 */       this.state._fsp -= 1;
/*      */ 
/* 5587 */       pushFollow(FOLLOW_fully_qualified_method_in_insn_format35c_method2840);
/* 5588 */       fully_qualified_method163 = fully_qualified_method();
/* 5589 */       this.state._fsp -= 1;
/*      */ 
/* 5591 */       match(this.input, 3, null);
/*      */ 
/* 5594 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT35c_METHOD161 != null ? INSTRUCTION_FORMAT35c_METHOD161.getText() : null);
/*      */ 
/* 5597 */       byte[] registers = register_list162 != null ? ((register_list_return)register_list162).registers : null;
/* 5598 */       byte registerCount = register_list162 != null ? ((register_list_return)register_list162).registerCount : 0;
/*      */ 
/* 5600 */       ImmutableMethodReference methodReference = fully_qualified_method163;
/*      */ 
/* 5602 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction35c(opcode, registerCount, registers[0], registers[1], registers[2], registers[3], registers[4], this.dexBuilder.internMethodReference(methodReference)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5609 */       reportError(re);
/* 5610 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format35c_type()
/*      */     throws RecognitionException
/*      */   {
/* 5623 */     CommonTree INSTRUCTION_FORMAT35c_TYPE164 = null;
/* 5624 */     TreeRuleReturnScope register_list165 = null;
/* 5625 */     TreeRuleReturnScope nonvoid_type_descriptor166 = null;
/*      */     try
/*      */     {
/* 5631 */       match(this.input, 174, FOLLOW_I_STATEMENT_FORMAT35c_TYPE_in_insn_format35c_type2863);
/* 5632 */       match(this.input, 2, null);
/* 5633 */       INSTRUCTION_FORMAT35c_TYPE164 = (CommonTree)match(this.input, 93, FOLLOW_INSTRUCTION_FORMAT35c_TYPE_in_insn_format35c_type2865);
/* 5634 */       pushFollow(FOLLOW_register_list_in_insn_format35c_type2867);
/* 5635 */       register_list165 = register_list();
/* 5636 */       this.state._fsp -= 1;
/*      */ 
/* 5638 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format35c_type2869);
/* 5639 */       nonvoid_type_descriptor166 = nonvoid_type_descriptor();
/* 5640 */       this.state._fsp -= 1;
/*      */ 
/* 5642 */       match(this.input, 3, null);
/*      */ 
/* 5645 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT35c_TYPE164 != null ? INSTRUCTION_FORMAT35c_TYPE164.getText() : null);
/*      */ 
/* 5648 */       byte[] registers = register_list165 != null ? ((register_list_return)register_list165).registers : null;
/* 5649 */       byte registerCount = register_list165 != null ? ((register_list_return)register_list165).registerCount : 0;
/*      */ 
/* 5651 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction35c(opcode, registerCount, registers[0], registers[1], registers[2], registers[3], registers[4], this.dexBuilder.internTypeReference(nonvoid_type_descriptor166 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor166).type : null)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5658 */       reportError(re);
/* 5659 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format3rc_method()
/*      */     throws RecognitionException
/*      */   {
/* 5672 */     CommonTree INSTRUCTION_FORMAT3rc_METHOD167 = null;
/* 5673 */     TreeRuleReturnScope register_range168 = null;
/* 5674 */     ImmutableMethodReference fully_qualified_method169 = null;
/*      */     try
/*      */     {
/* 5680 */       match(this.input, 175, FOLLOW_I_STATEMENT_FORMAT3rc_METHOD_in_insn_format3rc_method2892);
/* 5681 */       match(this.input, 2, null);
/* 5682 */       INSTRUCTION_FORMAT3rc_METHOD167 = (CommonTree)match(this.input, 96, FOLLOW_INSTRUCTION_FORMAT3rc_METHOD_in_insn_format3rc_method2894);
/* 5683 */       pushFollow(FOLLOW_register_range_in_insn_format3rc_method2896);
/* 5684 */       register_range168 = register_range();
/* 5685 */       this.state._fsp -= 1;
/*      */ 
/* 5687 */       pushFollow(FOLLOW_fully_qualified_method_in_insn_format3rc_method2898);
/* 5688 */       fully_qualified_method169 = fully_qualified_method();
/* 5689 */       this.state._fsp -= 1;
/*      */ 
/* 5691 */       match(this.input, 3, null);
/*      */ 
/* 5694 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT3rc_METHOD167 != null ? INSTRUCTION_FORMAT3rc_METHOD167.getText() : null);
/* 5695 */       int startRegister = register_range168 != null ? ((register_range_return)register_range168).startRegister : 0;
/* 5696 */       int endRegister = register_range168 != null ? ((register_range_return)register_range168).endRegister : 0;
/*      */ 
/* 5698 */       int registerCount = endRegister - startRegister + 1;
/*      */ 
/* 5700 */       ImmutableMethodReference methodReference = fully_qualified_method169;
/*      */ 
/* 5702 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction3rc(opcode, startRegister, registerCount, this.dexBuilder.internMethodReference(methodReference)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5709 */       reportError(re);
/* 5710 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format3rc_type()
/*      */     throws RecognitionException
/*      */   {
/* 5723 */     CommonTree INSTRUCTION_FORMAT3rc_TYPE170 = null;
/* 5724 */     TreeRuleReturnScope register_range171 = null;
/* 5725 */     TreeRuleReturnScope nonvoid_type_descriptor172 = null;
/*      */     try
/*      */     {
/* 5731 */       match(this.input, 176, FOLLOW_I_STATEMENT_FORMAT3rc_TYPE_in_insn_format3rc_type2921);
/* 5732 */       match(this.input, 2, null);
/* 5733 */       INSTRUCTION_FORMAT3rc_TYPE170 = (CommonTree)match(this.input, 98, FOLLOW_INSTRUCTION_FORMAT3rc_TYPE_in_insn_format3rc_type2923);
/* 5734 */       pushFollow(FOLLOW_register_range_in_insn_format3rc_type2925);
/* 5735 */       register_range171 = register_range();
/* 5736 */       this.state._fsp -= 1;
/*      */ 
/* 5738 */       pushFollow(FOLLOW_nonvoid_type_descriptor_in_insn_format3rc_type2927);
/* 5739 */       nonvoid_type_descriptor172 = nonvoid_type_descriptor();
/* 5740 */       this.state._fsp -= 1;
/*      */ 
/* 5742 */       match(this.input, 3, null);
/*      */ 
/* 5745 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT3rc_TYPE170 != null ? INSTRUCTION_FORMAT3rc_TYPE170.getText() : null);
/* 5746 */       int startRegister = register_range171 != null ? ((register_range_return)register_range171).startRegister : 0;
/* 5747 */       int endRegister = register_range171 != null ? ((register_range_return)register_range171).endRegister : 0;
/*      */ 
/* 5749 */       int registerCount = endRegister - startRegister + 1;
/*      */ 
/* 5751 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction3rc(opcode, startRegister, registerCount, this.dexBuilder.internTypeReference(nonvoid_type_descriptor172 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor172).type : null)));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5758 */       reportError(re);
/* 5759 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_format51l_type()
/*      */     throws RecognitionException
/*      */   {
/* 5772 */     CommonTree INSTRUCTION_FORMAT51l173 = null;
/* 5773 */     CommonTree REGISTER174 = null;
/* 5774 */     long fixed_64bit_literal175 = 0L;
/*      */     try
/*      */     {
/* 5780 */       match(this.input, 177, FOLLOW_I_STATEMENT_FORMAT51l_in_insn_format51l_type2950);
/* 5781 */       match(this.input, 2, null);
/* 5782 */       INSTRUCTION_FORMAT51l173 = (CommonTree)match(this.input, 101, FOLLOW_INSTRUCTION_FORMAT51l_in_insn_format51l_type2952);
/* 5783 */       REGISTER174 = (CommonTree)match(this.input, 201, FOLLOW_REGISTER_in_insn_format51l_type2954);
/* 5784 */       pushFollow(FOLLOW_fixed_64bit_literal_in_insn_format51l_type2956);
/* 5785 */       fixed_64bit_literal175 = fixed_64bit_literal();
/* 5786 */       this.state._fsp -= 1;
/*      */ 
/* 5788 */       match(this.input, 3, null);
/*      */ 
/* 5791 */       Opcode opcode = this.opcodes.getOpcodeByName(INSTRUCTION_FORMAT51l173 != null ? INSTRUCTION_FORMAT51l173.getText() : null);
/* 5792 */       short regA = parseRegister_byte(REGISTER174 != null ? REGISTER174.getText() : null);
/*      */ 
/* 5794 */       long litB = fixed_64bit_literal175;
/*      */ 
/* 5796 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderInstruction51l(opcode, regA, litB));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5802 */       reportError(re);
/* 5803 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_array_data_directive()
/*      */     throws RecognitionException
/*      */   {
/* 5816 */     short short_integral_literal176 = 0;
/* 5817 */     List array_elements177 = null;
/*      */     try
/*      */     {
/* 5823 */       match(this.input, 146, FOLLOW_I_STATEMENT_ARRAY_DATA_in_insn_array_data_directive2979);
/* 5824 */       match(this.input, 2, null);
/* 5825 */       match(this.input, 109, FOLLOW_I_ARRAY_ELEMENT_SIZE_in_insn_array_data_directive2982);
/* 5826 */       match(this.input, 2, null);
/* 5827 */       pushFollow(FOLLOW_short_integral_literal_in_insn_array_data_directive2984);
/* 5828 */       short_integral_literal176 = short_integral_literal();
/* 5829 */       this.state._fsp -= 1;
/*      */ 
/* 5831 */       match(this.input, 3, null);
/*      */ 
/* 5833 */       pushFollow(FOLLOW_array_elements_in_insn_array_data_directive2987);
/* 5834 */       array_elements177 = array_elements();
/* 5835 */       this.state._fsp -= 1;
/*      */ 
/* 5837 */       match(this.input, 3, null);
/*      */ 
/* 5840 */       int elementWidth = short_integral_literal176;
/* 5841 */       List elements = array_elements177;
/*      */ 
/* 5843 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderArrayPayload(elementWidth, array_elements177));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5849 */       reportError(re);
/* 5850 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_packed_switch_directive()
/*      */     throws RecognitionException
/*      */   {
/* 5863 */     int fixed_32bit_literal178 = 0;
/* 5864 */     List packed_switch_elements179 = null;
/*      */     try
/*      */     {
/* 5870 */       match(this.input, 178, FOLLOW_I_STATEMENT_PACKED_SWITCH_in_insn_packed_switch_directive3009);
/* 5871 */       match(this.input, 2, null);
/* 5872 */       match(this.input, 135, FOLLOW_I_PACKED_SWITCH_START_KEY_in_insn_packed_switch_directive3012);
/* 5873 */       match(this.input, 2, null);
/* 5874 */       pushFollow(FOLLOW_fixed_32bit_literal_in_insn_packed_switch_directive3014);
/* 5875 */       fixed_32bit_literal178 = fixed_32bit_literal();
/* 5876 */       this.state._fsp -= 1;
/*      */ 
/* 5878 */       match(this.input, 3, null);
/*      */ 
/* 5880 */       pushFollow(FOLLOW_packed_switch_elements_in_insn_packed_switch_directive3017);
/* 5881 */       packed_switch_elements179 = packed_switch_elements();
/* 5882 */       this.state._fsp -= 1;
/*      */ 
/* 5884 */       match(this.input, 3, null);
/*      */ 
/* 5887 */       int startKey = fixed_32bit_literal178;
/* 5888 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderPackedSwitchPayload(startKey, packed_switch_elements179));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5895 */       reportError(re);
/* 5896 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void insn_sparse_switch_directive()
/*      */     throws RecognitionException
/*      */   {
/* 5909 */     List sparse_switch_elements180 = null;
/*      */     try
/*      */     {
/* 5915 */       match(this.input, 179, FOLLOW_I_STATEMENT_SPARSE_SWITCH_in_insn_sparse_switch_directive3041);
/* 5916 */       match(this.input, 2, null);
/* 5917 */       pushFollow(FOLLOW_sparse_switch_elements_in_insn_sparse_switch_directive3043);
/* 5918 */       sparse_switch_elements180 = sparse_switch_elements();
/* 5919 */       this.state._fsp -= 1;
/*      */ 
/* 5921 */       match(this.input, 3, null);
/*      */ 
/* 5924 */       ((method_scope)this.method_stack.peek()).methodBuilder.addInstruction(new BuilderSparseSwitchPayload(sparse_switch_elements180));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5930 */       reportError(re);
/* 5931 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final nonvoid_type_descriptor_return nonvoid_type_descriptor()
/*      */     throws RecognitionException
/*      */   {
/* 5948 */     nonvoid_type_descriptor_return retval = new nonvoid_type_descriptor_return();
/* 5949 */     retval.start = this.input.LT(1);
/*      */     try
/*      */     {
/* 5955 */       if ((this.input.LA(1) == 8) || (this.input.LA(1) == 26) || (this.input.LA(1) == 199)) {
/* 5956 */         this.input.consume();
/* 5957 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/* 5960 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*      */         throw mse;
/*      */       }
/*      */ 
/* 5964 */       retval.type = ((CommonTree)retval.start).getText();
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5970 */       reportError(re);
/* 5971 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5976 */     return retval;
/*      */   }
/*      */ 
/*      */   public final reference_type_descriptor_return reference_type_descriptor()
/*      */     throws RecognitionException
/*      */   {
/* 5989 */     reference_type_descriptor_return retval = new reference_type_descriptor_return();
/* 5990 */     retval.start = this.input.LT(1);
/*      */     try
/*      */     {
/* 5996 */       if ((this.input.LA(1) == 8) || (this.input.LA(1) == 26)) {
/* 5997 */         this.input.consume();
/* 5998 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/* 6001 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*      */         throw mse;
/*      */       }
/*      */ 
/* 6005 */       retval.type = ((CommonTree)retval.start).getText();
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6011 */       reportError(re);
/* 6012 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6017 */     return retval;
/*      */   }
/*      */ 
/*      */   public final String type_descriptor()
/*      */     throws RecognitionException
/*      */   {
/* 6026 */     String type = null;
/*      */ 
/* 6029 */     TreeRuleReturnScope nonvoid_type_descriptor181 = null;
/*      */     try
/*      */     {
/* 6033 */       int alt35 = 2;
/* 6034 */       int LA35_0 = this.input.LA(1);
/* 6035 */       if (LA35_0 == 212) {
/* 6036 */         alt35 = 1;
/*      */       }
/* 6038 */       else if ((LA35_0 == 8) || (LA35_0 == 26) || (LA35_0 == 199)) {
/* 6039 */         alt35 = 2;
/*      */       }
/*      */       else
/*      */       {
/* 6043 */         NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
/*      */         throw nvae;
/*      */       }
/*      */ 
/* 6048 */       switch (alt35)
/*      */       {
/*      */       case 1:
/* 6052 */         match(this.input, 212, FOLLOW_VOID_TYPE_in_type_descriptor3122);
/* 6053 */         type = "V";
/*      */ 
/* 6055 */         break;
/*      */       case 2:
/* 6059 */         pushFollow(FOLLOW_nonvoid_type_descriptor_in_type_descriptor3130);
/* 6060 */         nonvoid_type_descriptor181 = nonvoid_type_descriptor();
/* 6061 */         this.state._fsp -= 1;
/*      */ 
/* 6063 */         type = nonvoid_type_descriptor181 != null ? ((nonvoid_type_descriptor_return)nonvoid_type_descriptor181).type : null;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6070 */       reportError(re);
/* 6071 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6076 */     return type;
/*      */   }
/*      */ 
/*      */   public final short short_integral_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6085 */     short value = 0;
/*      */ 
/* 6088 */     long long_literal182 = 0L;
/* 6089 */     int integer_literal183 = 0;
/* 6090 */     short short_literal184 = 0;
/* 6091 */     char char_literal185 = '\000';
/* 6092 */     byte byte_literal186 = 0;
/*      */     try
/*      */     {
/* 6096 */       int alt36 = 5;
/* 6097 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 187:
/* 6100 */         alt36 = 1;
/*      */ 
/* 6102 */         break;
/*      */       case 102:
/* 6105 */         alt36 = 2;
/*      */ 
/* 6107 */         break;
/*      */       case 204:
/* 6110 */         alt36 = 3;
/*      */ 
/* 6112 */         break;
/*      */       case 25:
/* 6115 */         alt36 = 4;
/*      */ 
/* 6117 */         break;
/*      */       case 22:
/* 6120 */         alt36 = 5;
/*      */ 
/* 6122 */         break;
/*      */       default:
/* 6124 */         NoViableAltException nvae = new NoViableAltException("", 36, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 6128 */       switch (alt36)
/*      */       {
/*      */       case 1:
/* 6132 */         pushFollow(FOLLOW_long_literal_in_short_integral_literal3148);
/* 6133 */         long_literal182 = long_literal();
/* 6134 */         this.state._fsp -= 1;
/*      */ 
/* 6137 */         LiteralTools.checkShort(long_literal182);
/* 6138 */         value = (short)(int)long_literal182;
/*      */ 
/* 6141 */         break;
/*      */       case 2:
/* 6145 */         pushFollow(FOLLOW_integer_literal_in_short_integral_literal3160);
/* 6146 */         integer_literal183 = integer_literal();
/* 6147 */         this.state._fsp -= 1;
/*      */ 
/* 6150 */         LiteralTools.checkShort(integer_literal183);
/* 6151 */         value = (short)integer_literal183;
/*      */ 
/* 6154 */         break;
/*      */       case 3:
/* 6158 */         pushFollow(FOLLOW_short_literal_in_short_integral_literal3172);
/* 6159 */         short_literal184 = short_literal();
/* 6160 */         this.state._fsp -= 1;
/*      */ 
/* 6162 */         value = short_literal184;
/*      */ 
/* 6164 */         break;
/*      */       case 4:
/* 6168 */         pushFollow(FOLLOW_char_literal_in_short_integral_literal3180);
/* 6169 */         char_literal185 = char_literal();
/* 6170 */         this.state._fsp -= 1;
/*      */ 
/* 6172 */         value = (short)char_literal185;
/*      */ 
/* 6174 */         break;
/*      */       case 5:
/* 6178 */         pushFollow(FOLLOW_byte_literal_in_short_integral_literal3188);
/* 6179 */         byte_literal186 = byte_literal();
/* 6180 */         this.state._fsp -= 1;
/*      */ 
/* 6182 */         value = (short)byte_literal186;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6189 */       reportError(re);
/* 6190 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6195 */     return value;
/*      */   }
/*      */ 
/*      */   public final int integral_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6204 */     int value = 0;
/*      */ 
/* 6207 */     long long_literal187 = 0L;
/* 6208 */     int integer_literal188 = 0;
/* 6209 */     short short_literal189 = 0;
/* 6210 */     byte byte_literal190 = 0;
/*      */     try
/*      */     {
/* 6214 */       int alt37 = 4;
/* 6215 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 187:
/* 6218 */         alt37 = 1;
/*      */ 
/* 6220 */         break;
/*      */       case 102:
/* 6223 */         alt37 = 2;
/*      */ 
/* 6225 */         break;
/*      */       case 204:
/* 6228 */         alt37 = 3;
/*      */ 
/* 6230 */         break;
/*      */       case 22:
/* 6233 */         alt37 = 4;
/*      */ 
/* 6235 */         break;
/*      */       default:
/* 6237 */         NoViableAltException nvae = new NoViableAltException("", 37, 0, this.input);
/*      */         throw nvae;
/*      */       }
/* 6241 */       switch (alt37)
/*      */       {
/*      */       case 1:
/* 6245 */         pushFollow(FOLLOW_long_literal_in_integral_literal3203);
/* 6246 */         long_literal187 = long_literal();
/* 6247 */         this.state._fsp -= 1;
/*      */ 
/* 6250 */         LiteralTools.checkInt(long_literal187);
/* 6251 */         value = (int)long_literal187;
/*      */ 
/* 6254 */         break;
/*      */       case 2:
/* 6258 */         pushFollow(FOLLOW_integer_literal_in_integral_literal3215);
/* 6259 */         integer_literal188 = integer_literal();
/* 6260 */         this.state._fsp -= 1;
/*      */ 
/* 6262 */         value = integer_literal188;
/*      */ 
/* 6264 */         break;
/*      */       case 3:
/* 6268 */         pushFollow(FOLLOW_short_literal_in_integral_literal3223);
/* 6269 */         short_literal189 = short_literal();
/* 6270 */         this.state._fsp -= 1;
/*      */ 
/* 6272 */         value = short_literal189;
/*      */ 
/* 6274 */         break;
/*      */       case 4:
/* 6278 */         pushFollow(FOLLOW_byte_literal_in_integral_literal3231);
/* 6279 */         byte_literal190 = byte_literal();
/* 6280 */         this.state._fsp -= 1;
/*      */ 
/* 6282 */         value = byte_literal190;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6289 */       reportError(re);
/* 6290 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6295 */     return value;
/*      */   }
/*      */ 
/*      */   public final int integer_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6304 */     int value = 0;
/*      */ 
/* 6307 */     CommonTree INTEGER_LITERAL191 = null;
/*      */     try
/*      */     {
/* 6313 */       INTEGER_LITERAL191 = (CommonTree)match(this.input, 102, FOLLOW_INTEGER_LITERAL_in_integer_literal3247);
/* 6314 */       value = LiteralTools.parseInt(INTEGER_LITERAL191 != null ? INTEGER_LITERAL191.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6319 */       reportError(re);
/* 6320 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6325 */     return value;
/*      */   }
/*      */ 
/*      */   public final long long_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6334 */     long value = 0L;
/*      */ 
/* 6337 */     CommonTree LONG_LITERAL192 = null;
/*      */     try
/*      */     {
/* 6343 */       LONG_LITERAL192 = (CommonTree)match(this.input, 187, FOLLOW_LONG_LITERAL_in_long_literal3262);
/* 6344 */       value = LiteralTools.parseLong(LONG_LITERAL192 != null ? LONG_LITERAL192.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6349 */       reportError(re);
/* 6350 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6355 */     return value;
/*      */   }
/*      */ 
/*      */   public final short short_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6364 */     short value = 0;
/*      */ 
/* 6367 */     CommonTree SHORT_LITERAL193 = null;
/*      */     try
/*      */     {
/* 6373 */       SHORT_LITERAL193 = (CommonTree)match(this.input, 204, FOLLOW_SHORT_LITERAL_in_short_literal3277);
/* 6374 */       value = LiteralTools.parseShort(SHORT_LITERAL193 != null ? SHORT_LITERAL193.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6379 */       reportError(re);
/* 6380 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6385 */     return value;
/*      */   }
/*      */ 
/*      */   public final byte byte_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6394 */     byte value = 0;
/*      */ 
/* 6397 */     CommonTree BYTE_LITERAL194 = null;
/*      */     try
/*      */     {
/* 6403 */       BYTE_LITERAL194 = (CommonTree)match(this.input, 22, FOLLOW_BYTE_LITERAL_in_byte_literal3292);
/* 6404 */       value = LiteralTools.parseByte(BYTE_LITERAL194 != null ? BYTE_LITERAL194.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6409 */       reportError(re);
/* 6410 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6415 */     return value;
/*      */   }
/*      */ 
/*      */   public final float float_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6424 */     float value = 0.0F;
/*      */ 
/* 6427 */     CommonTree FLOAT_LITERAL195 = null;
/*      */     try
/*      */     {
/* 6433 */       FLOAT_LITERAL195 = (CommonTree)match(this.input, 51, FOLLOW_FLOAT_LITERAL_in_float_literal3307);
/* 6434 */       value = LiteralTools.parseFloat(FLOAT_LITERAL195 != null ? FLOAT_LITERAL195.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6439 */       reportError(re);
/* 6440 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6445 */     return value;
/*      */   }
/*      */ 
/*      */   public final double double_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6454 */     double value = 0.0D;
/*      */ 
/* 6457 */     CommonTree DOUBLE_LITERAL196 = null;
/*      */     try
/*      */     {
/* 6463 */       DOUBLE_LITERAL196 = (CommonTree)match(this.input, 34, FOLLOW_DOUBLE_LITERAL_in_double_literal3322);
/* 6464 */       value = LiteralTools.parseDouble(DOUBLE_LITERAL196 != null ? DOUBLE_LITERAL196.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6469 */       reportError(re);
/* 6470 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6475 */     return value;
/*      */   }
/*      */ 
/*      */   public final char char_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6484 */     char value = '\000';
/*      */ 
/* 6487 */     CommonTree CHAR_LITERAL197 = null;
/*      */     try
/*      */     {
/* 6493 */       CHAR_LITERAL197 = (CommonTree)match(this.input, 25, FOLLOW_CHAR_LITERAL_in_char_literal3337);
/* 6494 */       value = (CHAR_LITERAL197 != null ? CHAR_LITERAL197.getText() : null).charAt(1);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6499 */       reportError(re);
/* 6500 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6505 */     return value;
/*      */   }
/*      */ 
/*      */   public final String string_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6514 */     String value = null;
/*      */ 
/* 6517 */     CommonTree STRING_LITERAL198 = null;
/*      */     try
/*      */     {
/* 6523 */       STRING_LITERAL198 = (CommonTree)match(this.input, 208, FOLLOW_STRING_LITERAL_in_string_literal3352);
/*      */ 
/* 6525 */       value = STRING_LITERAL198 != null ? STRING_LITERAL198.getText() : null;
/* 6526 */       value = value.substring(1, value.length() - 1);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6532 */       reportError(re);
/* 6533 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6538 */     return value;
/*      */   }
/*      */ 
/*      */   public final boolean bool_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6547 */     boolean value = false;
/*      */ 
/* 6550 */     CommonTree BOOL_LITERAL199 = null;
/*      */     try
/*      */     {
/* 6556 */       BOOL_LITERAL199 = (CommonTree)match(this.input, 21, FOLLOW_BOOL_LITERAL_in_bool_literal3371);
/* 6557 */       value = Boolean.parseBoolean(BOOL_LITERAL199 != null ? BOOL_LITERAL199.getText() : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6562 */       reportError(re);
/* 6563 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6568 */     return value;
/*      */   }
/*      */ 
/*      */   public final List<EncodedValue> array_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6577 */     List elements = null;
/*      */ 
/* 6580 */     EncodedValue literal200 = null;
/*      */     try
/*      */     {
/* 6586 */       elements = Lists.newArrayList();
/* 6587 */       match(this.input, 114, FOLLOW_I_ENCODED_ARRAY_in_array_literal3393);
/* 6588 */       if (this.input.LA(1) == 2) {
/* 6589 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 6593 */           int alt38 = 2;
/* 6594 */           int LA38_0 = this.input.LA(1);
/* 6595 */           if ((LA38_0 == 8) || ((LA38_0 >= 21) && (LA38_0 <= 22)) || ((LA38_0 >= 25) && (LA38_0 <= 26)) || (LA38_0 == 34) || (LA38_0 == 51) || (LA38_0 == 102) || ((LA38_0 >= 114) && (LA38_0 <= 117)) || (LA38_0 == 180) || (LA38_0 == 187) || (LA38_0 == 191) || (LA38_0 == 199) || (LA38_0 == 204) || (LA38_0 == 208) || (LA38_0 == 212)) {
/* 6596 */             alt38 = 1;
/*      */           }
/*      */ 
/* 6599 */           switch (alt38)
/*      */           {
/*      */           case 1:
/* 6603 */             pushFollow(FOLLOW_literal_in_array_literal3396);
/* 6604 */             literal200 = literal();
/* 6605 */             this.state._fsp -= 1;
/*      */ 
/* 6607 */             elements.add(literal200);
/*      */ 
/* 6609 */             break;
/*      */           default:
/* 6612 */             break label250;
/*      */           }
/*      */         }
/*      */ 
/* 6616 */         label250: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6623 */       reportError(re);
/* 6624 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6629 */     return elements;
/*      */   }
/*      */ 
/*      */   public final Set<Annotation> annotations()
/*      */     throws RecognitionException
/*      */   {
/* 6638 */     Set annotations = null;
/*      */ 
/* 6641 */     Annotation annotation201 = null;
/*      */     try
/*      */     {
/* 6647 */       HashMap annotationMap = Maps.newHashMap();
/* 6648 */       match(this.input, 106, FOLLOW_I_ANNOTATIONS_in_annotations3421);
/* 6649 */       if (this.input.LA(1) == 2) {
/* 6650 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 6654 */           int alt39 = 2;
/* 6655 */           int LA39_0 = this.input.LA(1);
/* 6656 */           if (LA39_0 == 105) {
/* 6657 */             alt39 = 1;
/*      */           }
/*      */ 
/* 6660 */           switch (alt39)
/*      */           {
/*      */           case 1:
/* 6664 */             pushFollow(FOLLOW_annotation_in_annotations3424);
/* 6665 */             annotation201 = annotation();
/* 6666 */             this.state._fsp -= 1;
/*      */ 
/* 6669 */             Annotation anno = annotation201;
/* 6670 */             Annotation old = (Annotation)annotationMap.put(anno.getType(), anno);
/* 6671 */             if (old != null) {
/*      */               throw new SemanticException(this.input, "Multiple annotations of type %s", new Object[] { anno.getType() });
/*      */             }
/*      */ 
/* 6676 */             break;
/*      */           default:
/* 6679 */             break label180;
/*      */           }
/*      */         }
/*      */ 
/* 6683 */         label180: match(this.input, 3, null);
/*      */       }
/*      */ 
/* 6687 */       if (annotationMap.size() > 0) {
/* 6688 */         annotations = ImmutableSet.copyOf(annotationMap.values());
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6695 */       reportError(re);
/* 6696 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6701 */     return annotations;
/*      */   }
/*      */ 
/*      */   public final Annotation annotation()
/*      */     throws RecognitionException
/*      */   {
/* 6710 */     Annotation annotation = null;
/*      */ 
/* 6713 */     CommonTree ANNOTATION_VISIBILITY202 = null;
/* 6714 */     TreeRuleReturnScope subannotation203 = null;
/*      */     try
/*      */     {
/* 6720 */       match(this.input, 105, FOLLOW_I_ANNOTATION_in_annotation3453);
/* 6721 */       match(this.input, 2, null);
/* 6722 */       ANNOTATION_VISIBILITY202 = (CommonTree)match(this.input, 6, FOLLOW_ANNOTATION_VISIBILITY_in_annotation3455);
/* 6723 */       pushFollow(FOLLOW_subannotation_in_annotation3457);
/* 6724 */       subannotation203 = subannotation();
/* 6725 */       this.state._fsp -= 1;
/*      */ 
/* 6727 */       match(this.input, 3, null);
/*      */ 
/* 6730 */       int visibility = AnnotationVisibility.getVisibility(ANNOTATION_VISIBILITY202 != null ? ANNOTATION_VISIBILITY202.getText() : null);
/* 6731 */       annotation = new ImmutableAnnotation(visibility, subannotation203 != null ? ((subannotation_return)subannotation203).annotationType : null, subannotation203 != null ? ((subannotation_return)subannotation203).elements : null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6737 */       reportError(re);
/* 6738 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6743 */     return annotation;
/*      */   }
/*      */ 
/*      */   public final AnnotationElement annotation_element()
/*      */     throws RecognitionException
/*      */   {
/* 6752 */     AnnotationElement element = null;
/*      */ 
/* 6755 */     CommonTree SIMPLE_NAME204 = null;
/* 6756 */     EncodedValue literal205 = null;
/*      */     try
/*      */     {
/* 6762 */       match(this.input, 107, FOLLOW_I_ANNOTATION_ELEMENT_in_annotation_element3478);
/* 6763 */       match(this.input, 2, null);
/* 6764 */       SIMPLE_NAME204 = (CommonTree)match(this.input, 205, FOLLOW_SIMPLE_NAME_in_annotation_element3480);
/* 6765 */       pushFollow(FOLLOW_literal_in_annotation_element3482);
/* 6766 */       literal205 = literal();
/* 6767 */       this.state._fsp -= 1;
/*      */ 
/* 6769 */       match(this.input, 3, null);
/*      */ 
/* 6772 */       element = new ImmutableAnnotationElement(SIMPLE_NAME204 != null ? SIMPLE_NAME204.getText() : null, literal205);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6778 */       reportError(re);
/* 6779 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6784 */     return element;
/*      */   }
/*      */ 
/*      */   public final subannotation_return subannotation()
/*      */     throws RecognitionException
/*      */   {
/* 6798 */     subannotation_return retval = new subannotation_return();
/* 6799 */     retval.start = this.input.LT(1);
/*      */ 
/* 6801 */     CommonTree CLASS_DESCRIPTOR207 = null;
/* 6802 */     AnnotationElement annotation_element206 = null;
/*      */     try
/*      */     {
/* 6808 */       ArrayList elements = Lists.newArrayList();
/* 6809 */       match(this.input, 180, FOLLOW_I_SUBANNOTATION_in_subannotation3509);
/* 6810 */       match(this.input, 2, null);
/* 6811 */       CLASS_DESCRIPTOR207 = (CommonTree)match(this.input, 26, FOLLOW_CLASS_DESCRIPTOR_in_subannotation3519);
/*      */       while (true)
/*      */       {
/* 6815 */         int alt40 = 2;
/* 6816 */         int LA40_0 = this.input.LA(1);
/* 6817 */         if (LA40_0 == 107) {
/* 6818 */           alt40 = 1;
/*      */         }
/*      */ 
/* 6821 */         switch (alt40)
/*      */         {
/*      */         case 1:
/* 6825 */           pushFollow(FOLLOW_annotation_element_in_subannotation3530);
/* 6826 */           annotation_element206 = annotation_element();
/* 6827 */           this.state._fsp -= 1;
/*      */ 
/* 6830 */           elements.add(annotation_element206);
/*      */ 
/* 6833 */           break;
/*      */         default:
/* 6836 */           break label161;
/*      */         }
/*      */       }
/*      */ 
/* 6840 */       label161: match(this.input, 3, null);
/*      */ 
/* 6843 */       retval.annotationType = (CLASS_DESCRIPTOR207 != null ? CLASS_DESCRIPTOR207.getText() : null);
/* 6844 */       retval.elements = elements;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6850 */       reportError(re);
/* 6851 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6856 */     return retval;
/*      */   }
/*      */ 
/*      */   public final FieldReference field_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6865 */     FieldReference value = null;
/*      */ 
/* 6868 */     ImmutableFieldReference fully_qualified_field208 = null;
/*      */     try
/*      */     {
/* 6874 */       match(this.input, 116, FOLLOW_I_ENCODED_FIELD_in_field_literal3569);
/* 6875 */       match(this.input, 2, null);
/* 6876 */       pushFollow(FOLLOW_fully_qualified_field_in_field_literal3571);
/* 6877 */       fully_qualified_field208 = fully_qualified_field();
/* 6878 */       this.state._fsp -= 1;
/*      */ 
/* 6880 */       match(this.input, 3, null);
/*      */ 
/* 6883 */       value = fully_qualified_field208;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6889 */       reportError(re);
/* 6890 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6895 */     return value;
/*      */   }
/*      */ 
/*      */   public final MethodReference method_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6904 */     MethodReference value = null;
/*      */ 
/* 6907 */     ImmutableMethodReference fully_qualified_method209 = null;
/*      */     try
/*      */     {
/* 6913 */       match(this.input, 117, FOLLOW_I_ENCODED_METHOD_in_method_literal3592);
/* 6914 */       match(this.input, 2, null);
/* 6915 */       pushFollow(FOLLOW_fully_qualified_method_in_method_literal3594);
/* 6916 */       fully_qualified_method209 = fully_qualified_method();
/* 6917 */       this.state._fsp -= 1;
/*      */ 
/* 6919 */       match(this.input, 3, null);
/*      */ 
/* 6922 */       value = fully_qualified_method209;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6928 */       reportError(re);
/* 6929 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6934 */     return value;
/*      */   }
/*      */ 
/*      */   public final FieldReference enum_literal()
/*      */     throws RecognitionException
/*      */   {
/* 6943 */     FieldReference value = null;
/*      */ 
/* 6946 */     ImmutableFieldReference fully_qualified_field210 = null;
/*      */     try
/*      */     {
/* 6952 */       match(this.input, 115, FOLLOW_I_ENCODED_ENUM_in_enum_literal3615);
/* 6953 */       match(this.input, 2, null);
/* 6954 */       pushFollow(FOLLOW_fully_qualified_field_in_enum_literal3617);
/* 6955 */       fully_qualified_field210 = fully_qualified_field();
/* 6956 */       this.state._fsp -= 1;
/*      */ 
/* 6958 */       match(this.input, 3, null);
/*      */ 
/* 6961 */       value = fully_qualified_field210;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 6967 */       reportError(re);
/* 6968 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6973 */     return value;
/*      */   }
/*      */ 
/*      */   public static class subannotation_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String annotationType;
/*      */     public List<AnnotationElement> elements;
/*      */   }
/*      */ 
/*      */   public static class reference_type_descriptor_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String type;
/*      */   }
/*      */ 
/*      */   public static class nonvoid_type_descriptor_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String type;
/*      */   }
/*      */ 
/*      */   public static class instruction_return extends TreeRuleReturnScope
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class register_range_return extends TreeRuleReturnScope
/*      */   {
/*      */     public int startRegister;
/*      */     public int endRegister;
/*      */   }
/*      */ 
/*      */   public static class register_list_return extends TreeRuleReturnScope
/*      */   {
/*      */     public byte[] registers;
/*      */     public byte registerCount;
/*      */   }
/*      */ 
/*      */   public static class registers_directive_return extends TreeRuleReturnScope
/*      */   {
/*      */     public boolean isLocalsDirective;
/*      */     public int registers;
/*      */   }
/*      */ 
/*      */   public static class method_name_and_prototype_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String name;
/*      */     public List<SmaliMethodParameter> parameters;
/*      */     public String returnType;
/*      */   }
/*      */ 
/*      */   public static class method_prototype_return extends TreeRuleReturnScope
/*      */   {
/*      */     public List<String> parameters;
/*      */     public String returnType;
/*      */   }
/*      */ 
/*      */   protected static class method_scope
/*      */   {
/*      */     boolean isStatic;
/*      */     int totalMethodRegisters;
/*      */     int methodParameterRegisters;
/*      */     MethodImplementationBuilder methodBuilder;
/*      */   }
/*      */ 
/*      */   public static class class_spec_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String type;
/*      */     public int accessFlags;
/*      */   }
/*      */ 
/*      */   public static class header_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String classType;
/*      */     public int accessFlags;
/*      */     public String superType;
/*      */     public List<String> implementsList;
/*      */     public String sourceSpec;
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.smaliTreeWalker
 * JD-Core Version:    0.6.0
 */