/*
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public
 License as published by the Free Software Foundation; either
 version 2 of the license or (at your option) any later version.
 */

package org.gjt.jclasslib.idea

import com.intellij.lang.injection.InjectedLanguageManager
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiClassOwner
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtilBase

class ShowBytecodeAction : AnAction() {

    override fun getActionUpdateThread() = ActionUpdateThread.BGT

    override fun update(e: AnActionEvent) {
        e.presentation.apply {
            isEnabled = getPsiElement(e)?.run { containingFile is PsiClassOwner && isContainedInClass(this) } == true
            icon = ICON_SHOW_BYTE_CODE
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        val psiElement = getPsiElement(e) ?: return
        val project = e.project ?: return

        openClassFile(psiElement, null, project)
    }

    private fun getPsiElement(e: AnActionEvent): PsiElement? =
            getPsiElement(e.dataContext, e.project, e.getData(CommonDataKeys.EDITOR))

    private fun getPsiElement(dataContext: DataContext, project: Project?, editor: Editor?): PsiElement? = when {
        project == null -> null
        editor == null -> dataContext.getData(CommonDataKeys.PSI_ELEMENT)
        else -> {
            val psiFile = PsiUtilBase.getPsiFileInEditor(editor, project)
            psiFile?.let {
                val offset = editor.caretModel.offset
                InjectedLanguageManager.getInstance(project).findInjectedElementAt(psiFile, offset)
                        ?: psiFile.findElementAt(offset)
            }
        }
    }

}

val ICON_SHOW_BYTE_CODE = IconLoader.getIcon("/icons/jclasslib.png", ShowBytecodeAction::class.java) // 13x13