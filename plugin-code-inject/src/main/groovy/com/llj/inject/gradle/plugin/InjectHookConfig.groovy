package com.llj.inject.gradle.plugin

import com.llj.inject.gradle.plugin.util.InjectMethodCell
import jdk.internal.org.objectweb.asm.Opcodes

class InjectHookConfig {

    /**
     * 必须是全路径形式，不是点分形式的包名
     */
    public static String sAgentClassName = 'com/llj/lib/tracker/Tracker'

    /**
     *     String name
     *     String desc
     *     String parent
     *     String agentName
     *     String agentDesc
     *     String paramsStart
     *     String paramsCount
     *     List[] opcodes
     */

    /**
     *     ('I',Opcodes.ILOAD);// I: int , retrieve integer from local variable
     *     ('Z',Opcodes.ILOAD);// Z: bool , retrieve boolean from local variable
     *     ('J',Opcodes.LLOAD);// J: long , retrieve long from local variable
     *     ('F',Opcodes.FLOAD);// F: float , retrieve float from local variable
     *     ('D',Opcodes.DLOAD);// D: double , retrieve double from local variable
     *     ('Ljava/lang/Integer',Opcodes.ALOAD);// object
     */

    /**
     * 父类parent是class的类。
     * 首先通过targetPackages可以将范围限定在摸个包或者某个类中
     * 然后通过name+desc组合成map的key找到对应的InjectMethodCell，然后调用对应的sAgentClassName，agentName,agentDesc
     */
    public final static HashMap<String, InjectMethodCell> sSuperNameMethods = new HashMap<>()
    static {
        //DebouncingOnClickListener
        sSuperNameMethods.put('doClick(Landroid/view/View;)V', new InjectMethodCell(
                'doClick',
                '(Landroid/view/View;)V',
                'butterknife/internal/DebouncingOnClickListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
    }
    /**
     * parent是interface
     */
    public final static HashMap<String, InjectMethodCell> sInterfaceMethods = new HashMap<>()
    static {
        //View
        sInterfaceMethods.put('onClick(Landroid/view/View;)V', new InjectMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'android/view/View$OnClickListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        //DialogInterface
        sInterfaceMethods.put('onClick(Landroid/content/ DialogInterface;I)V', new InjectMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'android/content/DialogInterface$OnClickListener',
                'trackDialogOnClick',
                '(Ljava/lang/Object;Landroid/content/DialogInterface;I)V',
                0, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        //AdapterView
        sInterfaceMethods.put('onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new InjectMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemClickListener',
                'trackOnItemClick',
                '(Ljava/lang/Object;Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                0, 5,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.LLOAD]))
        sInterfaceMethods.put('onItemSelected(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new InjectMethodCell(
                'onItemSelected',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemSelectedListener',
                'trackOnItemSelected',
                '(Ljava/lang/Object;Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                0, 5,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.LLOAD]))
        //ExpandableListView
        sInterfaceMethods.put('onGroupClick(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z', new InjectMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'android/widget/ExpandableListView$OnGroupClickListener',
                'trackOnGroupClick',
                '(Ljava/lang/Object;Landroid/widget/ExpandableListView;Landroid/view/View;IJ)V',
                0, 5,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.LLOAD]))
        sInterfaceMethods.put('onChildClick(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z', new InjectMethodCell(
                'onChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
                'android/widget/ExpandableListView$OnChildClickListener',
                'trackOnChildClick',
                '(Ljava/lang/Object;Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)V',
                0, 6,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD, Opcodes.LLOAD]))
        //RatingBar
        sInterfaceMethods.put('onRatingChanged(Landroid/widget/RatingBar;FZ)V', new InjectMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'android/widget/RatingBar$OnRatingBarChangeListener',
                'trackOnRatingChanged',
                '(Ljava/lang/Object;Landroid/widget/RatingBar;FZ)V',
                0, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.FLOAD, Opcodes.ILOAD]))
        //SeekBar
        sInterfaceMethods.put('onStopTrackingTouch(Landroid/widget/SeekBar;)V', new InjectMethodCell(
                'onStopTrackingTouch',
                '(Landroid/widget/SeekBar;)V',
                'android/widget/SeekBar$OnSeekBarChangeListener',
                'onStopTrackingTouch',
                '(Ljava/lang/Object;Landroid/widget/SeekBar;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))
        //CompoundButton
        sInterfaceMethods.put('onCheckedChanged(Landroid/widget/CompoundButton;Z)V', new InjectMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'android/widget/CompoundButton$OnCheckedChangeListener',
                'onCheckedChanged',
                '(Ljava/lang/Object;Landroid/widget/CompoundButton;Z)V',
                0, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        //RadioGroup
        sInterfaceMethods.put('onCheckedChanged(Landroid/widget/RadioGroup;I)V', new InjectMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'android/widget/RadioGroup$OnCheckedChangeListener',
                'onCheckedChanged',
                '(Ljava/lang/Object;Landroid/widget/RadioGroup;I)V',
                0, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        //SeekBar
        sInterfaceMethods.put('onProgressChanged(Landroid/widget/SeekBar;IZ)V', new InjectMethodCell(
                'onProgressChanged',
                '(Landroid/widget/SeekBar;IZ)V',
                'android/widget/SeekBar$OnSeekBarChangeListener',
                'trackOnProgressChanged',
                '(Ljava/lang/Object;Landroid/widget/RadioGroup;IZ)V',
                0, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD]))

        //TabHost
        sInterfaceMethods.put('onTabChanged(Ljava/lang/String;)V', new InjectMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'android/widget/TabHost$OnTabChangeListener',
                'trackOnTabChanged',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD]))

        //TabLayout
        sInterfaceMethods.put('onTabSelected(Landroid/support/design/widget/TabLayout$Tab;)V', new InjectMethodCell(
                'onTabSelected',
                '(Landroid/support/design/widget/TabLayout$Tab;)V',
                'android/support/design/widget/TabLayout$OnTabSelectedListener',
                'trackOnTabSelected',
                '(Ljava/lang/Object;Ljava/lang/Object;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))

        sInterfaceMethods.put('onTabSelected(Lcom/google/android/material/tabs/TabLayout$Tab;)V', new InjectMethodCell(
                'onTabSelected',
                '(Lcom/google/android/material/tabs/TabLayout$Tab;)V',
                'com/google/android/material/tabs/TabLayout$OnTabSelectedListener',
                'trackOnTabSelected',
                '(Ljava/lang/Object;Ljava/lang/Object;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))

        // Todo: 扩展
    }

    /**
     * android.gradle 3.2.1 版本中，针对 Lambda 表达式处理
     */

    public final
    static HashMap<String, InjectMethodCell> sLambdaMethods = new HashMap<>()

    static {
        //View
        sLambdaMethods.put('(Landroid/view/View;)V', new InjectMethodCell(
                'onClick',
                '(Landroid/view/View;)V',
                'android/view/View$OnClickListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        //CompoundButton
        sLambdaMethods.put('(Landroid/widget/CompoundButton;Z)V', new InjectMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/CompoundButton;Z)V',
                'android/widget/CompoundButton$OnCheckedChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        //CompoundButton
        sLambdaMethods.put('(Landroid/widget/RatingBar;FZ)V', new InjectMethodCell(
                'onRatingChanged',
                '(Landroid/widget/RatingBar;FZ)V',
                'android/widget/RatingBar$OnRatingBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        //CompoundButton
        sLambdaMethods.put('(Landroid/widget/SeekBar;)V', new InjectMethodCell(
                'onStopTrackingTouch',
                '(Landroid/widget/SeekBar;)V',
                'android/widget/SeekBar$OnSeekBarChangeListener',
                'trackViewOnClick',
                '(Landroid/view/View;)V',
                1, 1,
                [Opcodes.ALOAD]))
        //CompoundButton
        sLambdaMethods.put('(Landroid/widget/RadioGroup;I)V', new InjectMethodCell(
                'onCheckedChanged',
                '(Landroid/widget/RadioGroup;I)V',
                'android/widget/RadioGroup$OnCheckedChangeListener',
                'trackRadioGroup',
                '(Landroid/widget/RadioGroup;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        //DialogInterface
        sLambdaMethods.put('(Landroid/content/DialogInterface;I)V', new InjectMethodCell(
                'onClick',
                '(Landroid/content/DialogInterface;I)V',
                'android/content/DialogInterface$OnClickListener',
                'trackDialog',
                '(Landroid/content/DialogInterface;I)V',
                1, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        //AdapterView
        sLambdaMethods.put('(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new InjectMethodCell(
                'onItemClick',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemClickListener',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Landroid/widget/AdapterView;Landroid/view/View;IJ)V', new InjectMethodCell(
                'onItemSelected',
                '(Landroid/widget/AdapterView;Landroid/view/View;IJ)V',
                'android/widget/AdapterView$OnItemSelectedListener',
                'trackListView',
                '(Landroid/widget/AdapterView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        //ExpandableListView
        sLambdaMethods.put('(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z', new InjectMethodCell(
                'onGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IJ)Z',
                'android/widget/ExpandableListView$OnGroupClickListener',
                'trackExpandableListViewOnGroupClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;I)V',
                1, 3,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD]))
        sLambdaMethods.put('(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z', new InjectMethodCell(
                'onChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;IIJ)Z',
                'android/widget/ExpandableListView$OnChildClickListener',
                'trackExpandableListViewOnChildClick',
                '(Landroid/widget/ExpandableListView;Landroid/view/View;II)V',
                1, 4,
                [Opcodes.ALOAD, Opcodes.ALOAD, Opcodes.ILOAD, Opcodes.ILOAD]))
        //TabHost
        sLambdaMethods.put('(Ljava/lang/String;)V', new InjectMethodCell(
                'onTabChanged',
                '(Ljava/lang/String;)V',
                'android/widget/TabHost$OnTabChangeListener',
                'trackTabHost',
                '(Ljava/lang/String;)V',
                1, 1,
                [Opcodes.ALOAD]))
        //NavigationView
        sLambdaMethods.put('(Landroid/view/MenuItem;)Z', new InjectMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'android/support/design/widget/NavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Ljava/lang/Object;Landroid/view/MenuItem;)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ALOAD]))

        //NavigationView
        sLambdaMethods.put('(Landroid/view/MenuItem;)Z2', new InjectMethodCell(
                'onNavigationItemSelected',
                '(Landroid/view/MenuItem;)Z',
                'android/support/design/widget/NavigationView$OnNavigationItemSelectedListener',
                'trackMenuItem',
                '(Landroid/view/MenuItem;)V',
                0, 1,
                [Opcodes.ALOAD]))

        // Todo: 扩展
    }

    /**
     * Fragment中的方法
     */
    public final static HashMap<String, InjectMethodCell> sFragmentMethods = new HashMap<>()
    static {

        sFragmentMethods.put('onStart()V', new InjectMethodCell(
                'onStart',
                '()V',
                '',// parent省略，均为 android/app/Fragment 或 android/support/v4/app/Fragment
                'trackFragmentStart',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD]))
        sFragmentMethods.put('onResume()V', new InjectMethodCell(
                'onResume',
                '()V',
                '',// parent省略，均为 android/app/Fragment 或 android/support/v4/app/Fragment
                'trackFragmentResume',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD]))

        sFragmentMethods.put('onPause()V', new InjectMethodCell(
                'onPause',
                '()V',
                '',
                'trackFragmentPause',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD]))
        sFragmentMethods.put('onStop()V', new InjectMethodCell(
                'onStop',
                '()V',
                '',
                'trackFragmentStop',
                '(Ljava/lang/Object;)V',
                0, 1,
                [Opcodes.ALOAD]))
        sFragmentMethods.put('setUserVisibleHint(Z)V', new InjectMethodCell(
                'setUserVisibleHint',
                '(Z)V',
                '',// parent省略，均为 android/app/Fragment 或 android/support/v4/app/Fragment
                'trackFragmentUserVisibleHint',
                '(Ljava/lang/Object;Z)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
        sFragmentMethods.put('onHiddenChanged(Z)V', new InjectMethodCell(
                'onHiddenChanged',
                '(Z)V',
                '',
                'trackFragmentHiddenChanged',
                '(Ljava/lang/Object;Z)V',
                0, 2,
                [Opcodes.ALOAD, Opcodes.ILOAD]))
    }

}
