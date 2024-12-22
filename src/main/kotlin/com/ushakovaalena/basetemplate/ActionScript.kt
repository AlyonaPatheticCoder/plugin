package com.ushakovaalena.basetemplate

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile

class ActionScript : AnAction() {
    /*
    Класс плагина, наследуемый от AnAction
    Переопределяет метод ActionPerformed
    Возвращаемое значение:
    Открывает файл README.md текущего проекта
     */

    override fun actionPerformed(event: AnActionEvent) {
        val project: Project? = event.project
        if (project == null) {
            Messages.showMessageDialog(
                "Project not opened",
                "Open README",
                Messages.getErrorIcon()
            )
            return
        }
        val readmeFile = findREADME(project.baseDir)
        if (readmeFile == null) {
            Messages.showMessageDialog(
                "README.md file not found",
                "Open README",
                Messages.getErrorIcon()
            )
            return
        }
        FileEditorManager.getInstance(project).openFile(readmeFile, true)
    }

    private fun findREADME(directory: VirtualFile): VirtualFile? {
        /*
        Функция findREADME
        Параметр: directory -  директория проекта
        Возвращаемое значение: null / child
        Рекурсивная функция, пробегающаяся по директориям проекта и файлам внутри, пока не будет найден readme.md
        */
        if (!directory.isDirectory) return null
        for (child in directory.children) {
            if (child.isDirectory) {
                val found = findREADME(child)
                if (found != null) {
                    return found
                }
            } else if (child.name.lowercase() == "readme.md") {
                return child
            }
        }
        return null
    }
}