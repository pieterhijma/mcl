'(:target "intel_gpu"
  :output "opencl"
  :device-type "CL_DEVICE_TYPE_GPU"
  :memory-spaces (("local" . ("" ""))
		  ("shared" . ("__local" "barrier(CLK_LOCAL_MEM_FENCE)"))
		  ("reg" .  ("" ""))
		  ("global" . ("__global" "barrier(CLK_GLOBAL_MEM_FENCE)")))
  :pargroups (("threads" . ("get_local_id" "get_local_size"))
	      ("blocks" . ("get_group_id" "get_group_size")))
  :dimensions (("get_local_id" . (3 :func))
	       ("get_group_id" . (3 :func)))
  :bandwidth-memoryspace "global"
  :builtin-funcs (("atomicAdd" . "atomic_add")))
