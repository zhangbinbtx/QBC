/*package com.quaero.qbcTest.dto.entity;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.tomcat.jni.Directory;

import com.quaero.qbcTest.server.ICommand;

*//**
 * 命令缓存管理类
 * @author zhangbin
 *
 *//*
public class CommandCacheManager
{
    /// <summary>
    /// 命令缓存字典
    /// </summary>
    protected Dictionary<Integer, ICommand> _commandDict = new Hashtable<>();

    protected String[] GetLibFiles()
    {
        String appDir = Directory.GetCurrentDirectory();
        String[] fileNames = Directory.GetFiles(appDir).Where(s => { return (Path.GetFileName(s).StartsWith("Bil.") && s.EndsWith(".dll")); }).ToArray();
        return fileNames; 
    }

    protected void AddCommandByFile(String fileName)
    {
        try
        {
            Assembly asm = Assembly.LoadFile(fileName);
            Type[] types = asm.GetTypes();
            foreach (Type t in types)
            {
                if ((typeof(ICommand).IsAssignableFrom(t)) && (!t.IsInterface) && (!t.IsAbstract))
                {
                    ICommand cmd = (ICommand)t.Assembly.CreateInstance(t.FullName);
                    _commandDict.Add(cmd.Command, cmd);                        
                }
            }
        }
        catch (Exception e)
        {
            LogHelper.Error(String.Format("CommandCacheManager.AddCommandByFile Error:{0}", e.Message));
        }
    }

    protected void LoadCommands()
    {
        String[] fileNames = GetLibFiles();
        for(String fileName :fileNames)
        {
            AddCommandByFile(fileName);
        }
    }

    /// <summary>
    /// 构造函数
    /// 利用反射原理初始化所有命令到字典列表中
    /// </summary>
    public CommandCacheManager()
    {
        LoadCommands();
    }

    /// <summary>
    /// 获取命令实例
    /// </summary>
    /// <param name="cmdValue">命令值</param>
    /// <param name="cmdInstance">命令实例</param>
    /// <returns>获取成功返回True</returns>
    public boolean GetCommand(int cmdValue,ICommand cmdInstance)
    {
        if (cmdValue == 0x30)
        {
            return _commandDict.TryGetValue(cmdValue,cmdInstance);
        }
        else
            return _commandDict.TryGetValue(cmdValue, cmdInstance);
    }
*/