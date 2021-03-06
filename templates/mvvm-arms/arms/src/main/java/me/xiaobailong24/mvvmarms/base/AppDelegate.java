package <%= appPackage %>.base;

import android.app.Application;
import android.content.Context;

import <%= appPackage %>.di.component.ArmsComponent;
import <%= appPackage %>.di.module.ArmsModule;
import <%= appPackage %>.lifecycle.LifecycleInjector;
import <%= appPackage %>.lifecycle.delegate.AppLifecycles;
import <%= appPackage %>.lifecycle.delegate.ILifecycle;
import <%= appPackage %>.lifecycle.di.component.LifecycleComponent;
import <%= appPackage %>.lifecycle.di.module.LifecycleModule;
import <%= appPackage %>.repository.IRepository;
import <%= appPackage %>.repository.RepositoryInjector;
import <%= appPackage %>.repository.di.component.RepositoryComponent;
import <%= appPackage %>.repository.di.module.RepositoryModule;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Application 生命周期代理接口实现类
 */
public class AppDelegate implements AppLifecycles, ILifecycle, IRepository, IArms {
    private Application mApplication;
    /**
     * {@link RepositoryInjector}
     */
    private RepositoryInjector mRepositoryInjector;
    /**
     * {@link LifecycleInjector}
     */
    private LifecycleInjector mLifecycleInjector;
    /**
     * {@link ArmsInjector}
     */
    private ArmsInjector mArmsInjector;


    public AppDelegate(Context context) {
        if (mRepositoryInjector == null) {
            mRepositoryInjector = new RepositoryInjector(context);
        }
        if (mLifecycleInjector == null) {
            mLifecycleInjector = new LifecycleInjector(context);
        }
        if (mArmsInjector == null) {
            mArmsInjector = new ArmsInjector(context);
        }
    }

    @Override
    public void attachBaseContext(Context context) {
        mLifecycleInjector.attachBaseContext(context);
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;

        //Repository inject
        mRepositoryInjector.onCreate(mApplication);

        //Lifecycle inject
        mLifecycleInjector.onCreate(mApplication);

        //Arms Inject
        mArmsInjector.onCreate(mApplication);

    }

    @Override
    public void onTerminate(Application application) {
        mLifecycleInjector.onTerminate(application);
        this.mLifecycleInjector = null;
        mArmsInjector.onTerminate(application);
        this.mArmsInjector = null;
        mRepositoryInjector.onTerminate(application);
        this.mRepositoryInjector = null;
        this.mApplication = null;
    }


    @Override
    public LifecycleComponent getLifecycleComponent() {
        return mLifecycleInjector.getLifecycleComponent();
    }

    @Override
    public LifecycleModule getLifecycleModule() {
        return mLifecycleInjector.getLifecycleModule();
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        return mRepositoryInjector.getRepositoryComponent();
    }

    @Override
    public RepositoryModule getRepositoryModule() {
        return mRepositoryInjector.getRepositoryModule();
    }

    @Override
    public ArmsComponent getArmsComponent() {
        return mArmsInjector.getArmsComponent();
    }

    @Override
    public ArmsModule getArmsModule() {
        return mArmsInjector.getArmsModule();
    }
}