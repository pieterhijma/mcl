'(:target "xeon_e5620"
  :output "opencl"
  :device-type "CL_DEVICE_TYPE_CPU"
  :memory-spaces (("reg" . ("" ""))
		  ("main" . ("__global" "barrier(CLK_GLOBAL_MEM_FENCE)")))
  :pargroups (("vectors" . ("get_local_id" "get_local_size"))
	      ("threads" . ("get_group_id" "get_group_size")))
  :dimensions (("get_local_id" . (3 :func))
	       ("get_group_id" . (3 :func)))
  :bandwidth-memoryspace "main"
  :builtin-funcs (nil))
