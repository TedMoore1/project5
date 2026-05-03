package optim;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class Optimizer5 extends ClassVisitor {
    public Optimizer5(ClassVisitor cv){
        super(ASM9, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv;
        mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if(mv != null){
            mv = new RemoveTrue(mv);
        }
        return mv;
    }

    private class RemoveTrue extends MethodVisitor {
        protected final static int SEEN_NOTHING = 0;
        protected final static int SEEN_ICONST_1 = 1;
        protected int state;

        public RemoveTrue(MethodVisitor mv){
            super(ASM9, mv);
        }

        public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
            visitInsn();
            mv.visitFrame(type, numLocal, local, numStack, stack);

        }

        @Override
        public void visitInsn(int opcode){
            visitInsn();
            if(opcode == ICONST_1){
                state = SEEN_ICONST_1;
                return;
            }
            mv.visitInsn(opcode);
        }

        protected void visitInsn(){
            if(state == SEEN_ICONST_1){
                mv.visitInsn(ICONST_1);
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
            if(state == SEEN_ICONST_1){
                if(opcode == IF_ICMPEQ){
                    mv.visitJumpInsn(IFNE, label);
                    state = SEEN_NOTHING;
                    return;
                }
            }
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
