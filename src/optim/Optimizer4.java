package optim;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class Optimizer4 extends ClassVisitor {
    public Optimizer4(ClassVisitor cv){
        super(ASM9, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv;
        mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if(mv != null){
            mv = new RemoveZeroMul(mv);
        }
        return mv;
    }

    private class RemoveZeroMul extends MethodVisitor {
        protected final static int SEEN_NOTHING = 0;
        protected final static int SEEN_ILOAD = 1;
        protected final static int SEEN_ALOAD = 2;
        protected final static int SEEN_GETFIELD = 3;
        protected final static int SEEN_ALOAD_2 = 2;
        protected final static int SEEN_GETFIELD_2 = 3;

        protected int state;
        protected int index;
        protected String owner;
        protected String name;
        protected String desc;

        public RemoveZeroMul(MethodVisitor mv){
            super(ASM9, mv);
        }

        public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
            visitInsn();
            mv.visitFrame(type, numLocal, local, numStack, stack);

        }

        @Override
        public void visitInsn(int opcode){
//            if(state == SEEN_ILOAD && (opcode == IMUL || opcode == IDIV)){
//                mv.visitInsn(ICONST_0);
//                state = SEEN_NOTHING;
//                return;
//            }
//
//            visitInsn();
//
//            if(opcode == ICONST_0){
//                state = SEEN_ICONST_0;
//                return;
//            }
            visitInsn();
            mv.visitInsn(opcode);
        }

        protected void visitInsn(){
            if(state == SEEN_ILOAD){
                mv.visitVarInsn(ILOAD, index);
            } else if (state == SEEN_ALOAD){
                mv.visitVarInsn(ALOAD,index);
            } else if (state == SEEN_GETFIELD){
                mv.visitVarInsn(ALOAD,index);
                visitFieldInsn(GETFIELD, owner, name, desc);
            }

            state = SEEN_NOTHING;
        }

        @Override
        public void visitIntInsn(int opcode, int operand){
            visitInsn();
            mv.visitIntInsn(opcode,operand);
        }

        @Override
        public void visitVarInsn(int opcode, int varIndex) {
            if(state == 1 && opcode == ILOAD){
                state = SEEN_ILOAD;
                index = varIndex;
                return;
            }

            visitInsn();
            mv.visitVarInsn(opcode, varIndex);
        }

        @Override
        public void visitTypeInsn(int opcode, String desc){
            visitInsn();
            mv.visitTypeInsn(opcode, desc);
        }

        @Override
        public void visitFieldInsn(int opc, String owner, String name, String desc){
            if(state == SEEN_ALOAD){
                if(opc==GETFIELD){
                    state = SEEN_GETFIELD;
                    this.owner=owner;
                    this.name=name;
                    this.desc=desc;
                    return;
                }
            }


            visitInsn();



            mv.visitFieldInsn(opc, owner, name, desc);
        }

        @Override
        public void visitMethodInsn(int opc, String owner, String name, String desc){
            visitInsn();
            mv.visitMethodInsn(opc,owner,name, desc);
        }

        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            visitInsn();
            mv.visitMethodInsn(opcode,owner, name, descriptor, isInterface);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label){
            visitInsn();
            mv.visitJumpInsn(opcode, label);
        }

        public void visitLabel(Label label){
            visitInsn();
            mv.visitLabel(label);
        }

        public void visitLdcInsn(Object cst){
            visitInsn();
            mv.visitLdcInsn(cst);
        }
        public void visitIincInsn(int var, int increment){
            visitInsn();
            mv.visitIincInsn(var, increment);
        }
    }
}
